package com.urise.webapp.storage;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.exception.StorageException;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage<sqlHelper> implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        try {
            sqlHelper.exec("DELETE FROM resume", PreparedStatement::execute);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume r) {
        try {
            sqlHelper.exec("UPDATE resume SET full_name = ? WHERE UUID = ?", ps -> {
                ps.setString(2, r.getUuid());
                ps.setString(1, r.getFullName());
                ps.executeUpdate();
            });
        } catch (SQLException e) {
            throw new StorageException("Can not to update resume", "", e);
        }
    }

    @Override
    public void save(Resume r) {
        try {
            sqlHelper.exec("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.executeUpdate();
            });
        } catch (SQLException e) {
            PSQLException psqlException = (PSQLException) e.getCause();
            if (psqlException.getSQLState().equals("23505")) {
                throw new ExistStorageException(r.getUuid());
            } else {
                throw new StorageException(e);
            }
        }
    }

    @Override
    public Resume get(String uuid) {
        ResultSet rs;
        try {
            rs = sqlHelper.exec("SELECT * FROM resume r WHERE r.uuid = ?", ps -> {
                ps.setString(1, uuid);
                ps.execute();
            });
            if (rs.next()) {
                return new Resume(uuid, rs.getString("full_name"));
            } else {
                throw new NotExistStorageException(uuid);
            }
        } catch (SQLException e) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public void delete(String uuid) {
        try {
            sqlHelper.exec("DELETE FROM resume WHERE uuid = ?", ps -> {
                ps.setString(1, uuid);
                int result = ps.executeUpdate();
                if (result == 0) {
                    throw new NotExistStorageException(uuid);
                }
            });
        } catch (Exception e) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        try {
            ResultSet rs = sqlHelper.exec("SELECT * FROM resume ORDER BY full_name ASC, uuid", PreparedStatement::execute);
            List<Resume> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new Resume(rs.getString("uuid"), rs.getString("full_name")));
            }
            return list;
        } catch (SQLException e) {
            throw new StorageException("Empty storage", "", e);
        }
    }

    @Override
    public int size() {
        try {
            ResultSet rs = sqlHelper.exec("SELECT COUNT(*) FROM resume", PreparedStatement::execute);
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }
        } catch (SQLException e) {
            throw new StorageException("Empty storage", "", e);
        }
    }
}
