package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage {
    int resumeCount = 0;

    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    public final void clear() {
        Arrays.fill(storage, 0, resumeCount, null);
        resumeCount = 0;
    }

    public final void save(Resume r) {
        if (resumeCount >= storage.length) {
            System.out.println("В хранилище резюме нет свободного места!");
        }
        int index = getSearchKey(r.getUuid());
        if (index >= 0) {
            throw new ExistStorageException(r.getUuid());
        } else {
            insertAt(r, index);
        }
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

    public final void delete(String uuid) {
        int index = getSearchKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        if (index >= 0) {
            deleteAt(index);
        }
    }

    public final Resume get(String uuid) {
        int index = getSearchKey(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        if (index != -1) {
            return storage[index];
        }
        return null;
    }

    public final Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCount);
    }

    public final int size() {
        return resumeCount;
    }

    protected abstract int getSearchKey(String uuid);

    protected  abstract void insertAt(Resume r, int index);

    protected  abstract void deleteAt(int index);

    protected abstract void replaceAt(Resume r, int index);


}
