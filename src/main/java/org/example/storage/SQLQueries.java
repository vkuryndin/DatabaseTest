package org.example.storage;

//utility class for SQL strings
public class SQLQueries {

    private SQLQueries() {
    }

    public static final String SQLQueryGetStudentByEmail ="""
        SELECT id
        FROM students
        WHERE email = ?::citext
        LIMIT 1
    """;

    public static final String SQLQueryGetCourseByName = """
        SELECT id
        FROM courses
        WHERE course_name = ?::citext
        LIMIT 1
    """;
}
