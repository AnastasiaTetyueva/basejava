package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import org.junit.Ignore;
import org.junit.Test;

public class MapStorageTest extends AbstractArrayStorageTest {
    public MapStorageTest() {
        super(new MapStorage());
    }

    @Ignore
    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {

    }


}
