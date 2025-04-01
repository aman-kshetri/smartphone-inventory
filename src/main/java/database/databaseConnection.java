package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class databaseConnection {
	private final static String databaseName = "phones";
	private final static String username = "root";
	private final static String password = "";
	private final static String jdbcUrl = "jdbc:mysql://localhost:3306/" + databaseName;
	
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Connection con = null;
		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection(jdbcUrl, username, password);
		if(con == null) {
			System.out.println("No database connection has been made.");
		}
		else {
			System.out.println("Database Connection has been made");
		}
		return con;
	}
	
}
