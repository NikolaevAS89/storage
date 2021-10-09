package ru.timestop.storage.service;

import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class RequestCountServiceImpl implements RequestCountService {
    private final Map<String, Integer> cash;

    public RequestCountServiceImpl() {
        this.cash = new ConcurrentHashMap<>();
    }


    @Override
    public void increaseRequestCount(String fileId) {
        cash.compute(fileId, (k, v) -> (v == null) ? 1 : v + 1);
    }

    @Override
    public int getRequestCount(String fileId) {
        return cash.get(fileId);
    }
}
