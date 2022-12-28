package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage{

    @Override
    public void save(Resume r) {
        if (resumeCount >= storage.length) {
            System.out.println("В хранилище резюме нет свободного места!");
            return;
        }
        int index = getSearchKey(r.getUuid());
        if (index >= 0) {
            System.out.printf("В хранилище уже есть такое резюме: %s", r.getUuid());
        } else {
            index = Math.abs(index + 1);
            storage[index] = r;
            if (resumeCount > 0) {
                for (int i = resumeCount - 1; i > index; i--) {
                    storage[i] = storage[i + 1];
                }
            }
            resumeCount++;
        }
    }

    @Override
    public void delete(String uuid) {
        int index = getSearchKey(uuid);
        if (index < 0) {
            System.out.printf("В хранилище нет такого резюме: %s", uuid);
            return;
        }
        for (int i = index; i < resumeCount - 1; i++) {
            storage[index] = storage[index + 1];
        }
        storage[resumeCount - 1] = null;
        resumeCount--;
    }

    @Override
    public void update(Resume r) {
        int index = getSearchKey(r.getUuid());
        if (index >= 0) {
            storage[index] = r;
        }
    }

    @Override
    protected int getSearchKey(String uuid) {
        Resume searchKey = new Resume();
        searchKey.setUuid(uuid);
        return Arrays.binarySearch(storage, 0, resumeCount, searchKey);
    }
}

