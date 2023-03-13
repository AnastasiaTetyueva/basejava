package com.urise.webapp.sql;

import com.urise.webapp.exception.StorageException;
import com.urise.webapp.storage.serializer.ThrowingConsumer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlHelper {
    private final ConnectionFactory connectionFactory;

    public SqlHelper(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public void exec(String query, ThrowingConsumer<PreparedStatement> perform) {
        try {
            Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            perform.accept(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
