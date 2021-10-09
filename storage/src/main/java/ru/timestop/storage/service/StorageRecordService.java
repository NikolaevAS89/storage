package ru.timestop.storage.service;

import org.springframework.web.multipart.MultipartFile;
import ru.timestop.storage.entity.StorageRecord;

import java.util.stream.Stream;

/**
 * service for storage file's meta
 */
public interface StorageRecordService {
    /**
     * create new meta record
     */
    StorageRecord createNew(MultipartFile file, String user, String tags);

    /**
     * Search all records for user
     *
     * @param user
     * @return
     */
    Stream<StorageRecord> getAll(String user);

    /**
     * find meta by id
     *
     * @param id
     * @return
     */
    StorageRecord findById(String id);

    /**
     * delete meta
     *
     * @param storageRecord
     */
    void delete(StorageRecord storageRecord);

    void update(StorageRecord storageRecord);
}
