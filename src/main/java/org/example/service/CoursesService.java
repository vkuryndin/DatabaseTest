package org.example.service;

import org.example.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CoursesService {

    //constructor will not be used,
    private CoursesService() {
    }

    public static boolean courseExistsByName(String email) throws SQLException {
        String sql = """
        SELECT 1
        FROM courses
        WHERE course_name = ?::citext
        LIMIT 1
    """;

        try (Connection conn = ConnectionFactory.getConnection("appdb");
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check course by name", e);
        }
    }

    public static int insertCourse(String courseName) {
        String sql = """
        INSERT INTO courses (course_name)
        VALUES (?)
        RETURNING id
    """;
        try (Connection conn = ConnectionFactory.getConnection("appdb");
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, courseName);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                throw new SQLException("Course insert failed: no ID returned.");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}


