package com.herokuapp.avhaymart.mysqlmanager.services;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import com.herokuapp.avhaymart.mysqlmanager.models.DeleteRow;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class DeleteRequestService {

        // Delete Row
        public static ResponseEntity<String> deleteRow(DeleteRow req, String cookie) throws SQLException {
                java.sql.Connection connect = ConnectionManagerService.getConnection(cookie);

                Statement statement = connect.createStatement();

                int res = statement.executeUpdate("DELETE FROM " + req.getTableName() + " WHERE " + req.getRowIndex(1)
                                + "='" + req.getRowIndex(0) + "'");

                return ResponseEntity.status(HttpStatus.OK).body("Finished with status " + res);
        }

        // Delete Table
        public static ResponseEntity<String> deleteTable(Map<String, String> req, String cookie) throws SQLException {
                java.sql.Connection connect = ConnectionManagerService.getConnection(cookie);

                Statement statement = connect.createStatement();

                int res = statement.executeUpdate("DROP TABLE " + req.get("tableName"));

                return ResponseEntity.status(HttpStatus.OK).body("Finished with status " + res);
        }
}