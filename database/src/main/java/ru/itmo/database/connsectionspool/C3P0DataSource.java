package ru.itmo.database.connsectionspool;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class C3P0DataSource {
    private C3P0DataSource(){}

    // пул соединений
    private static ComboPooledDataSource connectionsPool = new ComboPooledDataSource();

    /*
    static { // настройки, переопределяющие или дополняющие настройки в properties файле
        try {
            connectionsPool.setDriverClass("org.postgresql.Driver");
            connectionsPool.setJdbcUrl("jdbc:postgresql://localhost:5432/lessons");
            connectionsPool.setUser("jjd");
            connectionsPool.setPassword("itmo");
            connectionsPool.setInitialPoolSize(5); // изначальное количество соединений. default = 3
            connectionsPool.setMinPoolSize(4); // минимальное количество соединений в пуле. default = 3
            connectionsPool.setMaxPoolSize(10); // максимальное количество соединений в пуле. default = 15
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
    }
    */
    public static Connection getConnection() throws SQLException {
        return connectionsPool.getConnection(); // получение объекта соединения из пула
        // аналогично: Connection connection = DriverManager.getConnection(CONNECTION_STR, LOGIN, PWD)
    }

}