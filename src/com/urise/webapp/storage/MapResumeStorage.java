package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.*;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final HashMap<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume key) {
        return storage.containsKey(key.getUuid());
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(Resume key, Resume resume) {
        storage.put(key.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume key, Resume resume) {
        storage.put(key.getUuid(), resume);
    }

    @Override
    protected void doDelete(Resume key) {
        storage.remove(key);
    }

    @Override
    protected Resume doGet(Resume key) {
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
