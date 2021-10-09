package ru.timestop.storage.entity;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;

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
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;
    private String user;
    private String name;
    private String extension;
    private long loadedTime;
    private String tag;
    @Transient
    private String downloadLink;

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

    public long getLoadedTime() {
        return loadedTime;
    }

    public void setLoadedTime(long loadedTime) {
        this.loadedTime = loadedTime;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }
}