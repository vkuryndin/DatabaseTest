package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public final class ConnectionFactory {

    private ConnectionFactory() {
    }

    public static Connection getConnection(String dbKey) throws SQLException {
        try {
            Class.forName(Db.getDriver(dbKey));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver not found for key: " + dbKey, e);
        }

        return DriverManager.getConnection(
                Db.getUrl(dbKey),
                Db.getUsername(dbKey),
                Db.getPassword(dbKey)
        );
    }
}
