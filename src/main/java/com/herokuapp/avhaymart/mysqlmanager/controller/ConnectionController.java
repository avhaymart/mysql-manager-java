package com.herokuapp.avhaymart.mysqlmanager.controller;

import com.herokuapp.avhaymart.mysqlmanager.models.Connection;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConnectionController {
	// @CookieValue("foo") String fooCookie
	@PostMapping("/connection")
	public ResponseEntity<Connection> connection(@RequestBody Connection connection, HttpServletResponse response)
			throws SQLException, UnsupportedEncodingException {
		System.out.println(connection.toString());
		java.sql.Connection connect = DriverManager
				.getConnection("jdbc:mysql://" + connection.getHost() + "/" + connection.getDatabase() + "?" + "user="
						+ connection.getUser() + "&password=" + connection.getPassword());
		Statement statement = connect.createStatement();
		// Result set get the result of the SQL query
		ResultSet resultSet = statement.executeQuery("SHOW TABLES;");
		System.out.println(resultSet);
		response.addCookie(new Cookie("connection",  URLEncoder.encode(connection.toString(), "UTF-8")));

		return ResponseEntity.status(HttpStatus.OK).body(connection);
	}
}