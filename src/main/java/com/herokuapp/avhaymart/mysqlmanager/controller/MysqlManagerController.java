package com.herokuapp.avhaymart.mysqlmanager.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.herokuapp.avhaymart.mysqlmanager.models.Connection;
import com.herokuapp.avhaymart.mysqlmanager.models.DbItem;
import com.herokuapp.avhaymart.mysqlmanager.models.DeleteRow;
import com.herokuapp.avhaymart.mysqlmanager.models.PostRow;
import com.herokuapp.avhaymart.mysqlmanager.models.PostTable;
import com.herokuapp.avhaymart.mysqlmanager.services.DeleteRequestService;
import com.herokuapp.avhaymart.mysqlmanager.services.GetRequestService;
import com.herokuapp.avhaymart.mysqlmanager.services.PostRequestService;

import org.json.JSONException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MysqlManagerController {

    // GET /api/columnNames
    @GetMapping("/api/columnNames")
    public ResponseEntity<ArrayList<String[]>> getColumns(@RequestParam String name,
            @CookieValue("connection") String cookie)
            throws SQLException, UnsupportedEncodingException, JSONException {
        return GetRequestService.columnNames(name, cookie);
    }

    // GET /api/viewTables
    @GetMapping("/api/viewTables")
    public ResponseEntity<ArrayList<ArrayList<DbItem>>> view(@CookieValue("connection") String cookie)
            throws SQLException, JsonParseException, JsonMappingException, IOException {
        return GetRequestService.viewTables(cookie);
    }

    // GET /api/viewRows
    @PostMapping("/api/viewRows")
    public ResponseEntity<ArrayList<HashMap<String, Object>>> viewTable(@RequestBody Map<String, String> req,
            @CookieValue("connection") String cookie)
            throws SQLException, JsonParseException, JsonMappingException, IOException {
        return GetRequestService.viewRows(req, cookie);
    }

    // POST /api/connectDB
    @PostMapping("/api/connectDB")
    public ResponseEntity<Connection> connection(@RequestBody Connection connection, HttpServletResponse response)
            throws SQLException, UnsupportedEncodingException {
        return PostRequestService.ConnectDB(connection, response);
    }

    // POST /api/table
    @PostMapping("/api/table")
    public ResponseEntity<String> postTable(@RequestBody PostTable req,
            @CookieValue("connection") String cookie)
            throws SQLException, UnsupportedEncodingException, JSONException {
        return PostRequestService.createTable(req, cookie);
    }

    // POST /api/row
    @PostMapping("/api/row")
    public ResponseEntity<String> postRow(@RequestBody PostRow req, @CookieValue("connection") String cookie)
            throws SQLException, UnsupportedEncodingException, JSONException {
        return PostRequestService.createRow(req, cookie);
    }

    // DELETE /api/row
    @DeleteMapping("/api/row")
    public ResponseEntity<String> deleteRow(@RequestBody DeleteRow req,
            @CookieValue("connection") String cookie)
            throws SQLException, UnsupportedEncodingException, JSONException {
        return DeleteRequestService.deleteRow(req, cookie);
    }

    // DELETE /api/table
    @DeleteMapping("/api/table")
    public ResponseEntity<String> deleteTable(@RequestBody Map<String, String> req,
            @CookieValue("connection") String cookie)
            throws SQLException, UnsupportedEncodingException, JSONException {
        return DeleteRequestService.deleteTable(req, cookie);
    }
}
