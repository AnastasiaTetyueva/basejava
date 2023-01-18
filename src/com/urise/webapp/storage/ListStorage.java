package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.ListIterator;

public class ListStorage extends AbstractStorage {
    ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume get(String uuid) {
        ListIterator<Resume> iterator = storage.listIterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid().equals(uuid)) {
                return r;
            }
        }
        throw new NotExistStorageException(uuid);
    }

    @Override
    public Resume[] getAll() {
        Resume list[] = new Resume[storage.size()];
        list = storage.toArray(list);
        return list;
    }

    @Override
    public int size() {
        return storage.size();
    }

    @Override
    protected int getSearchKey(String uuid) {
        ListIterator<Resume> iterator = storage.listIterator();
        int counter = 0;
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid().equals(uuid)) {
                return counter;
            }
            counter++;
        }
        return -(counter + 1);
    }

    @Override
    protected void insertAt(Resume r, int index) {
        storage.add(r);
    }

    @Override
    protected void deleteAt(int index) {
        storage.remove(index);
    }

    @Override
    protected void replaceAt(Resume r, int index) {
        storage.set(index, r);
    }

}
