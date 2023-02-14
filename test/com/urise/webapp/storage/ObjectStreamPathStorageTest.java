package com.urise.webapp.storage;

import com.urise.webapp.serializer.ResumeSerializer;

public class ObjectStreamPathStorageTest extends AbstractStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new PathStorage(STORAGE_DIR_PATH, new ResumeSerializer()));
    }
}
