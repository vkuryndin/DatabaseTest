package org.example.service;

import org.example.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.example.storage.SQLQueries;

public final class StudentService {

    //constructor will not be used,
    private StudentService() {
    }

    public static boolean studentExistsByEmail(String email) throws SQLException {

        try (Connection conn = ConnectionFactory.getConnection("appdb");
             PreparedStatement ps = conn.prepareStatement(SQLQueries.SQLQueryGetStudentByEmail)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check student by email", e);
        }
    }

    public static int insertStudent(String studentName, String email) {
        String sql = """
        INSERT INTO students (student_name, email)
        VALUES (?, ?)
        RETURNING id
    """;
        try (Connection conn = ConnectionFactory.getConnection("appdb");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, studentName);
            ps.setString(2, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                throw new SQLException("Student insert failed: no ID returned.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int findStudentbyEmail(String email) {
            try (Connection conn = ConnectionFactory.getConnection("appdb");
             PreparedStatement ps = conn.prepareStatement(SQLQueries.SQLQueryGetStudentByEmail)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                return -1; // student not found
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check student by email", e);
        }
    }

    public static int findStudentbyEmail(Connection connection, String email) {
        try {
        PreparedStatement ps = connection.prepareStatement(SQLQueries.SQLQueryGetStudentByEmail);

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                return -1; // student not found
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check student by email", e);
        }
    }

}