package ru.itmo.database.jdbc;

import ru.itmo.database.connsectionspool.C3P0DataSource;
import ru.itmo.database.entity.Course;
import ru.itmo.database.entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

public class JoinQueries {

    public static void init() {
        String initCoursesSql = "INSERT INTO tb_courses (id, title, duration, price) VALUES(100, 'Курс №1', 3.5, 64000);" +
                "INSERT INTO tb_courses (id, title, duration, price) VALUES(101, 'Курс №2', 1.5, 14000);" +
                "INSERT INTO tb_courses (id, title, duration, price) VALUES(102, 'Курс №3', 7.5, 98000);";

        try (Statement statement = C3P0DataSource.getConnection().createStatement()) {
            statement.executeUpdate(initCoursesSql);
        } catch (SQLException throwables) {
            System.out.println("Could not execute the request: " + throwables.getSQLState() + ", " + throwables.getMessage());
        }

        String initStudentsSql = "INSERT INTO tb_students (\"name\", course_id) VALUES('Иван', 100);" +
                "INSERT INTO tb_students (\"name\", course_id) VALUES('Мария', 102)";

        try (Statement statement = C3P0DataSource.getConnection().createStatement()) {
            statement.executeUpdate(initStudentsSql);
        } catch (SQLException throwables) {
            System.out.println("Could not execute the request: " + throwables.getSQLState() + ", " + throwables.getMessage());
        }
    }


    public static Student studentByName(String name) {
        String sql = "SELECT tb_students.id AS student_id, tb_students.\"name\" AS student_name, " +
                "tb_courses.id AS course_id, tb_courses.title AS course_title " +
                "FROM tb_students " +
                "JOIN tb_courses " +
                "ON tb_students.course_id = tb_courses.id " +
                "WHERE tb_students.\"name\"=" + name;
        try (Statement statement = C3P0DataSource.getConnection().createStatement()) {
            try (ResultSet data = statement.executeQuery(sql)) {
                if (data.next()) {
                    Course course = new Course();
                    course.setId(data.getInt("course_id"));
                    course.setTitle(data.getString("course_title"));
                    return new Student(
                            data.getInt("student_id"),
                            data.getString("student_name"),
                            course
                    );
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Could not execute the request: " + throwables.getSQLState() + ", " + throwables.getMessage());
        }
        return null;
    }

    public static ArrayList<Student> studentsByCourseName(String title) {
        String sql = "SELECT tb_students.id AS student_id, tb_students.name AS student_name, " +
                "tb_courses.id AS course_id, tb_courses.title AS course_title " +
                "FROM tb_students " +
                "JOIN tb_courses " +
                "ON tb_students.course_id = tb_courses.id " +
                "WHERE tb_courses.title=" + title;
        ArrayList<Student> students = new ArrayList<>();
        try (Statement statement = C3P0DataSource.getConnection().createStatement()) {
            try (ResultSet data = statement.executeQuery(sql)) {
                while (data.next()) {
                    Course course = new Course();
                    course.setId(data.getInt("course_id"));
                    course.setTitle(data.getString("course_title"));
                    Student student = new Student(
                            data.getInt("student_id"),
                            data.getString("student_name"),
                            course
                    );
                    students.add(student);
                }
                return students;
            }
        } catch (SQLException throwables) {
            System.out.println("Could not execute the request: " + throwables.getSQLState() + ", " + throwables.getMessage());
        }
        return null;
    }

    public static ArrayList<Course> emptyCourses() {
        String sql = "SELECT tb_courses.id AS id, " +
                "tb_courses.title AS title, " +
                "tb_courses.price AS price, " +
                "tb_courses.duration AS duration " +
                "FROM tb_courses " +
                "LEFT JOIN tb_students " +
                "ON tb_students.course_id = tb_courses.id " +
                "WHERE tb_courses.id IS NULL";

        ArrayList<Course> courses = new ArrayList<>();
        try (Statement statement = C3P0DataSource.getConnection().createStatement()) {
            try (ResultSet data = statement.executeQuery(sql)) {
                while (data.next()) {
                    Course course = new Course();
                    course.setId(data.getInt("id"));
                    course.setTitle(data.getString("title"));
                    course.setDuration(data.getDouble("duration"));
                    course.setPrice(data.getInt("price"));
                    courses.add(course);
                }
                return courses;
            }
        } catch (SQLException throwables) {
            System.out.println("Could not execute the request: " + throwables.getSQLState() + ", " + throwables.getMessage());
        }
        return null;
    }

}
