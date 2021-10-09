package ru.timestop.storage;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.timestop.storage.entity.StorageRecord;

import javax.persistence.PersistenceContext;
import java.util.Date;
import java.util.List;

@Repository
@PersistenceContext(name = "storageDB")
public interface StorageRecordRepository extends CrudRepository<StorageRecord, String> {

    List<StorageRecord> findAllByUser(String user);

    List<StorageRecord> findAllByUserAndLoadDateBefore(String user, Date loadDate);

    List<StorageRecord> findAllByUserAndLoadDateAfter(String user, Date loadDate);

    List<StorageRecord> findAllByUserAndLoadDateEquals(String user, Date loadDate);
}