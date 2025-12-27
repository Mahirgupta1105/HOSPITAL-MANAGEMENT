import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection manager
 * Uses DatabaseConfig for secure configuration management
 * Implements singleton pattern for connection reuse
 */
public class GetConnection {
	private static Connection c = null;
	
	/**
	 * Private constructor to prevent instantiation
	 */
	private GetConnection() {
		// Prevent instantiation
	}
	
	/**
	 * Gets a database connection using configuration from db.properties
	 * @return Database connection
	 */
	public static Connection getConnection(){
		if(c == null) {
			try {
				// Load database configuration from external file
				String url = DatabaseConfig.getUrl();
				String username = DatabaseConfig.getUsername();
				String password = DatabaseConfig.getPassword();
				
				c = DriverManager.getConnection(url, username, password);
				System.out.println("Database connection established successfully");
			} catch (SQLException e) {
				System.err.println("Failed to establish database connection: " + e.getMessage());
				e.printStackTrace();
			}
			return c;
		} else {
			return c;	
		}
	}
	
	/**
	 * Closes the database connection
	 */
	public static void closeConnection() {
		if (c != null) {
			try {
				c.close();
				c = null;
				System.out.println("Database connection closed");
			} catch (SQLException e) {
				System.err.println("Error closing database connection: " + e.getMessage());
				e.printStackTrace();
			}
		}
	}
}
