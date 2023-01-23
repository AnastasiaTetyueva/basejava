package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage<T> implements Storage {

    public void save(Resume resume) {
        T key = getSearchKey(resume.getUuid());
        getExistingSearchKey(key, resume.getUuid());
        doSave(key, resume);
    }

    public void update(Resume resume) {
        T key = getSearchKey(resume.getUuid());
        getNotExistingSearchKey(key, resume.getUuid());
        doUpdate(key, resume);
    }

    public void delete(String uuid) {
        T key = getSearchKey(uuid);
        getNotExistingSearchKey(key, uuid);
        doDelete(key);
    }

    public Resume get(String uuid) {
        T key = getSearchKey(uuid);
        getNotExistingSearchKey(key, uuid);
        return doGet(key);
    }

    protected void getExistingSearchKey(T key, String uuid) {
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
    }

    protected void getNotExistingSearchKey(T key, String uuid) {
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
    }

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean isExist(T key);

    protected abstract void doSave(T key, Resume resume);

    protected abstract void doUpdate(T key, Resume resume);

    protected abstract void doDelete(T key);

    protected abstract Resume doGet(T key);

}
