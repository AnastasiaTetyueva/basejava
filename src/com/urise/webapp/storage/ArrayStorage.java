/**
 * Array based storage for Resumes
 */
package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int resumeCount = 0;

    public void clear() {
        Arrays.fill(storage, 0, resumeCount, null);
        resumeCount = 0;
    }
    
    public void save(Resume r) {
        if (resumeCount >= storage.length) {
            System.out.println("В хранилище резюме нет свободного места!");
            return;
        }
        Resume current = this.get(r.getUuid());
        if (current != null) {
            System.out.printf("В хранилище уже есть такое резюме: %s", current.getUuid());
            return;
        }
        storage[resumeCount++] = r;
    }

    public Resume get(String uuid) {
        if (resumeCount == 0) {
            System.out.println("Хранилище пустое!");
            return null;
        }
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return storage[i];
            }
        }
        System.out.printf("В хранилище нет такого резюме: %s", uuid);
        return null;
    }

    public void delete(String uuid) {
        int index = -1;
        if (resumeCount == 0) {
            System.out.println("Хранилище пустое!");
            return;
        }
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.printf("Резюме не найдено: %s", uuid);
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

    public void update(Resume resume) {
        for (int i = 0; i < resumeCount; i++) {
            if (storage[i].getUuid().equals(resume.getUuid())) {
                storage[i] = resume;
                return;
            }
        }
        System.out.printf("В хранилище нет такого резюме: %s", resume.getUuid());
    }

}
