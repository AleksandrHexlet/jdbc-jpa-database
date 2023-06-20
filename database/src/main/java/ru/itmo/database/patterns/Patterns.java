package ru.itmo.database.patterns;

import ru.itmo.database.patterns.dao.DBItemStore;
import ru.itmo.database.patterns.dao.MemoryItemStore;
import ru.itmo.database.patterns.repository.ItemRepository;

import java.lang.reflect.Field;

public class Patterns {
    public static void main(String[] args) {
        createTable(Item.class);

        Item item = new Item();

        MemoryItemStore memory = new MemoryItemStore();
        DBItemStore db = new DBItemStore();

        ItemRepository repository01 = new ItemRepository(memory);
        repository01.add(item); // и тд

        ItemRepository repository02 = new ItemRepository(db);
        repository02.add(item); // и тд

    }

    public static void createTable(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(ClassTable.class)) {
            throw new IllegalArgumentException("ClassTable annotation was not found");
        }

        ClassTable classTable = clazz.getDeclaredAnnotation(ClassTable.class);
        String tableName = classTable.name();

        Field[] fields = clazz.getDeclaredFields();

        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        builder.append(tableName).append("(");
        for (Field field : fields) {
            if (!field.isAnnotationPresent(Definition.class)) continue;
            Definition definition = field.getDeclaredAnnotation(Definition.class);
            builder.append(field.getName()).append(" ").append(definition.value()).append(",");
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(");");

        System.out.println(builder);
    }

}
