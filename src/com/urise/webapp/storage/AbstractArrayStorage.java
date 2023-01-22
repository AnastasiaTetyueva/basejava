package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {
    int resumeCount = 0;

    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    protected boolean getExistingSearchKey(Integer key) {
        return key >= 0;
    }

    @Override
    protected boolean getNotExistingSearchKey(Integer key) {
        return key < 0;
    }

    public void clear() {
        Arrays.fill(storage, 0, resumeCount, null);
        resumeCount = 0;
    }

    @Override
    protected void doSave(Integer key, Resume resume) {
        if (resumeCount >= storage.length) {
            System.out.println("В хранилище резюме нет свободного места!");
        }
        insertAt(resume, key);
    }

    @Override
    protected void doUpdate(Integer key, Resume resume) {
        if (getExistingSearchKey(key)) {
            replaceAt(resume, key);
        }
    }

    @Override
    protected void doDelete(Integer key) {
        if (getExistingSearchKey(key)) {
            deleteAt(key);
        }
    }

    @Override
    protected Resume doGet(Integer key) {
        if (getExistingSearchKey(key)) {
            return storage[key];
        }
        return null;
    }

    public Resume[] getAll() {
        return Arrays.copyOf(storage, resumeCount);
    }

    public int size() {
        return resumeCount;
    }


    protected  abstract void insertAt(Resume r, int index);

    protected  abstract void deleteAt(int index);

    protected abstract void replaceAt(Resume r, int index);

}
