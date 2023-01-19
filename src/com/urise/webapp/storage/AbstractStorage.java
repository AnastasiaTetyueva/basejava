package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void clear() {

    }

    public void save(Resume r) {

    }

    public void update(Resume resume) {

    }

    public void delete(String uuid) {

    }

    public Resume get(String uuid) {
        return null;
    }

    public Resume[] getAll() {
        return new Resume[0];
    }

    public int size() {
        return 0;
    }




}
