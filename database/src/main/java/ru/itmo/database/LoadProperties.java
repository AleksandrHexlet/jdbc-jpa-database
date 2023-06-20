package ru.itmo.database;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Properties;

public class LoadProperties {
    public static void main(String[] args) {
        Properties properties = new Properties();


        String rootPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String settingsPath = rootPath + "settings.properties";

        try {
            properties.load(new FileReader(settingsPath));
        } catch (IOException e) {
            System.out.println("Loading Error: " + e.getMessage());
        }

        System.out.println(properties.getOrDefault("project.name", "Project #1"));

        properties.setProperty("owner", "jjd");

        properties.forEach((key, value) -> System.out.println(key + ": " + value));

        properties.replace("project.version", "1.2");

        try {
            properties.store(new FileWriter(settingsPath), "Settings");
        } catch (IOException e) {
            System.out.println("Writing Error: " + e.getMessage());
        }
    }
}
