package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

public class ListStorage extends AbstractStorage<Integer> {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected Integer getSearchKey(String uuid) {
        ListIterator<Resume> iterator = storage.listIterator();
        int index = 0;
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid().equals(uuid)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    protected boolean isExist(Integer key) {
       return key >= 0;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    protected void doSave(Integer key, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void doUpdate(Integer key, Resume resume) {
        storage.set(key, resume);
    }

    @Override
    protected void doDelete(Integer key) {
        storage.remove(key.intValue());
    }

    @Override
    protected Resume doGet(Integer key) {
        return storage.get(key);
    }

    @Override
    protected List<Resume> doCopyAll() {
        storage.sort(RESUME_COMPARATOR);
        return storage;
    }

    public int size() {
        return storage.size();
    }

}
