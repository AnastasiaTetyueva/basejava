package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public abstract class AbstractArrayStorageTest {
    private final Storage storage;

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    private static final String UUID1 = "uuid1";
    private static final String UUID2 = "uuid2";
    private static final String UUID3 = "uuid3";
    private static final String UUID4 = "uuid4";
    private static final String UUID_NOT_EXIST = "dummy";

    private static final Resume RESUME_1;
    private static final Resume RESUME_2;
    private static final Resume RESUME_3;
    private static final Resume RESUME_4;
    private static final Resume RESUME_5;

    static {
        RESUME_1 = new Resume(UUID1);
        RESUME_2 = new Resume(UUID2);
        RESUME_3 = new Resume(UUID3);
        RESUME_4 = new Resume(UUID4);
        RESUME_5 = new Resume(UUID1);
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
    public void getAll() throws Exception {
        String[] sourceArray = { UUID1, UUID2, UUID3 };
        HashSet<String> targetSet = new HashSet<String>(Arrays.asList(sourceArray));
        Resume[] stored = storage.getAll();
        Assert.assertEquals(Arrays.stream(stored).count(), Arrays.stream(sourceArray).count());
        int count = sourceArray.length;
        for (int i = 0; i < stored.length; i++) {
            if (targetSet.contains(stored[i].getUuid())) {
                count--;
            }
        }
        Assert.assertEquals(count, 0);
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
        Assert.assertEquals(0, storage.getAll().length);
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

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume(Integer.toString(i)));
            } catch (Exception e) {
                Assert.fail("Storage is not overflow!");
            }
        }
        storage.save(new Resume(UUID_NOT_EXIST));
    }

    private void assertSize(int quantity) {
        Assert.assertEquals(quantity, storage.size());
    }

    private void assertGet(Resume resume) {
        Resume r = storage.get(resume.getUuid());
        Assert.assertEquals(r, resume);
    }
}
