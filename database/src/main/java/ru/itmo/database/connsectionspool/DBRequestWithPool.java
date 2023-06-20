package ru.itmo.database.connsectionspool;

import ru.itmo.database.entity.Course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBRequestWithPool {
    public static int insertWithPool(Course course){
        String insertSQL = "INSERT INTO tb_courses (title, duration, price) VALUES(?, ?, ?) RETURNING id";
        try (PreparedStatement statement = C3P0DataSource.getConnection().prepareStatement(insertSQL)) {
            statement.setString(1, course.getTitle());
            statement.setDouble(2, course.getDuration());
            statement.setInt(3, course.getPrice());
            try (ResultSet keys = statement.executeQuery()) {
                if (!keys.next()) return 0;
                return keys.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("Could not execute the request: " + e.getSQLState() + ", " + e.getMessage());
            return 0;
        }
    }
}
