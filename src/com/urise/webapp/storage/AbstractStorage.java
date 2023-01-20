package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage<T extends Object> implements Storage {

    public void clear() {
        doClear();
    }

    public void save(Resume resume) {
        T key = getSearchKey(resume.getUuid());
        if (isExist(key)) {
            throw new ExistStorageException(resume.getUuid());
        }
        doSave(key, resume);
    }

    public void update(Resume resume) {
        T key = getSearchKey(resume.getUuid());
        if (!isExist(key)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        doUpdate(key, resume);
    }

    public void delete(String uuid) {
        T key = getSearchKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        doDelete(key);
    }

    public Resume get(String uuid) {
        T key = getSearchKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(key);
    }

    public Resume[] getAll() {
        return doGetAll();
    }

    public int size() {
        return doSize();
    }

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean isExist(T key);

    protected abstract void doClear();

    protected abstract void doSave(T key, Resume resume);

    protected abstract void doUpdate(T key, Resume resume);

    protected abstract void doDelete(T key);

    protected abstract Resume doGet(T key);

    protected abstract Resume[] doGetAll();

    protected abstract int doSize();

}
