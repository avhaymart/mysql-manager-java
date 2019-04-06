package com.herokuapp.avhaymart.mysqlmanager.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import com.herokuapp.avhaymart.mysqlmanager.models.Connection;
import com.herokuapp.avhaymart.mysqlmanager.models.DeleteRow;
import com.herokuapp.avhaymart.mysqlmanager.models.PostRow;
import com.herokuapp.avhaymart.mysqlmanager.models.PostTable;
import com.herokuapp.avhaymart.mysqlmanager.models.TableRow;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class APIController {

    @GetMapping("/api/columns")
    public ResponseEntity<ArrayList<String[]>> getColumns(@RequestParam String name,
            @CookieValue("connection") String connectionCookie)
            throws SQLException, UnsupportedEncodingException, JSONException {
        JSONObject parsedCookie = new JSONObject(URLDecoder.decode(connectionCookie, "UTF-8"));
        Connection connection = new Connection(parsedCookie.getString("user"), parsedCookie.getString("password"),
                parsedCookie.getString("database"), parsedCookie.getString("host"));

        // Make a connection to the database from the cookie
        java.sql.Connection connect = DriverManager
                .getConnection("jdbc:mysql://" + connection.getHost() + "/" + connection.getDatabase() + "?" + "user="
                        + connection.getUser() + "&password=" + connection.getPassword());

        Statement statement = connect.createStatement();

        ResultSet res = statement.executeQuery("SHOW COLUMNS FROM " + name);

        ArrayList<String[]> columns = new ArrayList<String[]>();
        while (res.next()) {
            String extra = res.getString("Extra");
            String field = res.getString("Field");
            String type = res.getString("Type");
            if (!extra.equals("auto_increment")){
                columns.add(new String[]{field,type,extra});
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(columns);
    }

    @DeleteMapping("/api/row")
    public ResponseEntity<String> deleteRow(@RequestBody DeleteRow req,
            @CookieValue("connection") String connectionCookie)
            throws SQLException, UnsupportedEncodingException, JSONException {
        JSONObject parsedCookie = new JSONObject(URLDecoder.decode(connectionCookie, "UTF-8"));
        Connection connection = new Connection(parsedCookie.getString("user"), parsedCookie.getString("password"),
                parsedCookie.getString("database"), parsedCookie.getString("host"));

        // Make a connection to the database from the cookie
        java.sql.Connection connect = DriverManager
                .getConnection("jdbc:mysql://" + connection.getHost() + "/" + connection.getDatabase() + "?" + "user="
                        + connection.getUser() + "&password=" + connection.getPassword());

        Statement statement = connect.createStatement();

        int res = statement.executeUpdate(
                "DELETE FROM " + req.getTableName() + " WHERE " + req.getRowIndex(1) + "='" + req.getRowIndex(0) + "'");

        return ResponseEntity.status(HttpStatus.OK).body("Finished with status " + res);
    }

    @DeleteMapping("/api/table")
    public ResponseEntity<String> deleteTable(@RequestBody Map<String, String> req,
            @CookieValue("connection") String connectionCookie)
            throws SQLException, UnsupportedEncodingException, JSONException {
        JSONObject parsedCookie = new JSONObject(URLDecoder.decode(connectionCookie, "UTF-8"));
        Connection connection = new Connection(parsedCookie.getString("user"), parsedCookie.getString("password"),
                parsedCookie.getString("database"), parsedCookie.getString("host"));

        // Make a connection to the database from the cookie
        java.sql.Connection connect = DriverManager
                .getConnection("jdbc:mysql://" + connection.getHost() + "/" + connection.getDatabase() + "?" + "user="
                        + connection.getUser() + "&password=" + connection.getPassword());

        Statement statement = connect.createStatement();

        int res = statement.executeUpdate("DROP TABLE " + req.get("tableName"));

        return ResponseEntity.status(HttpStatus.OK).body("Finished with status " + res);
    }

    @PostMapping("/api/table")
    public ResponseEntity<String> postTable(@RequestBody PostTable req,
            @CookieValue("connection") String connectionCookie)
            throws SQLException, UnsupportedEncodingException, JSONException {
        JSONObject parsedCookie = new JSONObject(URLDecoder.decode(connectionCookie, "UTF-8"));
        Connection connection = new Connection(parsedCookie.getString("user"), parsedCookie.getString("password"),
                parsedCookie.getString("database"), parsedCookie.getString("host"));

        // Make a connection to the database from the cookie
        java.sql.Connection connect = DriverManager
                .getConnection("jdbc:mysql://" + connection.getHost() + "/" + connection.getDatabase() + "?" + "user="
                        + connection.getUser() + "&password=" + connection.getPassword());

        Statement statement = connect.createStatement();


        String buildString = "CREATE TABLE " + req.getTableName() + "(id int NOT NULL AUTO_INCREMENT,";
    // req.body.rows.forEach(element => {
    //     buildString = buildString + element.itemName + " " + element.dataTypePretty + ", "
    // });

    for (TableRow item : req.getRows()) {
        buildString = buildString + item.getItemName() + " " + item.getDataTypePretty() + ", ";
    }

        buildString = buildString + "PRIMARY KEY (id));";
    
        int res = statement.executeUpdate(buildString);

        return ResponseEntity.status(HttpStatus.OK).body("Finished with status " + res);
    }

     @PostMapping("/api/row")
    public ResponseEntity<String> postRow(@RequestBody PostRow req,
            @CookieValue("connection") String connectionCookie)
            throws SQLException, UnsupportedEncodingException, JSONException {
        JSONObject parsedCookie = new JSONObject(URLDecoder.decode(connectionCookie, "UTF-8"));
        Connection connection = new Connection(parsedCookie.getString("user"), parsedCookie.getString("password"),
                parsedCookie.getString("database"), parsedCookie.getString("host"));

        // Make a connection to the database from the cookie
        java.sql.Connection connect = DriverManager
                .getConnection("jdbc:mysql://" + connection.getHost() + "/" + connection.getDatabase() + "?" + "user="
                        + connection.getUser() + "&password=" + connection.getPassword());

        Statement statement = connect.createStatement();

        Boolean id = false;
        String buildString = "INSERT INTO " + req.getData().getName() + "(";

        for (ArrayList<String> item : req.getData().getFields()) {
            if (item.get(0).equals("id")) {
                id=true;
            } else {
                buildString = buildString + item.get(0) + ",";
            }
        }
        buildString = buildString.substring(0, buildString.length() - 1) + ") VALUES (";
        for (String item : req.getData().getInput()) {
            if (id) {
                id=false;
            } else {
                buildString = buildString +"'"+ item+ "'"+ ",";
            }
        }
        buildString = buildString.substring(0, buildString.length() - 1) + ")";
        System.out.println(buildString);
        int res = statement.executeUpdate(buildString);


        return ResponseEntity.status(HttpStatus.OK).body("Finished with status " + res);
    }
}
