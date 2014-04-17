package com.permata.queue.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.Scanner;

import com.mysql.jdbc.Connection;

public class Connector {
	public String username = "root";
	public String password = "";
	private String server = "";

	public Connector() {

	}

	private String getFileServer() throws Exception {
		File file = new File("queue.properties");
		// System.out.println("file.exists() : " + file.exists());
		
		if (!file.exists())
			file.createNewFile();
		String s=null;
		try {
			Scanner sc = new Scanner(file);
			 s = sc.nextLine();
			sc.close();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
			throw new Exception("Server belum diisi");
		}
	
		return s;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) throws Exception {
		PrintWriter out;
		try {
			out = openWriter();
			out.println(server);
			out.close();
		} catch (Exception e) {
			// TODO: handle exception
			throw new Exception(e);
		}
		this.server = server;
	}

	private PrintWriter openWriter() throws Exception {
		try {

			File file = new File("queue.properties");
			if (!file.exists())
				file.createNewFile();
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter(file)), true);
			return out;
		} catch (IOException e) {
			System.out.println("I/O Error");
			throw new Exception(e);
		}
	}

	public Connection getConnection() throws SQLException {
		Connection conn = null;
		try {
			server = getFileServer();
			Class.forName("org.gjt.mm.mysql.Driver");
			conn = (Connection) DriverManager.getConnection("jdbc:mysql://"
					+ server + ":3306/queue", username, password);
		} catch (SQLException sq) {
			throw sq;
		} catch (Exception e) {
			e.printStackTrace();
			throw new SQLException(e.toString());
		}
		return conn;
	}

	public void closeConnection(Connection conn) throws SQLException {
		conn.close();
	}
}
