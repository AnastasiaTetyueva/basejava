/**
 * Array based storage for Resumes
 */
package com.storages;

import com.models.Resume;

import java.util.Arrays;

public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int resumeCount = 0;

    public void clear() {
        for (int i = 0; i < resumeCount; i++) {
            storage[i] = null;
        }
        resumeCount = 0;
    }
    
    public void save(Resume r) {
        if (resumeCount >= storage.length) {
            System.out.println("В хранилище резюме нет свободного места!");
        } else {
            storage[resumeCount++] = r;
        }
    }

    public Resume get(String uuid) {
        for (int i = 0; i < storage.length; i++) {
            if (storage[i].uuid.equals(uuid)) {
                return storage[i];
            }
        }
        return null;
    }

    public void delete(String uuid) {
        int index = -1;
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].uuid.equals(uuid)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("Резюме не найдено");
        }
        for (int i = index; i < resumeCount - 1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[resumeCount - 1] = null;
        resumeCount--;
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
}
