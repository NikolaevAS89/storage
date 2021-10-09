package ru.timestop.storage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.timestop.storage.entity.StorageRecord;

import javax.persistence.PersistenceContext;

@Repository
@PersistenceContext(name = "storageDB")
public interface StorageRecordRepository extends CrudRepository<StorageRecord, String> {

    StorageRecord findByUser(String user);
}