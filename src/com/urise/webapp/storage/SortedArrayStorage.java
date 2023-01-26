package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.Comparator;

public class SortedArrayStorage extends AbstractArrayStorage {
    /*
    private static class ResumeComparator implements Comparator<Resume> {
        @Override
        public int compare(Resume o1, Resume o2) {
            return o1.getUuid().compareTo(o2.getUuid());
        }
    }
    */

    private static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getUuid);

    @Override
    protected Integer getSearchKey(String uuid) {
        Resume searchKey = new Resume(uuid);
        return Arrays.binarySearch(storage, 0, resumeCount, searchKey, RESUME_COMPARATOR);
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

