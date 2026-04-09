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

    public static final String SQLQueryShowAllStudents = """
        SELECT *
        FROM students
    """;

    public static final String SQLQueryShowAllCourses = """
        SELECT *
        FROM courses
    """;

    public static final String SQLQueryGetStudentByEmailAllFields ="""
        SELECT id, student_name, email
        FROM students
        WHERE email = ?::citext
        LIMIT 1
    """;

    public static final String SQLQueryInsertStudent = """
                    INSERT INTO students (student_name, email)
                    VALUES (?, ?)
                    RETURNING id
            """;

    public static final String SQLQueryShowAllStudentsInTheCourses = """
            SELECT
                s.id,
                s.student_name,
                s.email,
                COALESCE(
                    STRING_AGG(
                        c.id || ' - ' || c.course_name,
                        ', '
                        ORDER BY c.id
                    ),
                    ''
                ) AS courses
            FROM students s
            LEFT JOIN student_courses sc ON sc.student_id = s.id
            LEFT JOIN courses c ON c.id = sc.course_id
            GROUP BY s.id, s.student_name, s.email
            ORDER BY s.id;
            """;
}
