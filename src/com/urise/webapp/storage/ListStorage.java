package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.ArrayList;
import java.util.ListIterator;

public class ListStorage extends AbstractStorage {
    private ArrayList<Resume> storage = new ArrayList<>();

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public void save(Resume r) {
        if (findIterator(r.getUuid()) != null) {
            throw new ExistStorageException(r.getUuid());
        }
        storage.add(r);
    }

    @Override
    public void update(Resume resume) {
        findIterator(resume.getUuid()).set(resume);
    }

    @Override
    public void delete(String uuid) {
        if (findIterator(uuid) == null) {
            throw new NotExistStorageException(uuid);
        }
        findIterator(uuid).remove();
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


    private ListIterator<Resume> findIterator(String uuid) {
        ListIterator<Resume> iterator = storage.listIterator();
        while (iterator.hasNext()) {
            Resume r = iterator.next();
            if (r.getUuid().equals(uuid)) {
                return iterator;
            }
        }
        return null;
    }


}
