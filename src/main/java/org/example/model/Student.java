package org.example.model;

//student class, trying to switch from getting direct values from database to Student class

public class Student {

    public Student(int id, String name, String email) {
     this.id = id;
      this.name = name;
      this.email = email;
    }

    private final int id;
    private final String name;
    private final String email;

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }

        @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
