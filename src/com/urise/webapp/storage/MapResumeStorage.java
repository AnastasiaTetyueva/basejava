package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapResumeStorage extends AbstractStorage<Resume> {
    private final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Resume getSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected boolean isExist(Resume key) {
        return key != null;
    }

    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(Resume key, Resume resume) {
        storage.put(resume.getUuid(), resume);
    }

    @Override
    protected void doUpdate(Resume key, Resume resume) {
        storage.put(key.getUuid(), resume);
    }

    @Override
    protected void doDelete(Resume key) {
        storage.remove(key.getUuid());
    }

    @Override
    protected Resume doGet(Resume key) {
        return storage.get(key.getUuid());
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
