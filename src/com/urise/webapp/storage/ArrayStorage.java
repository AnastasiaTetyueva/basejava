/**
 * Array based storage for Resumes
 */
package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage extends AbstractArrayStorage {

    public void clear() {
        Arrays.fill(storage, 0, resumeCount, null);
        resumeCount = 0;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCount);
    }

    protected int getSearchKey(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        System.out.printf("В хранилище нет такого резюме: %s", uuid);
        return -(resumeCount + 1);
    }

    protected void insertAt(Resume r, int index) {
        storage[resumeCount] = r;
        resumeCount++;
    }

    protected void deleteAt(int index) {
        storage[index] = storage[resumeCount - 1];
        storage[resumeCount - 1] = null;
        resumeCount--;
    }

}
