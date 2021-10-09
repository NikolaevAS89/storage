package ru.timestop.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.timestop.storage.StorageProperties;
import ru.timestop.storage.entity.StorageRecord;
import ru.timestop.storage.exception.StorageException;
import ru.timestop.storage.exception.StorageFileNotFoundException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class StorageServiceImpl implements StorageService {
    private final String rootLocation;

    @Autowired
    public StorageServiceImpl(StorageProperties properties) {
        this.rootLocation = properties.getLocation();
    }

    private Path getStoredPath(StorageRecord record) {
        return Paths.get(this.rootLocation, record.getId())
                .normalize()
                .toAbsolutePath();
    }

    @Override
    public void store(StorageRecord record, MultipartFile file) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
            try (InputStream inputStream = file.getInputStream()) {
                Files.copy(inputStream, getStoredPath(record),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    @Override
    public Resource loadAsResource(StorageRecord record) {
        Path path = getStoredPath(record);
        try {
            Resource resource = new UrlResource(path.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + path);
            }
        } catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + path, e);
        }
    }

    @Override
    public boolean delete(StorageRecord record) {
        return getStoredPath(record).toFile().delete();
    }
}
