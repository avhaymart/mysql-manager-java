package com.herokuapp.avhaymart.mysqlmanager.services;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;

import com.herokuapp.avhaymart.mysqlmanager.models.Connection;

public class ConnectionManagerService {
    public static HashMap<String, Connection> connectionMap = new HashMap<String, Connection>();

    public static void cacheConnection(String name, Connection connection) {
        connectionMap.put(name, connection);
    }

    public static java.sql.Connection getConnection(String name) throws SQLException {
        Connection connection = connectionMap.get(name);
        return DriverManager.getConnection("jdbc:mysql://" + connection.getHost() + "/" + connection.getDatabase() + "?"
                + "user=" + connection.getUser() + "&password=" + connection.getPassword());
    }
}