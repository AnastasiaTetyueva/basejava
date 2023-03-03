package com.urise.webapp.storage.serializer;

import com.urise.webapp.exception.StorageException;

import java.util.function.Supplier;

@FunctionalInterface
public interface ThrowingSupplier<T> extends Supplier<T> {
    @Override
    default T get() {
        try {
            return getThrows();
        } catch (final Exception e) {
            throw new StorageException("", "", e);
        }
    }

    T getThrows() throws Exception;
}
