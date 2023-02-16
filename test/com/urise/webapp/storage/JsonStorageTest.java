package com.urise.webapp.storage;

import com.urise.webapp.storage.serializer.JsonStreamSerializer;

public class JsonStorageTest extends AbstractStorageTest {
    public JsonStorageTest() {
        super(new PathStorage(STORAGE_DIR_PATH, new JsonStreamSerializer()));
    }
}
