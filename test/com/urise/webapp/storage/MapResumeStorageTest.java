package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import org.junit.Ignore;
import org.junit.Test;

public class MapResumeStorageTest extends AbstractArrayStorageTest {
    public MapResumeStorageTest() {
        super(new MapResumeStorage());
    }

    @Ignore
    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {

    }
}
