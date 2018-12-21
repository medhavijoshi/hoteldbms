import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
/**
 * CS157A Hotel Management System
 * @author Jonathan Wong, Medhavi Joshi
 * 
 * Responsible for DB Connection
 */

public class ConnectionUtility {
	private static final String databaseFile = "db.properties";
	private static Properties props = new Properties();
	private static FileInputStream inputStream;
	
	/**
	 * Get a connection to database using DriverManager
	 * @return a connection to database from driver manager
	 */
	public static Connection getConnectionByDriverManager() {
		Connection conn = null;
		
		try {
			inputStream = new FileInputStream(databaseFile);
			props.load(inputStream); // load db properties into props
			
			// Load JDBC driver by Class.forName
			Class.forName(props.getProperty("MYSQL_DB_DRIVER_CLASS"));

			// Get connection from driver manager 
			conn = DriverManager.getConnection(props.getProperty("MYSQL_DB_URL"), 
					                           props.getProperty("MYSQL_DB_USERNAME"), 
					                           props.getProperty("MYSQL_DB_PASSWORD"));

		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return conn;
	}
	
	/**
	 * Get a connection to database using MysqlDataSource
	 * @return a connection to database from data source
	 */
	public static Connection getConnectionByDataSource() {
		Connection conn = null;
		
		try {
			inputStream = new FileInputStream(databaseFile);
			props.load(inputStream);
			
			// Create a datasource
			MysqlDataSource ds = new MysqlDataSource();
			ds.setURL(props.getProperty("DB_URL_MYSQL"));
			ds.setUser(props.getProperty("DB_USERNAME_MYSQL"));
			ds.setPassword(props.getProperty("DB_PASSWORD_MYSQL"));
			
			// Get connection from data source
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conn;
	}
	
	/**
	 * Get a statement from a given connection
	 * @param conn - a connection
	 * @return a statement 
	 */
	public static Statement getStatement(Connection conn) {
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			return stmt;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return stmt;
	}
	
	/**
	 * Close a connection
	 * @param conn - a connection to be closed
	 */
	public static void closeConnection(Connection conn) {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Close a statement
	 * @param stmt - a statement to be closed
	 */
	public static void closeStatement(Statement stmt) {
		try {
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
