package org.example.cli;

import org.example.service.StudentService;
import org.example.util.ConnectionFactory;
import org.example.util.InputUtils;
import org.example.service.StudentService;
import org.example.service.CoursesService;

import java.sql.Connection;
import java.sql.SQLException;

public class ConsoleActions {

    //no constructor
    private ConsoleActions() {
    }

    public static void addStudent() throws SQLException {
        while (true) {
            StudentService.showAllStudents();
            System.out.println(" ");
            System.out.println("Add student or press q(Q) to return:");
            String name = InputUtils.readTrimmed("Enter student name: ");
            if (name.equals("q") || name.equals("Q")) {
                return;
            }

            String email = InputUtils.readEmail("Enter student email: ");

            //checking if student exists
            if (!StudentService.studentExistsByEmail(email)) {
                int id = StudentService.insertStudent(name, email);
                if (id !=0) {
                    System.out.println("Student added successfully with ID: " + id);
                }
                return;
            } else {
                System.out.println("Student with this email already exists");
            }
        }
    }
    public static void addCourse() throws SQLException {
        while (true) {
            CoursesService.showAllCourses();
            System.out.println(" ");
            System.out.println("Add course or press q(Q) to return:");
            String courseName = InputUtils.readTrimmed("Enter course name: ");
            if (courseName.equals("q") || courseName.equals("Q")) {
                return;
            }
            if (!CoursesService.courseExistsByName(courseName)) {
                int id = CoursesService.insertCourse(courseName);
                if (id !=-1) {
                    System.out.println("Course added successfully with ID: " + id);
                }
                return;
            } else {
                System.out.println("Course with this name already exists");
            }
        }
    }
    public static void addStudentToCourse() throws SQLException {
        System.out.println("Add student to the course");
        String studentEmail = InputUtils.readTrimmed("Enter student email: ");
        try (Connection connection = ConnectionFactory.getConnection("appdb")) {
            int studentID = StudentService.findStudentbyEmail(connection, studentEmail);
            if (studentID == -1) {
                System.out.println("Student with e-mail " + studentEmail + " not found");
                return;
            }
            System.out.println("Student with e-mail " + studentEmail + " found with ID:  " + studentID);
            String courseName = InputUtils.readTrimmed("Enter course name: ");
            int courseID = CoursesService.findCoursebyName(connection, courseName);
            if (courseID == -1) {
                System.out.println("Course with name " + courseName + " not found");
                return;
            }
            System.out.println("Course " + courseName + " found with ID: " + courseID);
            //Add to the course here...
            if (CoursesService.addToTheCourse(connection, studentID, courseID)) {
                System.out.println("Student added to the course successfully");
            } else {
                System.out.println("Failed to add student to the course");
            }
        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }
    public static void removeStudentFromCourse(){
        System.out.println("Remove student from the course");
    }
    public static void removeCourse(){
        System.out.println("Remove the course");
    }
    public static void deleteStudent(){
        System.out.println("Delete the student");
    }

    public static void showAllStudents(){
        StudentService.showAllStudents();
    }
    public static void showAllCourses(){
        CoursesService.showAllCourses();
    }

    public static void showAllStudentsInCourse(){
        System.out.println("Show all students in the course");
    }
        /*
         System.out.println("1. Add student");
        System.out.println("2. Add course");
        System.out.println("3. Add student to the course");
        System.out.println("4. Remove student from the course");
        System.out.println("5. Remove the course");
        System.out.println("6. Delete the student");
        System.out.println("7. Return to the main menu"); //
     */

}
