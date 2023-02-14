package com.urise.webapp.storage;

import com.urise.webapp.serializer.ResumeSerializer;

public class ObjectStreamStorageTest extends AbstractStorageTest {
    public ObjectStreamStorageTest() {
        super(new FileStorage(STORAGE_DIR, new ResumeSerializer()));
    }
}
