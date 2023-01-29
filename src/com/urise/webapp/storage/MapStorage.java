package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected String getSearchKey(String uuid) {
        return uuid;
    }

    @Override
    protected boolean isExist(String key) {
        return storage.containsKey(key);
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(String key, Resume resume) {
        storage.put(key, resume);
    }

    @Override
    protected void doUpdate(String key, Resume resume) {
        storage.put(key, resume);
    }

    @Override
    protected void doDelete(String key) {
        storage.remove(key);
    }

    @Override
    protected Resume doGet(String key) {
       return storage.get(key);
    }

    @Override
    protected List<Resume> doCopyAll() {
        List<Resume> list = new ArrayList<Resume>(storage.values());
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    public int size() {
        return storage.size();
    }
}
