/**
 * Array based storage for Resumes
 */
package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    public static final int STORAGE_LIMIT = 10000;

    private final Resume[] storage = new Resume[STORAGE_LIMIT];

    private int resumeCount = 0;

    public void clear() {
        Arrays.fill(storage, 0, resumeCount, null);
        resumeCount = 0;
    }
    
    public void save(Resume r) {
        int index = getSearchKey(r.getUuid());
        if (resumeCount >= storage.length) {
            System.out.println("В хранилище резюме нет свободного места!");
        } else if (index != -1) {
            System.out.printf("В хранилище уже есть такое резюме: %s", r.getUuid());
        } else {
            storage[resumeCount++] = r;
        }
    }

    public Resume get(String uuid) {
        int index = getSearchKey(uuid);
        if (index != -1) {
            return storage[index];
        }
        return null;
    }

    public void delete(String uuid) {
        int index = getSearchKey(uuid);
        if (index != -1) {
            storage[index] = storage[resumeCount - 1];
            storage[resumeCount - 1] = null;
            resumeCount--;
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCount);
    }

    public int size() {
        return resumeCount;
    }

    public void update(Resume resume) {
        int index = getSearchKey(resume.getUuid());
        if (index != -1) {
            storage[index] = resume;
        }
    }

    private int getSearchKey(String uuid) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        System.out.printf("В хранилище нет такого резюме: %s", uuid);
        return -1;
    }

}
