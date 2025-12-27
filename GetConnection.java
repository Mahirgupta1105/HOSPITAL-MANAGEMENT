import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

<<<<<<< HEAD
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
=======
public class GetConnection {
	public static Connection c = null;
	private GetConnection()
	{
		
	}
	public static Connection getConnection(){
		if(c == null) {
			try {
				c = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital","root","root");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return c;
		}else {
			return c;	
		}
	}

>>>>>>> 097da98d41bf87d6e4bf7a3701e59bea6a3a0510
}
