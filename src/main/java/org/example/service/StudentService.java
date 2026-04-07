package org.example.service;

import org.example.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class StudentService {

    //constructor will not be used,
    private StudentService() {
    }

    public static boolean studentExistsByEmail(String email) throws SQLException {
        String sql = """
        SELECT 1
        FROM students
        WHERE email = ?::citext
        LIMIT 1
    """;

        try (Connection conn = ConnectionFactory.getConnection("appdb");
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
}
