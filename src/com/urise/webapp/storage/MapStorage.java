package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.HashMap;

public class MapStorage extends AbstractStorage<MapStorageKey> {
    private HashMap<String, Resume> storage = new HashMap();

    @Override
    protected MapStorageKey getSearchKey(String uuid) {
        return new MapStorageKey(uuid);
    }

    @Override
    protected boolean isExist(MapStorageKey key) {
        return storage.containsKey(key.key);
    }

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected void doSave(MapStorageKey key, Resume resume) {
        storage.put(key.key, resume);
    }

    @Override
    protected void doUpdate(MapStorageKey key, Resume resume) {
        storage.replace(key.key, resume);
    }

    @Override
    protected void doDelete(MapStorageKey key) {
        storage.remove(key.key);
    }

    @Override
    protected Resume doGet(MapStorageKey key) {
       return storage.get(key.key);
    }

    @Override
    protected Resume[] doGetAll() {
        Resume list[] = new Resume[storage.size()];
        return storage.values().toArray(list);
    }

    @Override
    protected int doSize() {
        return storage.size();
    }
}
