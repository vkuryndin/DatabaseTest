CREATE TABLE students (
                          id SERIAL PRIMARY KEY,
                          student_name TEXT NOT NULL,
                          email CITEXT NOT NULL UNIQUE
);

CREATE TABLE student_courses (
                                 student_id INT NOT NULL REFERENCES students(id) ON DELETE CASCADE,
                                 course_id  INT NOT NULL REFERENCES courses(id)  ON DELETE CASCADE,
                                 PRIMARY KEY (student_id, course_id)
);

CREATE TABLE courses (
                         id SERIAL PRIMARY KEY,
                         course_name TEXT NOT NULL UNIQUE
);
