package com.herokuapp.avhaymart.mysqlmanager.services;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import com.herokuapp.avhaymart.mysqlmanager.models.Connection;
import com.herokuapp.avhaymart.mysqlmanager.models.PostRow;
import com.herokuapp.avhaymart.mysqlmanager.models.PostTable;
import com.herokuapp.avhaymart.mysqlmanager.models.TableRow;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class PostRequestService {

    // Connect to the Database
    public static ResponseEntity<Connection> ConnectDB(Connection connection, HttpServletResponse response)
            throws SQLException {
        DriverManager.getConnection("jdbc:mysql://" + connection.getHost() + "/" + connection.getDatabase() + "?"
                + "user=" + connection.getUser() + "&password=" + connection.getPassword());

        String key = RandomStringUtils.randomAlphanumeric(24);
        response.addCookie(new Cookie("connection", key));
        ConnectionManagerService.cacheConnection(key, connection);

        return ResponseEntity.status(HttpStatus.OK).body(connection);
    }

    // Create Table
    public static ResponseEntity<String> createTable(PostTable req, String cookie) throws SQLException {
        java.sql.Connection connect = ConnectionManagerService.getConnection(cookie);

        Statement statement = connect.createStatement();
        StringBuilder query = new StringBuilder("CREATE TABLE " + req.getTableName() + "(id int NOT NULL AUTO_INCREMENT,");
        for (TableRow item : req.getRows()) {
            query.append(item.getItemName() + " " + item.getDataTypePretty() + ", ");
        }
        query.append("PRIMARY KEY (id));");
        int res = statement.executeUpdate(query.toString());

        return ResponseEntity.status(HttpStatus.OK).body("Finished with status " + res);
    }

    // Create Row
    public static ResponseEntity<String> createRow(PostRow req, String cookie) throws SQLException {
        java.sql.Connection connect = ConnectionManagerService.getConnection(cookie);

        Statement statement = connect.createStatement();

        Boolean id = false;
        String query = "INSERT INTO " + req.getData().getName() + "(";

        for (ArrayList<String> item : req.getData().getFields()) {
            if (item.get(0).equals("id")) {
                id = true;
            } else {
                query = query + (item.get(0) + ",");
            }
        }
        query = query.substring(0, query.length() - 1) + ") VALUES (";
        for (String item : req.getData().getInput()) {
            if (id) {
                id = false;
            } else {
                query = query + "'" + item + "'" + ",";
            }
        }
        query = query.substring(0, query.length() - 1) + ")";
        int res = statement.executeUpdate(query);

        return ResponseEntity.status(HttpStatus.OK).body("Finished with status " + res);
    }
}
