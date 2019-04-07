package com.herokuapp.avhaymart.mysqlmanager.services;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.herokuapp.avhaymart.mysqlmanager.models.DbItem;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class GetRequestService {

    // Get Column Names
    public static ResponseEntity<ArrayList<String[]>> columnNames(String name, String cookie)
            throws SQLException {

        java.sql.Connection connect = ConnectionManagerService.getConnection(cookie);

        Statement statement = connect.createStatement();

        ResultSet res = statement.executeQuery("SHOW COLUMNS FROM " + name);

        ArrayList<String[]> columns = new ArrayList<String[]>();
        while (res.next()) {
            String extra = res.getString("Extra");
            String field = res.getString("Field");
            String type = res.getString("Type");
            if (!extra.equals("auto_increment")) {
                columns.add(new String[] { field, type, extra });
            }
        }

        return ResponseEntity.status(HttpStatus.OK).body(columns);
    }

    // View Tables
    public static ResponseEntity<ArrayList<ArrayList<DbItem>>> viewTables(String cookie)
            throws SQLException {
        java.sql.Connection connect = ConnectionManagerService.getConnection(cookie);

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

    // View Rows
    public static ResponseEntity<ArrayList<HashMap<String, Object>>> viewRows(Map<String, String> req,
            String cookie) throws SQLException {
        java.sql.Connection connect = ConnectionManagerService.getConnection(cookie);

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
