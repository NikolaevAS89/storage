package ru.timestop.storage.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.timestop.storage.entity.StorageRecord;
import ru.timestop.storage.exception.StorageFileNotFoundException;
import ru.timestop.storage.service.RequestCountService;
import ru.timestop.storage.service.StorageRecordService;
import ru.timestop.storage.service.StorageService;

import java.util.stream.Collectors;

@Controller
public class FileUploadController {
    private final static Logger LOGGER = LoggerFactory.getLogger(FileUploadController.class);
    private final StorageService storageService;
    private final RequestCountService requestCountService;
    private final StorageRecordService storageRecordService;

    @Autowired
    public FileUploadController(StorageService storageService
            , RequestCountService requestCountService
            , StorageRecordService storageRecordService) {
        this.storageService = storageService;
        this.requestCountService = requestCountService;
        this.storageRecordService = storageRecordService;
    }

    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = "Anonymus";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
        }
        return currentUserName;
    }

    @GetMapping("/files")
    public String listFiles(Model model) {
        model.addAttribute("files", storageRecordService.getAll(getUserName())
                .peek(record -> record.setDownloadLink(MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
                                "handleDownload", record.getId())
                        .build()
                        .toUri()
                        .toString()))
                .collect(Collectors.toList()));
        return "files";
    }


    @GetMapping("/files/{uuid:.+}")
    @ResponseBody
    public ResponseEntity<Resource> handleDownload(@PathVariable String uuid) {
        StorageRecord record = storageRecordService.findById(uuid);
        Resource file = storageService.loadAsResource(record);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + record.getName() + record.getExtension() + "\"")
                .body(file);
    }

    @PostMapping("/files/{uuid:.+}")
    public String handleDelete(@PathVariable String uuid) {

        StorageRecord record = storageRecordService.findById(uuid);
        storageRecordService.delete(record);
        storageService.delete(record);
        return "redirect:/files";
    }

    @PostMapping("/files")
    public String handleUpload(@RequestParam("file") MultipartFile file,
                               @RequestParam("tags") String tags,
                               RedirectAttributes redirectAttributes) {

        StorageRecord record = storageRecordService.createNew(file, getUserName(), tags);
        storageService.store(record, file);
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");

        return "redirect:/files";
    }


    @GetMapping("/file/{uuid:.+}")
    public ResponseEntity<StorageRecord> handleGetMeta(@PathVariable String uuid) {

        StorageRecord record = storageRecordService.findById(uuid);
        return ResponseEntity.ok().contentType(MediaType.APPLICATION_JSON)
                .body(record);
    }

    @PostMapping("/file/{uuid:.+}")
    public String handleUpdateMeta(@RequestParam("tags") String tags, @PathVariable String uuid) {
        LOGGER.info("handleUpdateMeta(" + tags + ", " + uuid + ")");
        StorageRecord record = storageRecordService.findById(uuid);
        record.setTag(tags);
        storageRecordService.update(record);
        return "redirect:/files";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }
}
