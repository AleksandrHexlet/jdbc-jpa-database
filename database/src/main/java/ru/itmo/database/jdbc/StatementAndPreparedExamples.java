package ru.itmo.database.jdbc;

import ru.itmo.database.entity.Course;

import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;

import static ru.itmo.database.jdbc.Settings.*;

public class StatementAndPreparedExamples {
    private static void loadDriver() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver is not found");
        }
    }

    public static boolean insert(Course course) {
        String insertSQL = "INSERT INTO tb_courses (title, duration, price) VALUES(?, ?, ?)";
        loadDriver();
        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PWD)) {
            try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                statement.setString(1, course.getTitle());
                statement.setDouble(2, course.getDuration());
                statement.setInt(3, course.getPrice());
                return statement.executeUpdate() != 0;
            }
        } catch (SQLException throwables) {
            System.out.println("Could not execute the request: " + throwables.getSQLState() + ", " + throwables.getMessage());
            return false;
        }
    }

    public static int insertAndGetId(Course course) {
        String insertSQL = "INSERT INTO tb_courses (title, duration, price) VALUES(?, ?, ?) RETURNING id";
        loadDriver();
        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PWD)) {
            try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                statement.setString(1, course.getTitle());
                statement.setDouble(2, course.getDuration());
                statement.setInt(3, course.getPrice());
                try (ResultSet keys = statement.executeQuery()) {
                    if (!keys.next()) return 0;
                    return keys.getInt("id");
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Could not execute the request: " + throwables.getSQLState() + ", " + throwables.getMessage());
            return 0;
        }
    }

    public static boolean update(Course course) {
        String updateSQL = "UPDATE tb_courses SET title=?, duration=?, price=? WHERE id=?";
        loadDriver();
        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PWD)) {
            try (PreparedStatement statement = connection.prepareStatement(updateSQL)) {
                statement.setString(1, course.getTitle());
                statement.setDouble(2, course.getDuration());
                statement.setInt(3, course.getPrice());
                statement.setInt(4, course.getId());
                return statement.executeUpdate() != 0;
            }
        } catch (SQLException throwables) {
            System.out.println("Could not execute the request: " + throwables.getSQLState() + ", " + throwables.getMessage());
            return false;
        }
    }

    public static boolean delete(Course course) {
        String deleteSQL = "DELETE FROM tb_courses WHERE id=" + course.getId();
        loadDriver();
        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PWD)) {
            try (Statement statement = connection.createStatement()) {
                return statement.executeUpdate(deleteSQL) != 0;
            }
        } catch (SQLException throwables) {
            System.out.println("Could not execute the request: " + throwables.getSQLState() + ", " + throwables.getMessage());
            return false;
        }
    }

    public static HashSet<Course> selectCourses() {
        String selectSQL = "SELECT id, title, price, duration FROM tb_courses";
        loadDriver();

        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PWD)) {
            try (Statement statement = connection.createStatement()) {
                HashSet<Course> courses = new HashSet<>();
                try (ResultSet resultSet = statement.executeQuery(selectSQL)) {
                    while (resultSet.next()) {
                        Course course = new Course(
                                resultSet.getString("title"),
                                resultSet.getDouble("duration"),
                                resultSet.getInt("price")

                        );
                        course.setId(resultSet.getInt("id"));
                        courses.add(course);
                    }
                }
                if (courses.isEmpty()) return null;
                return courses;
            }
        } catch (SQLException throwables) {
            System.out.println("Could not execute the request: " + throwables.getSQLState() + ", " + throwables.getMessage());
            return null;
        }
    }

    public static Course selectCoursesByID(int id) {
        String selectSQL = "SELECT id, title, duration FROM tb_courses WHERE id=" + id;
        loadDriver();
        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PWD)) {
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(selectSQL)) {
                    if (!resultSet.next()) return null;
                    Course course = new Course(
                            resultSet.getString("title"),
                            resultSet.getDouble("duration"),
                            -1);
                    course.setId(resultSet.getInt("id"));
                    return course;
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Could not execute the request: " + throwables.getSQLState() + ", " + throwables.getMessage());
            return null;
        }
    }

    public static HashSet<Course> selectCoursesByPriceAndDuration(int maxPrice, double maxDuration) {
        String selectSQL = "SELECT title, duration FROM tb_courses WHERE price <= ? AND duration <= ?";
        loadDriver();
        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PWD)) {
            try (PreparedStatement statement = connection.prepareStatement(selectSQL)) {
                statement.setInt(1, maxPrice);
                statement.setDouble(2, maxDuration);
                HashSet<Course> courses = new HashSet<>();
                try (ResultSet resultSet = statement.executeQuery()) {
                    while (resultSet.next()) {
                        Course course = new Course(
                                resultSet.getString("title"),
                                resultSet.getDouble("duration"),
                                -1
                        );
                        courses.add(course);
                    }
                }
                if (courses.isEmpty()) return null;
                return courses;
            }
        } catch (SQLException throwables) {
            System.out.println("Не удалось выполнить запрос: " + throwables.getSQLState() + ", " + throwables.getMessage());
            return null;
        }
    }
}
