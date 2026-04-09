package org.example.service;
import org.example.util.ConnectionFactory;
import java.sql.*;
import org.example.storage.SQLQueries;
import org.example.model.Student;

public final class StudentService {

    //constructor will not be used,// utility class
    private StudentService() {
    }

    public static boolean studentExistsByEmail(Connection connection, String email) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SQLQueries.SQLQueryGetStudentByEmail))
             {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to check student by email", e);
        }
    }

    //using Student class
    public static Student getStudentByEmail(String email) throws SQLException {
        try (Connection conn = ConnectionFactory.getConnection("appdb");
             PreparedStatement ps = conn.prepareStatement(SQLQueries.SQLQueryGetStudentByEmailAllFields)) {
            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("student_name");
                    String emailDb = rs.getString("email");
                    return new Student(id, name, emailDb);
                }
                return null;

            } catch (SQLException e) {
                throw new RuntimeException("Failed to check student by email", e);
            }
        }
    }



    public static int insertStudent(Connection connection, String studentName, String email) {
        try (PreparedStatement ps = connection.prepareStatement(SQLQueries.SQLQueryInsertStudent)) {
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

    public static int findStudentByEmail(String email) {
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

    public static int findStudentByEmail(Connection connection, String email) {
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

    //--------------------------------
    //showing all students in the database
    public static void showAllStudents() {
        System.out.println(" ");
        System.out.println("Showing all students:");
        try (Connection conn = ConnectionFactory.getConnection("appdb");
             Statement stmt = conn.createStatement()) {
            prepareInfoForAllStudents(stmt);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all students", e);
        }
    }
    //overloaded method for showAllStudents requiring a connection
    public static void showAllStudents(Connection connection) {
        System.out.println(" ");
        System.out.println("Showing all students:");
        try (Statement stmt = connection.createStatement()) {
            prepareInfoForAllStudents(stmt);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all students", e);
        }
    }

    //--------------------------------
    //showing all students in the courses
    public static void showAllStudentsInTheCourses() {
        System.out.println(" ");
        System.out.println("Showing all students in the courses:");
        try (Connection conn = ConnectionFactory.getConnection("appdb");
             Statement stmt = conn.createStatement()) {
            prepareInfoForAllStudentsInTheCourse(stmt);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all students", e);
        }
    }

    //overloaded method for showAllStudentsInTheCourses requiring a connection
    public static void showAllStudentsInTheCourses(Connection connection) {
        System.out.println(" ");
        System.out.println("Showing all students in the courses:");
        try (Statement stmt = connection.createStatement())
        {
            prepareInfoForAllStudentsInTheCourse(stmt);
        } catch (SQLException e) {
            throw new RuntimeException("Failed to get all students", e);
        }
    }
    //---------------------------------


    //private helpers
    private static void prepareInfoForAllStudents(Statement stmt) {
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static void prepareInfoForAllStudentsInTheCourse(Statement stmt) {
        try (ResultSet rs = stmt.executeQuery(SQLQueries.SQLQueryShowAllStudentsInTheCourses)) {
            System.out.printf("%-12s %-25s %-30s %-50s%n", "Student ID", "Student Name", "Student Email", "Courses (ID- Name)");
            System.out.println("---------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("student_name");
                String email = rs.getString("email");
                String courses = rs.getString("courses");

                //System.out.println("ID: " + id + ", Name: " + name + ", Email: " + email);
                System.out.printf("%-12d %-25s %-30s %-50s%n", id, name, email, courses);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



}