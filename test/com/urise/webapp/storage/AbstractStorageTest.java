package com.urise.webapp.storage;

import com.urise.webapp.Config;
import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.ResumeTestData;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public abstract class AbstractStorageTest {
    protected static final File STORAGE_DIR = Config.get().getStorageDir();

    protected static final Path STORAGE_DIR_PATH = STORAGE_DIR.toPath();

    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected static final String UUID1 = UUID.randomUUID().toString();
    protected static final String UUID2 = UUID.randomUUID().toString();
    protected static final String UUID3 = UUID.randomUUID().toString();
    protected static final String UUID4 = UUID.randomUUID().toString();
    protected static final String UUID_NOT_EXIST = "dummy";

    protected static final String name1 = "Петров Петр";
    protected static final String name2 = "Иванов Иван";
    protected static final String name3 = "Иванов Иван";

    protected static final Resume RESUME_1;
    protected static final Resume RESUME_2;
    protected static final Resume RESUME_3;
    protected static final Resume RESUME_4;
    protected static final Resume RESUME_5;

    static {
        RESUME_1 = ResumeTestData.createResume(UUID1, name1);
        RESUME_2 = ResumeTestData.createResume(UUID2, name2);
        RESUME_3 = ResumeTestData.createResume(UUID3, name3);
        RESUME_4 = ResumeTestData.createResume(UUID4, name1);
        RESUME_5 = ResumeTestData.createResume(UUID1, name1);
    }

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(RESUME_1);
        storage.save(RESUME_2);
        storage.save(RESUME_3);
    }

    @Test
    public void size () throws Exception {
        assertSize(3);
    }

    @Test
    public void getAllSorted() throws Exception {
        Resume[] list = storage.getAllSorted().toArray(new Resume[0]);
        assertSize(3);
        Assert.assertEquals(list[0], RESUME_2);
        Assert.assertEquals(list[1], RESUME_3);
        Assert.assertEquals(list[2], RESUME_1);
    }

    @Test
    public void save() throws Exception {
        storage.save(RESUME_4);
        assertGet(RESUME_4);
        assertSize(4);
    }

    @Test
    public void get() throws Exception {
        assertGet(RESUME_1);
        assertGet(RESUME_2);
        assertGet(RESUME_3);
    }

    @Test
    public void update() throws Exception {
        storage.update(RESUME_5);
        Resume resume = storage.get(UUID1);
        Assert.assertEquals(resume, RESUME_5);
    }

    @Test(expected = NotExistStorageException.class)
    public void delete() throws Exception {
        storage.delete(UUID2);
        assertSize(2);
        assertGet(RESUME_2);
    }

    @Test
    public void clear() throws Exception {
        storage.clear();
        assertSize(0);
        Assert.assertEquals(0, storage.size());
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() {
        storage.get(UUID_NOT_EXIST);
    }

    @Test(expected = ExistStorageException.class)
    public void saveSameResumeException() throws Exception {
        storage.save(RESUME_1);
    }

    @Test(expected = NotExistStorageException.class)
    public void deleteException() throws Exception {
        storage.delete(UUID_NOT_EXIST);
    }

    private void assertSize(int quantity) throws Exception {
        Assert.assertEquals(quantity, storage.size());
    }

    private void assertGet(Resume resume) throws Exception {
        Resume r = storage.get(resume.getUuid());
        Assert.assertEquals(r, resume);
    }
}
