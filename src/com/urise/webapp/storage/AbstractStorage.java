package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    public void clear() {

    }

    public final void update(Resume resume) {
        int index = getSearchKey(resume.getUuid());
        if (index < 0) {
            throw new NotExistStorageException(resume.getUuid());
        }
        if (index >= 0) {
            replaceAt(resume, index);
        }
    }

    public final void save(Resume r) {
        int index = getSearchKey(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertAt(r, index);
        }
    }

    public Resume get(String uuid) {
        return null;
    }

    public final void delete(String uuid) {
        int index = getSearchKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        if (index >= 0) {
            deleteAt(index);
        }
    }

    public Resume[] getAll() {
        return new Resume[0];
    }

    public int size() {
        return 0;
    }

    protected abstract int getSearchKey(String uuid);

    protected  abstract void insertAt(Resume r, int index);

    protected  abstract void deleteAt(int index);

    protected abstract void replaceAt(Resume r, int index);


}
