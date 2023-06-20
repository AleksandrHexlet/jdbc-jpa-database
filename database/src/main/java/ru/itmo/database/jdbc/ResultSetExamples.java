package ru.itmo.database.jdbc;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;

import static ru.itmo.database.jdbc.Settings.*;

public class ResultSetExamples {
    // сохраняет работоспособность и тогда, когда соединение с базой уже закрыто,
    // сразу кэшируя в память все данные, которые вернул запрос
    private static CachedRowSet cachedRowSet() throws SQLException {
        CachedRowSet cachedRowSet = RowSetProvider.newFactory().createCachedRowSet();
        cachedRowSet.setUrl(CONNECTION_STR);
        cachedRowSet.setUsername(LOGIN);
        cachedRowSet.setPassword(PWD);
        cachedRowSet.setCommand("SELECT * FROM course");
        cachedRowSet.execute();
        return cachedRowSet;
    }
}
