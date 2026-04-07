package org.example.cli;

import org.example.service.StudentService;
import org.example.util.InputUtils;
import org.example.service.StudentService;
import org.example.service.CoursesService;

import java.sql.SQLException;

public class ConsoleActions {

    //no constructor
    private ConsoleActions() {
    }

    public static void addStudent() throws SQLException {
        while (true) {
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
            System.out.println(" ");
            System.out.println("Add course or press q(Q) to return:");
            String courseName = InputUtils.readTrimmed("Enter course name: ");
            if (courseName.equals("q") || courseName.equals("Q")) {
                return;
            }
            if (!CoursesService.courseExistsByName(courseName)) {
                int id = CoursesService.insertCourse(courseName);
                if (id !=0) {
                    System.out.println("Course added successfully with ID: " + id);
                }
                return;
            } else {
                System.out.println("Course with this name already exists");
            }
        }
    }
    public static void addStudentToCourse() {
        System.out.println("Add student to the course");
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
