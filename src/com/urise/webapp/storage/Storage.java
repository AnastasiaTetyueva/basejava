package com.urise.webapp.storage;

import com.urise.webapp.model.Resume;

import java.sql.SQLException;
import java.util.List;

public interface Storage {
    void clear();

    void update(Resume r);

    void save(Resume r);

    Resume get(String uuid) throws SQLException;

    void delete(String uuid);

    List<Resume> getAllSorted() throws SQLException;

    int size() throws SQLException;
}
