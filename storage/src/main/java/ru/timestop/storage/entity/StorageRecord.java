package ru.timestop.storage.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "record")
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "getAllByUser",
                query = "select * from record r where r.user = ?",
                resultClass = StorageRecord.class
        )
})
public class StorageRecord {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    private String user;
    private String name;
    private String extension;
    @Temporal(TemporalType.DATE)
    private Date loadDate;
    @Temporal(TemporalType.TIME)
    private Date loadTime;
    private String tag;

    @Override
    public String toString() {
        return String.format(
                "Customer[id=%s, user='%s', name='%s', extension='%s', tag='%s']",
                getId(), getUser(), getName(), getExtension(), getTag());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Date getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(Date loadDate) {
        this.loadDate = loadDate;
    }

    public Date getLoadTime() {
        return loadTime;
    }

    public void setLoadTime(Date loadTime) {
        this.loadTime = loadTime;
    }
}