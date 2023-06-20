package ru.itmo.database.jdbc;

import ru.itmo.database.entity.Course;

import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;

import static ru.itmo.database.jdbc.Settings.*;

public class SeveralSqlQueriesExamples {

    public static void sqlScript() {
        // если строка состоит из нескольких запросов, необходимо разделять их точкой с запятой
        String createSQL = "CREATE TABLE IF NOT EXISTS tb_courses (" +
                "id SERIAL PRIMARY KEY," +
                "title VARCHAR(255) NOT NULL," +
                "duration NUMERIC(4,2) NOT NULL," +
                "price INTEGER DEFAULT 0);" +
                "CREATE TABLE IF NOT EXISTS tb_teachers (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "email VARCHAR(255) NOT NULL);" +
                "CREATE TABLE IF NOT EXISTS tb_students (" +
                "id SERIAL PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "course_id INTEGER NOT NULL," +
                "CONSTRAINT student_course FOREIGN KEY (course_id) " +
                "REFERENCES tb_courses (id) MATCH FULL " +
                "ON UPDATE NO ACTION ON DELETE NO ACTION" +
                ")";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver is not found");
        }
        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PWD)) {
            try (Statement statement = connection.createStatement()) {
                System.out.println(statement.executeUpdate(createSQL));
            }
        } catch (SQLException throwables) {
            System.out.println("Не у далось выполнить запрос " + throwables.getSQLState() + ", " + throwables.getMessage());
        }


    }

    public static void bufferQueries(HashSet<Course> courses) {
        // batch-режим вставки - заключается в том, запросы накапливаются в буфер,
        // а потом выполняются сразу в рамках одного соединения
        String insertSql = "INSERT INTO tb_courses (title, duration, price) VALUES(?, ?, ?)";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Driver is not found");
        }

        try (Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PWD)) {
            try (PreparedStatement statement = connection.prepareStatement(insertSql)) {
                for (Course course : courses) {
                    statement.setString(1, course.getTitle());
                    statement.setDouble(2, course.getDuration());
                    statement.setInt(3, course.getPrice());
                    statement.addBatch();
                }
                try {
                    int[] res = statement.executeBatch(); // все запросы будут выполнены в рамках соединения
                    System.out.println(Arrays.toString(res));
                } catch (SQLException throwables) {
                    System.out.println("Could not execute the request: " + throwables.getSQLState() + ", " + throwables.getMessage());
                }
            }
        } catch (SQLException throwables) {
            System.out.println("Failed to connect with database: " + throwables.getSQLState() + ", " + throwables.getMessage());
        }
    }

}
