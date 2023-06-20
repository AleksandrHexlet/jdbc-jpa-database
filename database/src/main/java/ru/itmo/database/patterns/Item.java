package ru.itmo.database.patterns;

// сущность
// в коде - класс / в БД - таблица
// в коде - поля класса / в БД - столбцы таблицы
// в коде - объект / в БД - строка таблицы
@ClassTable(name = "tb_items")
public class Item {
    @Definition(value = "VARCHAR(60) PRIMARY KEY")
    private String unique;

    @Definition(value = "VARCHAR(200) NOT NULL")
    private String title;

    @Definition(value = "INTEGER DEFAULT -1")
    private int numberOfItems;

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public void setNumberOfItems(int numberOfItems) {
        this.numberOfItems = numberOfItems;
    }
}
