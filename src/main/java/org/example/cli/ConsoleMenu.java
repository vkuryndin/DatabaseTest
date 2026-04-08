package org.example.cli;

import org.example.util.InputUtils;

import java.sql.SQLException;

public class ConsoleMenu {


    public void mainLoop() throws SQLException {
        while (true) {
            printMainMenu();
            String choice = InputUtils.readTrimmed("Please choose your main menu item: > ");

            if (choice == null) {
                System.out.println("Input closed. Exiting.");
                return;
            }
            switch (choice) {
                case "1" -> mainActionsLoop();
                case "2" -> statisticsLoop() ;  //TO DO
                case "3" -> printSettingsMenu(); //TO DO
                case "4" -> printHelpMenu(); //TO DO
                case "5", "q", "quit", "exit" -> {
                    return;
                }
                default -> System.out.println("Unknown option. Please try again.");
            }
        }
    }
    public void mainActionsLoop() throws SQLException {
        while (true) {
            printMainActionsMenu();
            String choice = InputUtils.readTrimmed("Please choose your main menu item: > ");

            if (choice == null) {
                System.out.println("Input closed. Exiting.");
                return;
            }
            switch (choice) {
                case "1" -> ConsoleActions.addStudent();
                case "2" -> ConsoleActions.addCourse();
                case "3" -> ConsoleActions.addStudentToCourse();
                case "4" -> ConsoleActions.removeStudentFromCourse();
                case "5" -> ConsoleActions.removeCourse();
                case "6" -> ConsoleActions.deleteStudent();
                case "7", "q", "quit", "exit" -> {
                    return;
                }
                default -> System.out.println("Unknown option. Please try again.");
            }
        }
    }

    public void statisticsLoop() throws SQLException {
        while (true) {
            printStatisticsMenu();
            String choice = InputUtils.readTrimmed("Please choose your statistics menu item: > ");
            if (choice == null) {
                System.out.println("Input closed. Exiting.");
                return;
            }
            switch (choice) {
                case "1" -> ConsoleActions.showAllStudents();
                case "2" -> ConsoleActions.showAllCourses();
                case "3" -> ConsoleActions.showAllStudentsInCourse();
                case "4", "q", "quit", "exit" -> {
                    return;
                }
                default -> System.out.println("Unknown option. Please try again.");
            }
        }

    }

    /** Prints the top-level menu options. */
    private void printMainMenu() {
        System.out.println();
        System.out.println("Main Menu");
        System.out.println("1. Main Actions");
        System.out.println("2. Statistics");
        System.out.println("3. Settings");
        System.out.println("4. Help / Examples");
        System.out.println("5. Exit"); //


    }
    /** Prints the Main actions menu options. */
    private void printMainActionsMenu(){
        // Actions menu:
        //1. Add student
        //2. Add course
        //3. Add student to the course
        //4. Remove student from the course
        //5. Remove the course
        //6. Delete the student
        //7. Return to the main menu
        System.out.println();
        System.out.println("Main Actions Menu");
        System.out.println("1. Add student");
        System.out.println("2. Add course");
        System.out.println("3. Add student to the course");
        System.out.println("4. Remove student from the course");
        System.out.println("5. Remove the course");
        System.out.println("6. Delete the student");
        System.out.println("7. Return to the main menu"); //

    }

    private void printStatisticsMenu(){
        System.out.println();
        System.out.println("Statistics Menu");
        System.out.println("1. Show all students");
        System.out.println("2. Show all courses");
        System.out.println("3. Show all students int the course");
        System.out.println("4. Return to the main menu"); //
        //Statistics menu:
        //1. Show all students
        //2. Show all courses
        //3. Show all students in the course
        //4. Return to the main menu

    }

    private void printSettingsMenu(){
        //Setting menu:
        //1. Check connection to database
        //2. Return to main menu
    }

    private void printHelpMenu(){
        //Help menu:
        //Some help info
    }

    //Menu:
    //Main menu
    //1. Main Actions
    //2. Statistics
    //3. Settings
    //4. Help
    //5. Exit

    // Actions menu:
    //1. Add student
    //2. Add course
    //3. Add student to the course
    //4. Remove student from the course
    //5. Remove the course
    //6. Delete the student
    //7. Return to main menu

    //Statistics menu:
    //1. Show all students
    //2. Show all courses
    //3. Show all students in the course
    //4. Return to main menu

    //Setting menu:
    //1. Check connection to database
    //2. Return to main menu

    //Help menu:
    //Some help info
}

