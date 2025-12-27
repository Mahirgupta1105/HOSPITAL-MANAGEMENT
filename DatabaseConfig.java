import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration loader for database settings
 * Loads database credentials from db.properties file
 */
public class DatabaseConfig {
    
    private static final String CONFIG_FILE = "db.properties";
    private static Properties properties = new Properties();
    private static boolean loaded = false;
    
    static {
        loadProperties();
    }
    
    /**
     * Loads database properties from configuration file
     */
    private static void loadProperties() {
        if (loaded) return;
        
        try (InputStream input = DatabaseConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                System.err.println("Unable to find " + CONFIG_FILE + ", using defaults");
                setDefaults();
                return;
            }
            properties.load(input);
            loaded = true;
        } catch (IOException e) {
            System.err.println("Error loading database configuration: " + e.getMessage());
            setDefaults();
        }
    }
    
    /**
     * Sets default database configuration
     */
    private static void setDefaults() {
        properties.setProperty("db.url", "jdbc:mysql://localhost:3306/hospital");
        properties.setProperty("db.username", "root");
        properties.setProperty("db.password", "root");
        properties.setProperty("db.driver", "com.mysql.cj.jdbc.Driver");
    }
    
    /**
     * Gets database URL
     * @return Database URL
     */
    public static String getUrl() {
        return properties.getProperty("db.url");
    }
    
    /**
     * Gets database username
     * @return Database username
     */
    public static String getUsername() {
        return properties.getProperty("db.username");
    }
    
    /**
     * Gets database password
     * @return Database password
     */
    public static String getPassword() {
        return properties.getProperty("db.password");
    }
    
    /**
     * Gets database driver class name
     * @return Driver class name
     */
    public static String getDriver() {
        return properties.getProperty("db.driver");
    }
}
