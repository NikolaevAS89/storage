package ru.timestop.storage.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;
import ru.timestop.storage.entity.StorageRecord;

public interface StorageService {

    void store(StorageRecord record, MultipartFile file);

    boolean delete(StorageRecord record);

    Resource loadAsResource(StorageRecord record);
}
