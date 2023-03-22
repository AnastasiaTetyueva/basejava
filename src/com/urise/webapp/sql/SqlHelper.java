package com.urise.webapp.sql;

import com.urise.webapp.exception.ExistStorageException;
import com.urise.webapp.exception.StorageException;
import org.postgresql.util.PSQLException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public <T> T exec(String query, SqlExecutor<T> perform) {
        try (Connection conn = connectionFactory.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(query);
            return perform.accept(ps);
        } catch (SQLException e) {
            throw addException(e);
        }
    }

    private StorageException addException(SQLException e) {
        PSQLException psqlException = (PSQLException) e.getCause();
        if (psqlException.getSQLState().equals("23505")) {
            throw new ExistStorageException(null);
        } else {
            throw new StorageException("", "", e);
        }
    }

}
