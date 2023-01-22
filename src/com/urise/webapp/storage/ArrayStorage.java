/**
 * Array based storage for Resumes
 */
package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected Integer getSearchKey(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void insertAt(Resume r, int index) {
        if (resumeCount >= storage.length) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        replaceAt(r, resumeCount);
        resumeCount++;
    }

    @Override
    protected void deleteAt(int index) {
        replaceAt(storage[resumeCount - 1], index);
        replaceAt(null, resumeCount - 1);
        resumeCount--;
    }

    @Override
    protected void replaceAt(Resume r, int index) {
        storage[index] = r;
    }
}
