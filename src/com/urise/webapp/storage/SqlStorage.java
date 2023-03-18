package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.ConnectionFactory;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage<sqlHelper> implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        ConnectionFactory connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlHelper = new SqlHelper(connectionFactory);
    }

    @Override
    public void clear() {
        sqlHelper.exec("DELETE FROM resume", ps -> {});
    }

    @Override
    public void update(Resume r) {
        sqlHelper.exec("UPDATE resume SET full_name = ? WHERE UUID = ?", ps -> {
            ps.setString(2, r.getUuid());
            ps.setString(1, r.getFullName());
        });
    }

    @Override
    public void save(Resume r) {
        try {
            sqlHelper.exec("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
            });
        } catch (Exception e) {
            throw new ExistStorageException(r.getUuid());
        }
    }

    @Override
    public Resume get(String uuid) {
        ResultSet rs = sqlHelper.exec("SELECT * FROM resume r WHERE r.uuid = ?", ps -> ps.setString(1, uuid));
        try {
            if (rs.next()) {
                return new Resume(uuid, rs.getString("full_name"));
            } else {
                throw new NotExistStorageException(uuid);
            }
        } catch (Exception e) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        try {
            ResultSet rs = sqlHelper.exec("DELETE FROM resume WHERE uuid = ?", ps -> ps.setString(1, uuid));
            if (!rs.next()) {
                throw new NotExistStorageException(uuid);
            }
        } catch (Exception e) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> list = new ArrayList<>();
        ResultSet rs = sqlHelper.exec("SELECT * FROM resume ORDER BY full_name ASC, uuid", ps -> {});
            try {
                while (rs.next()) {
                    list.add(new Resume(rs.getString("uuid").trim(), rs.getString("full_name")));
                }
            } catch (SQLException e) {
                throw new StorageException("Empty storage", "", e);
            }
        return list;
    }

    @Override
    public int size() {
        ResultSet rs = sqlHelper.exec("SELECT COUNT(*) FROM resume", ps -> {});
        try {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new StorageException("Empty storage", "", e);
        }
        return 0;
    }
}
