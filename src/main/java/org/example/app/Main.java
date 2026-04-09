package org.example.app;
import org.example.cli.ConsoleMenu;
import org.example.util.Db;

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        //System.out.printf("Hello and welcome!");

        //for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
        //    System.out.println("i = " + i);
        //}

        //testConnection();
        //getCourses();
        System.out.println("========================================");
        System.out.println("Postgres Database Demo Application");
        System.out.println("========================================");

        //To DO :: check here, whether the database connection is ok.

        Db.checkDBConnection("appdb");
        ConsoleMenu menu = new ConsoleMenu();
        menu.mainLoop();
    }

    /*
    //Think about it later
    private static void testConnection(){
        String url = "jdbc:postgresql://192.168.179.129:5432/appdb";
        String user = "appuser";
        String pass = "Gefsbizd19!"; // лучше потом вынести в переменные окружения

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement("SELECT now()");
             ResultSet rs = ps.executeQuery()) {

            rs.next();
            System.out.println("DB time: " + rs.getTimestamp(1));
        }
        catch (SQLException e) {
            System.out.println("DB error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void getCourses() {
        String url = "jdbc:postgresql://192.168.179.129:5432/appdb";
        String user = "appuser";
        String pass = "Gefsbizd19!";

        String sql = """
            SELECT courses.course_name
            FROM students
            JOIN student_courses ON student_courses.student_id = students.id
            JOIN courses ON courses.id = student_courses.course_id
            WHERE students.email = ?::citext
            ORDER BY courses.course_name
        """;

        try (Connection conn = DriverManager.getConnection(url, user, pass);
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "ivan@example.com"); // поставь email Иванова из БД
            System.out.println("PS = " + ps);

            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("RS = " + rs);
                int rows = 0;

                while (rs.next()) {
                    rows++;
                    System.out.println(rs.getString("course_name"));
                    System.out.println("row " + rows + ": " + rs.getString(1));
                }
                System.out.println("TOTAL ROWS = " + rows);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    } */
}