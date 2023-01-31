package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;

public abstract class AbstractStorage<T> implements Storage {
    private static final Logger LOG = Logger.getLogger(AbstractStorage.class.getName());

    public static final Comparator<Resume> RESUME_COMPARATOR = Comparator.comparing(Resume::getFullName).thenComparing(Resume::getUuid);

    public void save(Resume resume) {
        LOG.info("Save " + resume);
        T key = getExistingSearchKey(resume.getUuid());
        doSave(key, resume);
    }

    public void update(Resume resume) {
        LOG.info("Update " + resume);
        T key = getNotExistingSearchKey(resume.getUuid());
        doUpdate(key, resume);
    }

    public void delete(String uuid) {
        LOG.info("Delete " + uuid);
        T key = getNotExistingSearchKey(uuid);
        doDelete(key);
    }

    public Resume get(String uuid) {
        LOG.info("Get " + uuid);
        T key = getNotExistingSearchKey(uuid);
        return doGet(key);
    }

    public List<Resume> getAllSorted() {
        LOG.info("getAllSorted");
        List<Resume> list = doCopyAll();
        list.sort(RESUME_COMPARATOR);
        return list;
    }

    protected T getExistingSearchKey(String uuid) {
        T key = getSearchKey(uuid);
        if (isExist(key)) {
            LOG.warning("Resume " + uuid + " already exist");
            throw new ExistStorageException(uuid);
        }
        return key;
    }

    protected T getNotExistingSearchKey(String uuid) {
        T key = getSearchKey(uuid);
        if (!isExist(key)) {
            LOG.warning("Resume " + uuid + " not exist");
            throw new NotExistStorageException(uuid);
        }
        return key;
    }

    protected abstract List<Resume> doCopyAll();

    protected abstract T getSearchKey(String uuid);

    protected abstract boolean isExist(T key);

    protected abstract void doSave(T key, Resume resume);

    protected abstract void doUpdate(T key, Resume resume);

    protected abstract void doDelete(T key);

    protected abstract Resume doGet(T key);

}
