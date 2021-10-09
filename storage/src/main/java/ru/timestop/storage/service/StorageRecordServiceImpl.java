package ru.timestop.storage.service;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.timestop.storage.entity.StorageRecord;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.stream.Stream;

@Component
public class StorageRecordServiceImpl implements StorageRecordService {

    @PersistenceContext(unitName = "storageDB")
    private EntityManager entityManager;

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public StorageRecord createNew(MultipartFile file, String user, String tags) {
        String origin = file.getOriginalFilename();
        StorageRecord record = new StorageRecord();
        record.setTag(tags);
        record.setLoadedTime(System.nanoTime());
        record.setUser(user);
        if (origin != null && origin.contains(".")) {
            record.setName(origin.substring(0, origin.indexOf(".")));
            record.setExtension(origin.substring(origin.indexOf(".")));
        } else {
            record.setName(origin);
            record.setExtension("");
        }
        entityManager.persist(record);
        return record;
    }

    @Override
    public Stream<StorageRecord> getAll(String user) {
        return entityManager.createNamedQuery("getAllByUser", StorageRecord.class)
                .setParameter(1, user)
                .getResultStream();
    }

    @Override
    public StorageRecord findById(String id) {
        return entityManager.find(StorageRecord.class, id);
    }

    @Override
    @Transactional
    public void delete(StorageRecord storageRecord) {
        entityManager.remove(storageRecord);
    }

    @Override
    @Transactional
    public void update(StorageRecord storageRecord) {
        entityManager.merge(storageRecord);
    }
}
