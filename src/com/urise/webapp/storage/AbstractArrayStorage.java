package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int resumeCount = 0;

    public void clear() {
        Arrays.fill(storage, 0, resumeCount, null);
        resumeCount = 0;
    }

    public int size() {
        return resumeCount;
    }

    public Resume get(String uuid) {
        int index = getSearchKey(uuid);
        if (index != -1) {
            return storage[index];
        }
        return null;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCount);
    }

    protected abstract int getSearchKey(String uuid);
}
