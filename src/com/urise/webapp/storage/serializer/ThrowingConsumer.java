package com.urise.webapp.storage.serializer;

import com.urise.webapp.exception.StorageException;

import java.util.function.Consumer;

@FunctionalInterface
public interface ThrowingConsumer<T> extends Consumer<T> {
    @Override
    default void accept(T elem) {
        try {
            acceptThrows(elem);
        } catch (Exception e) {
            throw new StorageException(e);
        }
    }

    void acceptThrows(T elem) throws Exception;
}
