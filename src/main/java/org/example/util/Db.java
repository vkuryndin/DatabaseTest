package org.example.util;


import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * Db is a small helper around DriverManager + Connection lifecycle.
 * Key idea:
 * - Services call Db.inConnection(...) for single operations
 * - Services call Db.inTransaction(...) for multi-step operations that must be atomic
  * We pass Connection into DAO methods so the Service can control transactions.
 */
public final class Db {

    //new implementation
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input = Db.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (input == null) {
                throw new RuntimeException("db.properties not found");
            }
            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load db.properties", e);
        }
    }


    private Db() {
    }

    public static String getUrl(String dbKey) {
        return PROPERTIES.getProperty("db." + dbKey + ".url");
    }

    public static String getUsername(String dbKey) {
        return PROPERTIES.getProperty("db." + dbKey + ".username");
    }

    public static String getPassword(String dbKey) {
        return PROPERTIES.getProperty("db." + dbKey + ".password");
    }

    public static String getDriver(String dbKey) {
        return PROPERTIES.getProperty("db." + dbKey + ".driver");
    }



public static void checkDBConnection (String dbKey) throws SQLException {
        try (Connection connection = ConnectionFactory.getConnection(dbKey)) {
            if (connection == null) {
                throw new SQLException("Failed to establish connection for DB key: " + dbKey);
            }
            else
                System.out.println("Connection established successfully for: " + dbKey);
        }
}
    /**
     * Opens a new connection using DriverManager.
     * Caller is responsible for closing it (or use inConnection/inTransaction).
     */
    public Connection openConnection() throws SQLException {
        return DriverManager.getConnection(AppConfig.DB_URL, AppConfig.DB_USER, AppConfig.DB_PASS);
    }

    /**
     * Executes work with a Connection (auto-commit mode).
     * Connection is opened and closed automatically.
     */
    public <T> T inConnection(SqlFunction<Connection, T> work) throws SQLException {
        try (Connection conn = openConnection()) {
            return work.apply(conn);
        }
    }

    /**
     * Same as inConnection, but without return value.
     */
    public void inConnection(SqlConsumer<Connection> work) throws SQLException {
        try (Connection conn = openConnection()) {
            work.accept(conn);
        }
    }

    /**
     * Executes work inside a DB transaction:
     * - autoCommit = false
     * - commit on success
     * - rollback on error
     */
    public <T> T inTransaction(SqlFunction<Connection, T> work) throws SQLException {
        try (Connection conn = openConnection()) {
            boolean oldAutoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);
            try {
                T result = work.apply(conn);
                conn.commit();
                return result;
            } catch (SQLException e) {
                safeRollback(conn);
                throw e;
            } catch (RuntimeException e) {
                // If your DAO/service throws RuntimeException, we still rollback
                safeRollback(conn);
                throw e;
            } finally {
                // Restore autocommit so the connection is not left in a weird state
                try {
                    conn.setAutoCommit(oldAutoCommit);
                } catch (SQLException ignore) {
                    // ignore restore errors
                }
            }
        }
    }

    /**
     * Same as inTransaction, but without return value.
     */
    public void inTransaction(SqlConsumer<Connection> work) throws SQLException {
        inTransaction(conn -> {
            work.accept(conn);
            return null;
        });
    }

    /**
     * Simple DB health check (optional).
     * Throws SQLException if DB is not reachable.
     */
    public void ping() throws SQLException {
        inConnection(conn -> {
            try (Statement st = conn.createStatement()) {
                st.execute("SELECT 1");
            }
        });
    }

    private void safeRollback(Connection conn) {
        try {
            conn.rollback();
        } catch (SQLException ignore) {
            // rollback failure is usually not actionable in learning projects
        }
    }

    /**
     * Functional interface like Function<T,R>, but it can throw SQLException.
     * This makes writing DB code with lambdas much easier.
     */
    @FunctionalInterface
    public interface SqlFunction<T, R> {
        R apply(T t) throws SQLException;
    }

    /**
     * Functional interface like Consumer<T>, but it can throw SQLException.
     */
    @FunctionalInterface
    public interface SqlConsumer<T> {
        void accept(T t) throws SQLException;
    }
}
