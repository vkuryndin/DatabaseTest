package org.example.service;

import org.example.util.ConnectionFactory;

import java.sql.*;

import org.example.storage.SQLQueries;

public class CoursesService {

    //constructor will not be used,
    private CoursesService() {
    }

    public static boolean courseExistsByName(String email) throws SQLException {
          try (Connection conn = ConnectionFactory.getConnection("appdb");
             PreparedStatement ps = conn.prepareStatement(SQLQueries.SQLQueryGetCourseByName)) {

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
    public static int findCoursebyName (String email) {
        try (Connection conn = ConnectionFactory.getConnection("appdb");
             PreparedStatement ps = conn.prepareStatement(SQLQueries.SQLQueryGetCourseByName)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                return -1; // course not found
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check course by name", e);
        }
    }

    //overloaded method for findCourseByName requiring a connection
    public static int findCoursebyName (Connection connection, String email) {
        try {
            PreparedStatement ps = connection.prepareStatement(SQLQueries.SQLQueryGetCourseByName);

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                return -1; // course not found
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check course by name", e);
        }
    }

    public static boolean addToTheCourse (Connection connection, int studentId, int courseId) {
        String sql = """
                INSERT INTO student_courses (student_id, course_id)
                        VALUES (?, ?)
                        ON CONFLICT DO
                NOTHING;
        """;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, studentId);
            ps.setInt(2, courseId);

            int affectedRows = ps.executeUpdate();
            return affectedRows == 1;

        } catch (SQLException e) {
            throw new RuntimeException("Failed to add student to course", e);
        }
    }

    public static void showAllCourses() {
        System.out.println(" ");
        System.out.println("Showing all courses:");
        try (Connection conn = ConnectionFactory.getConnection("appdb");
             Statement stmt = conn.createStatement())
        {
            try (ResultSet rs = stmt.executeQuery(SQLQueries.SQLQueryShowAllCourses)) {
                System.out.printf("%-5s %-30s%n", "ID", "Name");
                System.out.println("---------------------------------------------");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("course_name");

                    System.out.printf("%-5d %-30s%n", id, name);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all courses", e);
        }
    }

}


