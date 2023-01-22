package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage<T> implements Storage {

    public void save(Resume resume) {
        T key = getSearchKey(resume.getUuid());
        if (getExistingSearchKey(key)) {
            throw new ExistStorageException(resume.getUuid());
        }
        doSave(key, resume);
    }

    public void update(Resume resume) {
        T key = getSearchKey(resume.getUuid());
        if (getNotExistingSearchKey(key)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        doUpdate(key, resume);
    }

    public void delete(String uuid) {
        T key = getSearchKey(uuid);
        if (getNotExistingSearchKey(key)) {
            throw new NotExistStorageException(uuid);
        }
        doDelete(key);
    }

    public Resume get(String uuid) {
        T key = getSearchKey(uuid);
        if (getNotExistingSearchKey(key)) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(key);
    }

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean getExistingSearchKey(T key);

    protected abstract boolean getNotExistingSearchKey(T key);

    protected abstract void doSave(T key, Resume resume);

    protected abstract void doUpdate(T key, Resume resume);

    protected abstract void doDelete(T key);

    protected abstract Resume doGet(T key);

}
