package com.herokuapp.avhaymart.mysqlmanager.controller;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.herokuapp.avhaymart.mysqlmanager.models.Connection;
import com.herokuapp.avhaymart.mysqlmanager.models.DbItem;

import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ViewController {
    @GetMapping("/view")
    public ResponseEntity<ArrayList<ArrayList<DbItem>>> view(@CookieValue("connection") String connectionCookie)
            throws SQLException, JsonParseException, JsonMappingException, IOException {
        JSONObject parsedCookie = new JSONObject(URLDecoder.decode(connectionCookie, "UTF-8"));
        Connection connection = new Connection(parsedCookie.getString("user"), parsedCookie.getString("password"),
                parsedCookie.getString("database"), parsedCookie.getString("host"));

        // Make a connection to the database from the cookie
        java.sql.Connection connect = DriverManager
                .getConnection("jdbc:mysql://" + connection.getHost() + "/" + connection.getDatabase() + "?" + "user="
                        + connection.getUser() + "&password=" + connection.getPassword());

        Statement statement = connect.createStatement();

        // The response is is going to have the tables in the database, as well as the
        // table name
        ArrayList<ArrayList<DbItem>> resArray = new ArrayList<ArrayList<DbItem>>();
        ArrayList<DbItem> tableArrList = new ArrayList<DbItem>();
        ArrayList<DbItem> dbArrList = new ArrayList<DbItem>();

        // first query will be the list of tables in the database
        ResultSet one = statement.executeQuery("SHOW TABLES;");
        while (one.next()) {
            tableArrList.add(new DbItem(one.getString(1)));
        }

        // second query will be to get the database name
        ResultSet two = statement.executeQuery("SELECT DATABASE() FROM DUAL;");

        while (two.next()) {
            dbArrList.add(new DbItem(two.getString(1)));
        }

        resArray.add(tableArrList);
        resArray.add(dbArrList);

        return ResponseEntity.status(HttpStatus.OK).body(resArray);
    }

    @PostMapping("/view/table")
    public ResponseEntity<ArrayList<HashMap<String, Object>>> viewTable(@RequestBody Map<String, String> req,
            @CookieValue("connection") String connectionCookie)
            throws SQLException, JsonParseException, JsonMappingException, IOException {
        JSONObject parsedCookie = new JSONObject(URLDecoder.decode(connectionCookie, "UTF-8"));
        Connection connection = new Connection(parsedCookie.getString("user"), parsedCookie.getString("password"),
                parsedCookie.getString("database"), parsedCookie.getString("host"));

        // Make a connection to the database from the cookie
        java.sql.Connection connect = DriverManager
                .getConnection("jdbc:mysql://" + connection.getHost() + "/" + connection.getDatabase() + "?" + "user="
                        + connection.getUser() + "&password=" + connection.getPassword());

        Statement statement = connect.createStatement();

        ResultSet res = statement.executeQuery("SELECT * FROM " + req.get("data"));

        ArrayList<HashMap<String, Object>> resArray = new ArrayList<HashMap<String, Object>>();

        ResultSetMetaData meta = null;
        meta = res.getMetaData();

        int colCount = meta.getColumnCount();
        ArrayList<String> cols = new ArrayList<String>();
        for (int index = 1; index <= colCount; index++)
            cols.add(meta.getColumnName(index));

        while (res.next()) {
            HashMap<String, Object> row = new HashMap<String, Object>();
            for (String colName : cols) {
                Object val = res.getObject(colName);
                row.put(colName, val);
            }
            resArray.add(row);
        }

        return ResponseEntity.status(HttpStatus.OK).body(resArray);
    }

}