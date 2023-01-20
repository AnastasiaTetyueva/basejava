package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    protected ArrayStorageKey getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return new ArrayStorageKey(Arrays.binarySearch(storage, 0, resumeCount, searchKey));
    }

    @Override
    protected void insertAt(Resume r, int index) {
        if (resumeCount >= storage.length) {
            throw new StorageException("Storage overflow", r.getUuid());
        }
        index = Math.abs(index + 1);
        if (index < resumeCount) {
            for (int i = resumeCount - 1; i >= index; i--) {
                storage[i + 1] = storage[i];
            }
        }
        storage[index] = r;
        resumeCount++;
    }

    @Override
    protected void deleteAt(int index) {
        for (int i = index; i < resumeCount; i++) {
            storage[i] = storage[i + 1];
        }
        storage[resumeCount - 1] = null;
        resumeCount--;
    }

    @Override
    protected void replaceAt(Resume r, int index) {
        storage[index] = r;
    }
}

