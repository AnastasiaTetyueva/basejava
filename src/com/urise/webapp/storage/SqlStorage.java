package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.ContactType;
import com.urise.webapp.model.Resume;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SqlStorage<sqlHelper> implements Storage {

    private final SqlHelper sqlHelper;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        sqlHelper = new SqlHelper(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
    }

    @Override
    public void clear() {
        sqlHelper.exec("DELETE FROM resume", PreparedStatement::executeUpdate);
    }

    @Override
    public void update(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("UPDATE resume SET full_name = ? " +
                            "WHERE UUID = ?")) {
                        ps.setString(2, r.getUuid());
                        ps.setString(1, r.getFullName());
                        if (ps.executeUpdate() == 0) {
                            throw new NotExistStorageException(r.getUuid());
                        }
                    }
                    addSqlContact(conn,"update", r);
                    return null;
                }
        );
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
                    try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                        ps.setString(1, r.getUuid());
                        ps.setString(2, r.getFullName());
                        ps.execute();
                    }
                    addSqlContact(conn,"save", r);
                    return null;
                }
        );
    }

    @Override
    public Resume get(String uuid) {
        return sqlHelper.exec("" +
                        "    SELECT * FROM resume r " +
                        " LEFT JOIN contact c " +
                        "        ON r.uuid = c.resume_uuid " +
                        "     WHERE r.uuid = ? ",
                ps -> {
                    ps.setString(1, uuid);
                    ResultSet rs = ps.executeQuery();
                    if (!rs.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    Resume r = new Resume(uuid, rs.getString("full_name"));
                    getContacts(r, rs);
                    return r;
                });
    }

    @Override
    public void delete(String uuid) {
        sqlHelper.exec("DELETE FROM resume WHERE uuid = ?", ps -> {
            ps.setString(1, uuid);
            if (ps.executeUpdate() == 0) {
                throw new NotExistStorageException(uuid);
            }
            return null;
        });
    }

    @Override
    public List<Resume> getAllSorted() {
        Map<String, Resume> map = sqlHelper.exec("SELECT * FROM resume",
                ps -> {
                    Map<String, Resume> m = new HashMap<>();
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Resume r = new Resume(rs.getString("uuid"), rs.getString("full_name"));
                        m.put(rs.getString("uuid"), r);
                    }
                    return m;
                });
        for (Map.Entry<String, Resume> entry : map.entrySet()) {
            sqlHelper.exec("SELECT * FROM contact WHERE resume_uuid = ?", ps -> {
                ps.setString(1, entry.getKey());
                ResultSet rs = ps.executeQuery();
                if (!rs.next()) {
                    return null;
                }
                getContacts(entry.getValue(), rs);
                return null;
            });
        }
        return new ArrayList<Resume>(map.values()).stream().sorted(AbstractStorage.RESUME_COMPARATOR).collect(Collectors.toList());
    }

    @Override
    public int size() {
        return sqlHelper.exec("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void getContacts(Resume r, ResultSet rs) throws SQLException {
        do {
            String value = rs.getString("value");
            ContactType type = ContactType.valueOf(rs.getString("type"));
            r.setContact(type, value);
        } while (rs.next());
    }

    private void addSqlContact(Connection conn, String command, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement(
                (command.equals("save")) ?
                        "INSERT INTO contact (value, type, resume_uuid) " +
                                "VALUES (?,?,?)" :
                        "UPDATE contact SET value = ? " +
                                "WHERE type = ? " +
                                "AND resume_uuid = ?"
        )) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(3, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(1, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }


}
