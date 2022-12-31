package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;


/**
 * Array based storage for Resumes
 */
public abstract class AbstractArrayStorage implements Storage {
    protected static final int STORAGE_LIMIT = 10000;

    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int resumeCount = 0;

    public final int size() {
        return resumeCount;
    }

    public final Resume get(String uuid) {
        int index = getSearchKey(uuid);
        if (index != -1) {
            return storage[index];
        }
        return null;
    }

    public final void save(Resume r) {
        int index = getSearchKey(r.getUuid());
        if (resumeCount >= storage.length) {
            System.out.println("В хранилище резюме нет свободного места!");
        } else if (index >= 0) {
            System.out.printf("В хранилище уже есть такое резюме: %s", r.getUuid());
        } else {
            insertAt(r, index);
        }
    }

    public final void delete(String uuid) {
        int index = getSearchKey(uuid);
        if (index >= 0) {
            deleteAt(index);
        }
    }

    public final void update(Resume resume) {
        int index = getSearchKey(resume.getUuid());
        if (index >= 0) {
            storage[index] = resume;
        }
    }

    protected abstract int getSearchKey(String uuid);

    protected  abstract void insertAt(Resume r, int index);

    protected  abstract void deleteAt(int index);
}
