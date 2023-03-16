package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;

public class SqlStorage<sqlHelper> implements Storage {
    private final ConnectionFactory connectionFactory;

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.exec("DELETE FROM resume", ps -> {});
    }

    @Override
    public void update(Resume r) {
        sqlHelper.exec("UPDATE resume SET full_name=? WHERE UUID=?", ps -> {
            ps.setString(2, r.getUuid());
            ps.setString(1, r.getFullName());
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.exec("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setString(1, r.getUuid());
            ps.setString(2, r.getFullName());
        });
    }

    @Override
    public Resume get(String uuid) throws SQLException {
        ResultSet rs = sqlHelper.exec("SELECT * FROM resume r WHERE r.uuid =?", ps -> ps.setString(1, uuid));
        if (!rs.next()) {
            throw new NotExistStorageException(uuid);
        }
        return new Resume(uuid, rs.getString("full_name"));
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.exec("DELETE FROM resume uuid WHERE uuid =?", ps -> {
            ps.setString(1, uuid);
        });
    }

    @Override
    public List<Resume> getAllSorted() throws SQLException {
        List<Resume> list = new ArrayList<>();
        ResultSet rs = sqlHelper.exec("SELECT * FROM resume ORDER BY full_name", ps -> {});
        while (rs.next()) {
            list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
        }
        return list;
    }

    @Override
    public int size() throws SQLException {
        ResultSet rs = sqlHelper.exec("SELECT COUNT(*) FROM resume", ps -> {});
        if (!rs.next()) {
            throw new StorageException("Empty storage", "");
        }
        return rs.getInt(1);
    }
}
