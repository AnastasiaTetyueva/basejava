package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage extends AbstractStorage<ArrayStorageKey> {
    int resumeCount = 0;

    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];

    @Override
    protected boolean isExist(ArrayStorageKey key) {
        return key.index >= 0;
    }

    @Override
    protected void doClear() {
        Arrays.fill(storage, 0, resumeCount, null);
        resumeCount = 0;
    }
    @Override
    protected void doSave(ArrayStorageKey key, Resume resume) {
        if (resumeCount >= storage.length) {
            System.out.println("В хранилище резюме нет свободного места!");
        }
        insertAt(resume, key.index);
    }
    @Override
    protected void doUpdate(ArrayStorageKey key, Resume resume) {
        if (isExist(key)) {
            replaceAt(resume, key.index);
        }
    }
    @Override
    protected void doDelete(ArrayStorageKey key) {
        if (isExist(key)) {
            deleteAt(key.index);
        }
    }
    @Override
    protected Resume doGet(ArrayStorageKey key) {
        if (key.index != -1) {
            return storage[key.index];
        }
        return null;
    }

    @Override
    protected Resume[] doGetAll() {
        return Arrays.copyOf(storage, resumeCount);
    }

    @Override
    protected int doSize() {
        return resumeCount;
    }


    protected  abstract void insertAt(Resume r, int index);

    protected  abstract void deleteAt(int index);

    protected abstract void replaceAt(Resume r, int index);


}
