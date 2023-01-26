package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MapStorage extends AbstractStorage<String> {
    private final HashMap<String, Resume> storage = new HashMap<>();

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

    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<Resume>(storage.values());
        list.sort(Resume.getResumeComparator);
        return list;
    }

    public int size() {
        return storage.size();
    }
}
