package ru.timestop.storage.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.timestop.storage.StorageRecordRepository;
import ru.timestop.storage.entity.StorageRecord;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.stream.Stream;

@Component
public class StorageRecordServiceImpl implements StorageRecordService {

    @PersistenceContext(unitName = "storageDB")
    private EntityManager entityManager;

    @Autowired
    private StorageRecordRepository recordRepository;

    @Autowired
    private StorageService storageService;

    @Override
    @Transactional
    public StorageRecord createNew(MultipartFile file, String user, String tags) {
        String origin = file.getOriginalFilename();
        StorageRecord record = new StorageRecord();
        record.setTag(tags);
        Date now = new Date(System.nanoTime());
        record.setLoadDate(now);
        record.setLoadTime(now);
        record.setUser(user);
        if (origin != null && origin.contains(".")) {
            record.setName(origin.substring(0, origin.indexOf(".")));
            record.setExtension(origin.substring(origin.indexOf(".")));
        } else {
            record.setName(origin);
            record.setExtension("");
        }
        entityManager.persist(record);
        storageService.store(record, file);
        return record;
    }

    @Override
    public Stream<StorageRecord> getAll(String user) {
        return recordRepository.findAllByUser(user).stream();
    }

    @Override
    public StorageRecord findById(String id) {
        return recordRepository.findById(id).get(); // TODO
    }

    @Override
    public void delete(StorageRecord storageRecord) {
        storageService.delete(storageRecord);
        recordRepository.delete(storageRecord);
    }

    @Override
    public void update(StorageRecord storageRecord) {
        recordRepository.save(storageRecord);
    }
}
