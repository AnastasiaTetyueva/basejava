package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractStorageTest {
    protected final Storage storage;

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    protected static final String UUID1 = "uuid1";
    protected static final String UUID2 = "uuid2";
    protected static final String UUID3 = "uuid3";
    protected static final String UUID4 = "uuid4";
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
        RESUME_1 = new Resume(UUID1, name1);
        RESUME_2 = new Resume(UUID2, name2);
        RESUME_3 = new Resume(UUID3, name3);
        RESUME_4 = new Resume(UUID4, name1);
        RESUME_5 = new Resume(UUID1, name1);
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
        Assert.assertSame(list[0], RESUME_2);
        Assert.assertSame(list[1], RESUME_3);
        Assert.assertSame(list[2], RESUME_1);
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
        Assert.assertSame(storage.get(UUID1), RESUME_5);
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
    public void getNotExist() throws Exception {
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

    private void assertSize(int quantity) {
        Assert.assertEquals(quantity, storage.size());
    }

    private void assertGet(Resume resume) {
        Resume r = storage.get(resume.getUuid());
        Assert.assertEquals(r, resume);
    }
}
