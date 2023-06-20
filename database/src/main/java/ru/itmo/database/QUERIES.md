### Таблицы создаются при помощи CREATE TABLE:

    CREATE TABLE [IF NOT EXISTS] имя_таблицы (
        имя_столбца1 тип_данных(размер) доп. информация,
        имя_столбца2 тип_данных(размер) доп. информация,
        имя_столбца3 тип_данных(размер) доп. информация
        и т.д.
    );

    CREATE TABLE IF NOT EXISTS tb_categories (
        id SERIAL PRIMARY KEY,
        name VARCHAR(50) NOT NULL,
        description VARCHAR(200) NOT NULL
    );

    CREATE TABLE IF NOT EXISTS tb_habits (
        id SERIAL PRIMARY KEY,
        name VARCHAR(50),
        description VARCHAR(200) NOT NULL,
        good boolean DEFAULT FALSE
    );

Можно создать тип перечисление с заранее установленным набором значений:

    CREATE TYPE color AS ENUM ('black', 'white', 'ginger', 'gray');

Создание таблиц и связей между ними:

    CREATE TABLE [IF NOT EXISTS] имя_таблицы (
        имя_столбца1 тип_данных(размер) доп._информация,
        имя_столбца2 тип_данных(размер) доп._информация,
        имя_столбца3 тип_данных(размер) доп._информация,
        FOREIGN KEY (имя_столбца)
        REFERENCES главная_таблица (имя_столбца_главной_таблицы)
        [ON DELETE {CASCADE|RESTRICT}] [ON UPDATE {CASCADE|RESTRICT}]
    );

    CASCADE: автоматически удаляет или изменяет строки из зависимой таблицы при удалении или изменении связанных строк в
             главной таблице.
    RESTRICT: предотвращает какие-либо действия в зависимой таблице при удалении или изменении связанных строк в главной
              таблице. Фактически какие-либо действия отсутствуют.  


    Одной категории может соответсвовать несколько котов - связь ОДИН КО МНОГИМ (1:N).
    В таблице tb_cats нужно добавить столбец, который будет хранить информацию о принадлежности к категории.

    CREATE TABLE tb_cats (
        id SERIAL PRIMARY KEY,
        name VARCHAR(100) DEFAULT 'kitty',
        cat_color COLOR NOT NULL,
        birth DATE NOT NULL, 
        category_id INTEGER NOT NULL,
        constraint cats_categories
        FOREIGN KEY (category_id) REFERENCES tb_categories (id)
    );

    Одна привычка может быть у нескольких котов, у кота может быть несколько привычек - связь МНОГИЕ КО МНОГИМ (N:M).
    Необходимо создать дополнительную таблицу, которая будет хранить связи между котами и их привычками.

    CREATE TABLE IF NOT EXISTS tb_cats_habits (
         cat_id INTEGER NOT NULL,
         habit_id INTEGER NOT NULL,
         PRIMARY KEY (cat_id, habit_id),
         CONSTRAINT cats_fk FOREIGN KEY (cat_id)
            REFERENCES tb_cats (id) MATCH FULL
            ON UPDATE NO ACTION ON DELETE CASCADE,
         CONSTRAINT habits_fk FOREIGN KEY (habit_id)
            REFERENCES tb_habits (id) MATCH FULL
            ON UPDATE NO ACTION ON DELETE CASCADE
    );

Будут созданы таблицы:

    tb_categories
    Column      | Type                   | Modifiers
    ----------- +------------------------+-----------
    id          | integer                | not null
    name        | character varying(50)  | not null
    description | character varying(200) | not null

      INDEXES: id - PRIMARY KEY


    tb_habits
    Column      | Type                   | Modifiers
    ----------- +------------------------+--------------
    id          | integer                | not null     
    name        | character varying(50)  | not null     
    description | character varying(200) | not null     
    good        | boolean                | default false

      INDEXES: id - PRIMARY KEY


    tb_cats
    Column      | Type                   | Modifiers
    ----------- +------------------------+----------------
    id          | integer                | not null
    name        | character varying(100) | default 'kitty'
    cat_color   | user-defined (color)   | not null
    birth       | date                   | not null
    category_id | integer                | not null

      INDEXES: id - PRIMARY KEY
      CONSTRAINTS: category_id - FOREIGN KEY 

      
    tb_cats_habits
    Column      | Type    | Modifiers
    ----------- +---------+----------------
    cat_id      | integer | not null
    habit_id    | integer | not null

      INDEXES: (cat_id, habit_id) - PRIMARY KEY
      CONSTRAINTS: cat_id - FOREIGN KEY, habit_id - FOREIGN KEY

    NOT NULL означает, что значение не может быть null
    DEFAULT означает, что значение не передано, то будет использоваться значение по умолчанию

### Таблицы наполняются данными при помощи INSERT INTO:

    INSERT INTO tb_categories (id, name, description) VALUES (1, 'purebred', 'are relatively rare');
    INSERT INTO tb_categories (id, name, description) VALUES (2, 'mestizos', 'are less aggressive than many purebred cats');
   
    INSERT INTO tb_habits (name, description, good) VALUES ('outside', 'must go outside every day', TRUE);
    INSERT INTO tb_habits (name, description, good) VALUES ('children', 'likes children', TRUE);
    INSERT INTO tb_habits (name, description) VALUES ('other cats', 'doesn''t like other cats');

    INSERT INTO tb_cats (name, cat_color, birth, category_id) VALUES
                        ('roxxy', 'gray', '2023-01-01', 2),        
                        ('luna', 'white', '2022-09-12', 1),        
                        ('tom', 'black', '2019-04-25', 1),        
                        ('loki', 'ginger', '2023-05-08', 2),        
                        ('abbey', 'gray', '2018-11-27', 2);   

    INSERT INTO tb_cats_habits (cat_id, habit_id) VALUES
                        (1, 2),
                        (1, 3),
                        (2, 1),
                        (2, 3),
                        (3, 1),
                        (3, 2),
                        (4, 2),
                        (5, 1),
                        (5, 2);

### Удаление строк осуществляется с помощью DELETE:

    DELETE FROM имя_таблицы WHERE условие удаления;

    DELETE FROM tb_cats WHERE id=1;

    Если сделать DELETE без фильтра, то он удалит все значения из таблицы.

### Для удаления таблицы используется DROP TABLE: `DROP TABLE tb_habits;`

### Обновление строк:

      UPDATE имя_таблицы SET имя_столбца = 'новое значение' WHERE условие обновления;

      UPDATE tb_cats SET name='jack' WHERE id=3;

### Из таблицы можно выбрать данные при помощи SELECT:

Выбор значений по всем столбцам:

    SELECT * FROM tb_categories;
    SELECT id, name, description FROM tb_categories; 

    id  | name     | description 
    ----+----------+--------------------------------------------
    1   | purebred | are relatively rare    
    2   | mestizos | are less aggressive than many purebred cats  


    SELECT * FROM tb_habits;
    SELECT id, name, description, good FROM tb_habits; 

    id  | name       | description               | good
    ----+------------+---------------------------+-----------
    1   | outside    | must go outside every day | true
    2   | children   | likes children            | true
    3   | other cats | doesn't like other cats   | false

Можно выбирать не все столбцы, а только некоторые:

    SELECT name FROM tb_habits;

    name   
    ----------
    outside    
    children   
    other cats 

Можем сортировать при помощи ORDER BY:

    SELECT name FROM tb_habits ORDER BY name;

    name   
    ----------
    children
    other cats
    outside


    SELECT name FROM tb_habits ORDER BY name DESC;

    в обратном порядке
    name   
    ----------
    outside
    other cats
    children

Ограничение на количество результирующих строк:

    SELECT name, cat_color, birth FROM tb_cats LIMIT 2;

    name | color  | birth
    -----+--------+-----------
    luna | white  | 2022-09-12
    loki | ginger | 2023-05-08

Можно получить только нужные строки с помощью WHERE.

Операторы сравнения (= != > < >= <=) и логические операторы (OR AND NOT).

    SELECT name, description FROM tb_categories WHERE id=1;

    name     | description 
    ---------+--------------------
    purebred | are relatively rare  

Диапазон значений -- BETWEEN ... AND ...

    SELECT id, name FROM tb_cats
    WHERE EXTRACT(YEAR FROM birth) BETWEEN 2020 AND 2023;

    id | name 
    ---+------
    2  | luna 
    4  | loki 

Одно или несколько значений -- IN(value1, value2, value3) -- NOT IN(1, 3)

    SELECT name, birth, cat_color FROM tb_cats WHERE id IN(2, 4, 5);

    name  | birth       | cat_color
    ------+-------------+----------
    luna  | 2022-09-12  | white
    loki  | 2023-05-08  | ginger
    abbey | 2018-11-27  | gray

    
    SELECT cat_id FROM tb_cats_habits WHERE habit_id NOT IN(1, 3);

    cat_id 
    ------
    3
    4
    5

Агрегирующие функции:

    SELECT count(*) as habits_count from tb_habits;
    
    количество строк 
    habits_count
    -------
    4


    SELECT sum(СТОЛБЕЦ) as sum FROM ТАБЛИЦА;

    сумма по столбцу
    sum
    -----------
    сумма по столбцу

    SELECT avg(СТОЛБЕЦ) as avg FROM ТАБЛИЦА;

    среднее значение по столбцу
    avg
    -------
    среднее значение по столбцу

    Оператор AS позволяет назначать другие имена столбцам.

### Получение данных из нескольких таблиц (достигается путем нахождения одинакового столбца между таблицами)

Перечисление таблиц в блоке FROM

     SELECT tb_cats.name AS cat_name, tb_cats.birth AS cat_birth, tb_categories.name AS category
     FROM tb_cats, tb_categories
     WHERE tb_cats.category_id = tb_categories.id;

     cat_name | cat_birth   | category
     ---------+-------------+----------
     luna     | 2022-09-12  | purebred
     loki     | 2023-05-08  | mestizos
     abbey    | 2018-11-27  | mestizos
     jack     | 2018-11-27  | purebred


     SELECT tb_habits.name AS habit
     FROM tb_cats, tb_habits, tb_cats_habits
     WHERE tb_cats_habits.habit_id = tb_habits.id AND tb_cats_habits.cat_id = tb_cats.id AND tb_cats.name = 'abbey';

     habit
     --------
     outside
     children

Inner Join запросы - выбирает в обеих таблицах все записи, у которых есть общее значение и выводит их в результирующем
наборе, остальные записи исключаются

     SELECT tb_cats.name AS cat_name, tb_cats.birth AS cat_birth, tb_categories.name AS category
     FROM tb_cats
     JOIN tb_categories
     ON tb_cats.category_id = tb_categories.id;

     результат аналогичен предыдущему


     SELECT tb_habits.name AS habit
     FROM tb_cats
     JOIN tb_cats_habits
     ON tb_cats_habits.cat_id = tb_cats.id 
     JOIN tb_habits 
     ON tb_cats_habits.habit_id = tb_habits.id 
     WHERE tb_cats.name = 'abbey';

     результат аналогичен предыдущему


Outer Left Join - возвращает все записи из левой таблицы (FROM) и только совпадающие записи из правой таблицы (JOIN).

     кот без привычек
     INSERT INTO tb_cats (name, cat_color, birth, category_id) VALUES ('poly', 'black', '2022-06-22', 2);

     привычка, которой нет ни у одного кота
     INSERT INTO tb_habits (name, description, good) VALUES ('food', 'likes meat', FALSE);

     категория, которая не присвоена ни одному коту
     INSERT INTO tb_categories (id, name, description) VALUES (3, 'mini cats', 'are very cute');

     SELECT tb_categories.name AS category, tb_cats.name AS cat   
     FROM tb_categories 
     LEFT JOIN tb_cats
     ON tb_categories.id = tb_cats.category_id; 

     category  | cat
     ----------+-------
     purebred  | luna  
     mestizos  | loki  
     mestizos  | abbey 
     purebred  | jack  
     mestizos  | poly  
     mini cats | NULL


     SELECT tb_categories.name AS category  
     FROM tb_categories
     LEFT JOIN tb_cats
     ON tb_categories.id = tb_cats.category_id
     WHERE tb_cats.id IS NULL;

     category
     ---------
     mini cats   


     SELECT tb_cats.name AS cat
     FROM tb_cats
     LEFT JOIN tb_cats_habits
     ON tb_cats_habits.cat_id = tb_cats.id
     LEFT JOIN tb_habits
     ON tb_cats_habits.habit_id = tb_habits.id
     WHERE tb_habits.id IS NULL;

     cat
     ----
     poly


Outer Right Join - возвращает все записи из правой таблицы (FROM) и только совпадающие записи из левой таблицы (JOIN).     

Full Join - возвращает все записи из каждой таблицы, включая все нулевые значения.

     SELECT tb_cats.name AS cat, tb_habits.name AS habit
     FROM tb_cats
     FULL JOIN tb_cats_habits
     ON tb_cats_habits.cat_id = tb_cats.id
     FULL JOIN tb_habits
     ON tb_cats_habits.habit_id = tb_habits.id;

     cat   | habit
     ------+-----------
     luna  | outside   
     luna  | other cats  
     jack  | outside
     jack  | children  
     loki  | children  
     abbey | outside
     abbey | children
     poly  | NULL
     NULL  | food









