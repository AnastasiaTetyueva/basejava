package com.urise.webapp.storage;

import com.urise.webapp.exception.NotExistStorageException;
import com.urise.webapp.model.*;
import com.urise.webapp.sql.SqlHelper;

import java.sql.*;
import java.util.*;
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
                deleteContacts(conn, r);
                addSqlContacts(conn, r);
                deleteSections(conn, r);
                addSqlSections(conn, r);
                return null;
        });
    }

    @Override
    public void save(Resume r) {
        sqlHelper.transactionalExecute(conn -> {
            try (PreparedStatement ps = conn.prepareStatement("INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
                ps.setString(1, r.getUuid());
                ps.setString(2, r.getFullName());
                ps.execute();
            }
            addSqlContacts(conn, r);
            addSqlSections(conn, r);
            return null;
        });
    }

    @Override
    public Resume get(String uuid) {
        Resume resume = sqlHelper.exec("" +
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
                    do {
                        getContact(rs, r);
                    } while (rs.next());
                    return r;
                });
        return sqlHelper.exec("" +
                        "    SELECT * FROM section WHERE resume_uuid = ?",
                ps -> {
                    ps.setString(1, resume.getUuid());
                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        getSection(rs, resume);
                    }
                    return resume;
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
        return sqlHelper.transactionalExecute(conn -> {
            Map<String, Resume> resumes = new LinkedHashMap<>();
            try (PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume")) {
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String uuid = rs.getString("uuid");
                    Resume resume = new Resume(uuid, rs.getString("full_name"));
                    resumes.put(uuid, resume);
                }
                ps.execute();
            }
            try (PreparedStatement ps1 = conn.prepareStatement("SELECT * FROM contact")) {
                ResultSet rs1 = ps1.executeQuery();
                while (rs1.next()) {
                    getContact(rs1, resumes.get(rs1.getString("resume_uuid")));
                }
            }
            try (PreparedStatement ps2 = conn.prepareStatement("SELECT * FROM section")) {
                ResultSet rs2 = ps2.executeQuery();
                while (rs2.next()) {
                    getSection(rs2, resumes.get(rs2.getString("resume_uuid")));
                }
            }
            return resumes.values().stream().sorted(AbstractStorage.RESUME_COMPARATOR).collect(Collectors.toList());
        });
    }

    @Override
    public int size() {
        return sqlHelper.exec("SELECT COUNT(*) FROM resume", ps -> {
            ResultSet rs = ps.executeQuery();
            return rs.next() ? rs.getInt(1) : 0;
        });
    }

    private void getContact(ResultSet rs, Resume r) throws SQLException {
        String type = rs.getString("type");
        if (type != null) {
            r.setContact(ContactType.valueOf(type), rs.getString("value"));
        }
    }

    private void getSection(ResultSet rs, Resume r) throws SQLException {
        SectionType type = SectionType.valueOf(rs.getString("type"));
        switch (type) {
            case PERSONAL:
            case OBJECTIVE:
                TextSection resultT = new TextSection();
                resultT.setText(rs.getString("value"));
                r.setSection(type, resultT);
                break;
            case ACHIEVEMENT:
            case QUALIFICATIONS:
                ListSection resultL = new ListSection();
                resultL.getList().addAll(Arrays.asList(rs.getString("value").split("\n")));
                r.setSection(type, resultL);
                break;
            default: break;
        }
    }

    private void addSqlContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO contact (value, type, resume_uuid) " +
                "VALUES (?,?,?)")) {
            for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
                ps.setString(3, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(1, e.getValue());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void addSqlSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("INSERT INTO section (value, type, resume_uuid) " +
                "VALUES (?,?,?)")) {
            for (Map.Entry<SectionType, AbstractSection> e : r.getSections().entrySet()) {
                ps.setString(3, r.getUuid());
                ps.setString(2, e.getKey().name());
                ps.setString(1, e.getValue().toString());
                ps.addBatch();
            }
            ps.executeBatch();
        }
    }

    private void deleteContacts(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM contact WHERE resume_uuid = ?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

    private void deleteSections(Connection conn, Resume r) throws SQLException {
        try (PreparedStatement ps = conn.prepareStatement("DELETE FROM section WHERE resume_uuid = ?")) {
            ps.setString(1, r.getUuid());
            ps.execute();
        }
    }

}
