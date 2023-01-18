package com.urise.webapp.storage;

import com.urise.webapp.exception.StorageException;
import org.junit.Ignore;
import org.junit.Test;

public class ListStorageTest extends AbstractArrayStorageTest{
    public ListStorageTest() {
        super(new ListStorage());
    }

    @Ignore
    @Test(expected = StorageException.class)
    public void saveOverflow() throws Exception {

    }
}
