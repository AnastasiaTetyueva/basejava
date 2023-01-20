package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.ListIterator;

public class ListStorage extends AbstractStorage<ListStorageKey> {
    private ArrayList<Resume> storage = new ArrayList<>();

    @Override
    protected ListStorageKey getSearchKey(String uuid) {
        ListIterator<Resume> iterator = storage.listIterator();
        int index = 0;
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid().equals(uuid)) {
                return new ListStorageKey(index);
            }
            index++;
        }
        return new ListStorageKey(-1);
    }

    @Override
    protected boolean isExist(ListStorageKey key) {
        return key.index >= 0;
    }

    @Override
    protected void doClear() {
        storage.clear();
    }

    @Override
    protected void doSave(ListStorageKey key, Resume resume) {
        storage.add(resume);
    }

    @Override
    protected void doUpdate(ListStorageKey key, Resume resume) {
        storage.set(key.index, resume);
    }

    @Override
    protected void doDelete(ListStorageKey key) {
        storage.remove(key.index);
    }

    @Override
    protected Resume doGet(ListStorageKey key) {
        return storage.get(key.index);
    }

    @Override
    protected Resume[] doGetAll() {
        Resume list[] = new Resume[storage.size()];
        list = storage.toArray(list);
        return list;
    }
    @Override
    protected int doSize() {
        return storage.size();
    }

}
