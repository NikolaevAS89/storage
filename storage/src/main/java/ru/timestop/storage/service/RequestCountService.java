package ru.timestop.storage.service;

public interface RequestCountService {
    void increaseRequestCount(String fileId);
    int getRequestCount(String fileId);
}
