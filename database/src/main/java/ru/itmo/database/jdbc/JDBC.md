## JDBC (Java DataBase Connectivity)

- платформенно независимый промышленный стандарт взаимодействия Java-приложений с различными СУБД, реализованный в виде
  пакета java.sql, входящего в состав Java SE.

Для соединения с сервером используется JDBC Driver Manager. Для отправки запросов и получения результатов используется
JDBC API.

Для установления соединения нужен JDBC драйвер для конкретной БД.

1. Загрузка драйвера: `Class.forName("org.postgresql.Driver");`
2.

Подключение: `Connection con = DriverManager.getConnection("jdbc:postgresql://ip:port/имя_бд", "имя_пользователя", "пароль");`

Класс Connection предоставляет множество методов для работы с БД: получения метаданных (информация о таблицах,
хранимых процедурах и т. п.), управления транзакциями, информации о текущей схеме, подключении и так далее.

Некоторые из методов Connection:

1) `Statement createStatement()` - создает и возвращает экземпляр Statement для выполнения запросов к БД
2) `PreparedStatement prepareStatement(String sql)` - создает и возвращает экземпляр PreparedStatement для выполнения
   подготовленных запросов к БД
3) `void setAutoCommit(boolean autoCommit)` - для включения и выключения автокоммита транзакций
4) `boolean getAutoCommit()` - возвращает статус автокоммита транзакций
5) `void commit()` - подтверждение (коммит) транзакции
6) `void rollback()` - откатывает транзакцию к началу
7) `DatabaseMetaData getMetaData()` - возвращает объект, хранящий информацию о Базе Данных

3. Создание Statement (для выполнения запроса к БД): `Statement statement = con.createStatement();`

Некоторые из методов Statement:

1) `ResultSet executeQuery(String sql)` - используется в запросах, результатом которых является один единственный набор
   значений, таких как запросов типа SELECT. Результат работы метода - ResultSet.
2) `int executeUpdate(String sql)` - следует использовать для выполнения INSERT, UPDATE или DELETE (Data Manipulation
   Language) запросов и для CREATE TABLE, DROP TABLE (DDL - Data Definition Language) запросов. Результатом выполнения
   метода executeUpdate является целочисленное значение, определяющее, сколько строк было модифицировано. Для выражений
   DML, которые не оперируют со строками, возвращаемое методом executeUpdate значение всегда равно нулю.
3) `boolean execute(String sql)` - для выполнения запроса с неизвестным или множественным результатом. Вернет, если в
   результате выполнения запроса можно получить ResultSet. Метод `ResultSet getResultSet()` позволит получить ResultSet,
   если он доступен.
4) `void setMaxRows(int max)` - установить максимальное количество строк, которые может содержать ResultSet. 0 - без
   лимита.
5) `int getMaxRows()` - получить максимальное количество строк, которые может содержать ResultSet. 0 - без лимита.
6) `void addBatch( String sql )` - добавляет запрос в буфер.
7) `void clearBatch()` - очищает буфер.
8) `int[] executeBatch()` - выполняет запросы, накопленные в буфер, возвращает массив с результатами выполнения каждого.
9) `ResultSet getGeneratedKeys()` - ссылка на ResultSet, который содержит автоматически сгенерированные значения.
10) `void setFetchDirection(int direction)` - устанавливает направление извлечения данных - от первой к последней (
    ResultSet.FETCH_FORWARD), от последней к первой (ResultSet.FETCH_REVERSE).
11) `int getFetchDirection()` - возвращает направление извлечения данных
12) `void setFetchSize(int rows)` - устанавливает количество строк, извлекаемых в ResultSet за один раз (если необходимо
    получать большие объемы данных).

4. Создание PreparedStatement (для выполнения запроса к
   БД): `PreparedStatement statement = con.prepareStatement(sqlString);`

Помимо Statement существует так же PreparedStatement. Его особенность заключается в первую очередь в том, что он
обрабатывается, оптимизируется и кэшируется СУБД один раз при первом исполнении, после чего каждый последующий запрос
выполняется гораздо быстрее. Помимо этого он защищает от SQL инъекций, т.к. аргументы экранируются.

Некоторые из методов PreparedStatement:

1) `void setNull(int parameterIndex, int sqlType)` - устанавливает значение параметра parameterIndex в значении SQL NULL
2) `void setBoolean(int parameterIndex, boolean x)` - устанавливает значение параметра parameterIndex в значении x.
   Аналогичные методы существуют для всех примитивов и строк.
3) `void setBytes(int parameterIndex, byte x[])` - для установки SQL VARBINARY или LONGVARBINARY значений
4) `void setDate(int parameterIndex, java.sql.Date x)` - для установки SQL даты
5) `void setTime(int parameterIndex, java.sql.Time x)` - для установки SQL времени
6) `void setTime(int parameterIndex, java.sql.Timestamp x)` - для установки SQL времени и даты

Также содержит методы, позволяющие установить другие, доступные в БД типы (blob, xml, array и тд)

5. Получение объекта ResultSet: `ResultSet resultSet = statement.executeQuery(sqlString);`

   В объекте ResultSet итератор устанавливается на позиции перед первой строкой. И чтобы переместиться к первой строке (
   и ко всем последующим) необходимо вызвать метод next(). Пока в наборе ResultSet есть доступные строки, метод next
   будет возвращать true.

         while(resultSet.next()){ // получение данных их ResultSet
            методы resultSet для получения данных:
            getBoolean() возвращает значение boolean
            getDate() возвращает значение Date
            getDouble() возвращает значение double
            getInt() возвращает значение int
            getFloat() возвращает значение float
            getLong() возвращает значение long
            getString() возвращает значение String
         }

### Connection, Statement, PreparedStatement, ResultSet должны быть закрыты после окончания работы с ними

### Транзакции:

      // Выключяем автокоммит транзакций
      con.setAutoCommit(false);
      // Создаем statement
      try (Statement stmnt = con.createStatement()) {
         // Выполняем запрос к БД
        
         // Если все хорошо завершаем тразакцию
         con.commit();
      } catch (SQLException e) {
         // В случае ошибки откатываем изменения
         con.rollback();
      }

