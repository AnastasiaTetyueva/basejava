package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import org.junit.Assert;
import org.junit.Test;

public class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {
        storage.clear();
        for (int i = 0; i < AbstractArrayStorage.STORAGE_LIMIT; i++) {
            try {
                storage.save(new Resume(Integer.toString(i), "Сидоров"));
            } catch (Exception e) {
                Assert.fail("Storage is not overflow!");
            }
        }
        storage.save(new Resume(UUID_NOT_EXIST));
    }
}
