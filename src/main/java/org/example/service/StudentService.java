package org.example.service;

import org.example.util.ConnectionFactory;

import java.sql.*;

import org.example.storage.SQLQueries;

public final class StudentService {

    //constructor will not be used,// utility class
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
    public static void showAllStudents() {
        System.out.println(" ");
        System.out.println("Showing all students:");
        try (Connection conn = ConnectionFactory.getConnection("appdb");
             Statement stmt = conn.createStatement())
             {
            try (ResultSet rs = stmt.executeQuery(SQLQueries.SQLQueryShowAllStudents)) {
                System.out.printf("%-5s %-25s %-30s%n", "ID", "Name", "Email");
                System.out.println("--------------------------------------------------------------");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("student_name");
                    String email = rs.getString("email");

                    //System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
                    System.out.printf("%-5d %-25s %-30s%n", id, name, email);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all students", e);
        }
    }


}