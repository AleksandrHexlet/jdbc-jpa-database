package ru.itmo.database;

import ru.itmo.database.jdbc.SeveralSqlQueriesExamples;
import ru.itmo.database.entity.Course;

import java.util.HashSet;
import java.util.Properties;

import static ru.itmo.database.connsectionspool.DBRequestWithPool.insertWithPool;
import static ru.itmo.database.jdbc.JoinQueries.*;
import static ru.itmo.database.jdbc.StatementAndPreparedExamples.*;
import static ru.itmo.database.jdbc.SeveralSqlQueriesExamples.*;

public class Main {

    public static void main(String[] args) {

        System.out.println("JDBC");
        System.out.println("Multiple queries. Single String");
        sqlScript();


        System.out.println("Multiple queries. Single Connection");
        HashSet<Course> courses = new HashSet<>();
        courses.add(new Course("nodeJS", 4, 55000));
        courses.add(new Course("C++", 3, 59000));
        courses.add(new Course("Python", 2.5, 35000));
        bufferQueries(courses);


        System.out.println("Insert data");
        Course insert01 = new Course("GO", 3, 60000);
        Course insert02 = new Course("Kotlin", 2.3, 37000);

        if (insert(insert01)) System.out.println("Course " + insert01.getTitle() + " has been inserted");
        int id = insertAndGetId(insert02);
        if (id > 0) {
            System.out.println("Course " + insert02.getTitle() + " has been inserted. ID = " + id);
            insert02.setId(id);
        }

        System.out.println("Update data");
        insert02.setPrice(insert02.getPrice() + 10_000);
        if (update(insert02)) System.out.println("Course " + insert02.getTitle() + " has been updated");

        System.out.println("Select data");
        HashSet<Course> coursesFromDB01 = selectCourses();
        System.out.println("courses: " + coursesFromDB01);

        HashSet<Course> coursesFromDB02 = selectCoursesByPriceAndDuration(55000, 3);
        System.out.println("courses - maxPrice = 55000, maxDuration = 3: " + coursesFromDB02);

        Course courseFromDB = selectCoursesByID(1);
        System.out.println("course - id = 1: " + courseFromDB);

        System.out.println("Delete data");
        if (courseFromDB != null && delete(courseFromDB))
            System.out.println("Course " + courseFromDB.getTitle() + " has been deleted");

        System.out.println("C3P0 POOL");
        Course spring = new Course("Spring. Spring Boot", 2.5, 30000);
        int springId = insertWithPool(spring);
        if (springId > 0) {
            System.out.println("Course " + spring.getTitle() + " has been inserted. ID = " + springId);
            spring.setId(springId);
        }

        System.out.println("JOIN QUERIES");

        init();
        System.out.println(studentByName("'Иван'"));
        System.out.println(studentsByCourseName("'Курс №1'"));
        System.out.println(emptyCourses());
    }
}