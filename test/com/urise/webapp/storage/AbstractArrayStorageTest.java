package com.urise.webapp.storage;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.Resume;

public abstract class AbstractArrayStorageTest {
    private Storage storage;

    private AbstractArrayStorageTest() {
        storage = new ArrayStorage();
    }

    private static final String UUID_1 = "uuid1";
    private static final String UUID_2 = "uuid2";
    private static final String UUID_3 = "uuid3";
    private static final String UUID_4 = "uuid4";

    @Before
    public void setUp() throws Exception {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
    }

    @Test
    public void size () throws Exception {
        Assert.assertEquals(3, storage.size());
    }

    @Test
    public void clear() throws Exception {
        Assert.assertEquals(0, storage.size());
    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void getAll() throws Exception {

    }

    @Test
    public void save() throws Exception {
        storage.save(new Resume(UUID_4));
        Assert.assertTrue(storage.get("uuid4").equals("uuid4"));
    }

    @Test
    public void delete() throws Exception {
        storage.delete("uuid2");
        Assert.assertNull("uuid2");
    }

    @Test
    public void get() throws Exception {
        Assert.assertTrue(storage.get("uuid1").equals("uuid1"));
    }

    @Test(expected = NotExistStorageException.class)
    public void getNotExist() throws Exception {
        storage.get("dummy");
    }

}
