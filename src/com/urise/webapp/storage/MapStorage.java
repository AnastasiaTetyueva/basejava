package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage<String> {
    private HashMap<String, Resume> storage = new HashMap();

    @Override
    protected String getSearchKey(String uuid) {
        return new String(uuid);
    }

    @Override
    protected boolean getExistingSearchKey(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected boolean getNotExistingSearchKey(String key) {
        return !storage.containsKey(key);
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
        storage.replace(key, resume);
    }

    @Override
    protected void doDelete(String key) {
        storage.remove(key);
    }

    @Override
    protected Resume doGet(String key) {
       return storage.get(key);
    }

    public Resume[] getAll() {
        Resume list[] = new Resume[storage.size()];
        return storage.values().toArray(list);
    }

    public int size() {
        return storage.size();
    }
}
