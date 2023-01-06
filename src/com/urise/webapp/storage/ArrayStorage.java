/**
 * Array based storage for Resumes
 */
package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

public class ArrayStorage extends AbstractArrayStorage {

    protected int getSearchKey(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
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
