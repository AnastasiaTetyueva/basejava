package com.urise.webapp.storage;

import com.urise.webapp.ResumeTestData;
import com.urise.webapp.exception.StorageException;
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
                storage.save(ResumeTestData.createResume(Integer.toString(i), "Сидоров".concat(Integer.toString(i))));
            } catch (Exception e) {
                Assert.fail("Storage is not overflow!");
            }
        }
        storage.save(ResumeTestData.createResume(UUID_NOT_EXIST, "Dummy"));
    }
}
