package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    protected Object key;

    public void clear() {
        doClear();
    }

    public void save(Resume r) {
        key = getSearchKey(r.getUuid());
        if (isExist(key)) {
            throw new ExistStorageException(r.getUuid());
        }
        doSave(r);
    }

    public void update(Resume resume) {
        key = getSearchKey(resume.getUuid());
        if (!isExist(key)) {
            throw new NotExistStorageException(resume.getUuid());
        }
        doUpdate(key, resume);
    }

    public void delete(String uuid) {
        key = getSearchKey(uuid);
        if (!isExist(key)) {
            throw new NotExistStorageException(uuid);
        }
        doDelete(key);
    }

    public Resume get(String uuid) {
        key = getSearchKey(uuid);
        if (isExist(key)) {
            throw new ExistStorageException(uuid);
        }
        doGet(key);
        return null;
    }

    public Resume[] getAll() {
        doGetAll();
        return null;
    }

    public int size() {
        doSize();
        return 0;
    }

    protected abstract Object getSearchKey(String uuid);

    protected abstract boolean isExist(Object key);

    protected abstract void doClear();

    protected abstract void doSave(Resume r);

    protected abstract void doUpdate(Object key, Resume resume);

    protected abstract void doDelete(Object key);

    protected abstract Resume doGet(Object key);

    protected abstract Resume[] doGetAll();

    protected abstract void doSize();

}
