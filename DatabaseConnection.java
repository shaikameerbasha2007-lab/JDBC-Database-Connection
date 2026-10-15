import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    // Database connection parameters
    private static final String URL = "jdbc:mysql://localhost:3306/testdb";
    private static final String USER = "root";
    private static final String PASSWORD = "password";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            // Step 1: Register and load the JDBC Driver (Optional in modern JDBC 4.0+)
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("JDBC Driver successfully loaded.");

            // Step 2: Establish the database connection
            System.out.println("Connecting to database...");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connection established successfully!");

            // Step 3: Create a Statement object to execute queries
            statement = connection.createStatement();

            // Step 4: Execute a SQL query (Example: fetching server version)
            String query = "SELECT VERSION() AS version";
            resultSet = statement.executeQuery(query);

            // Step 5: Process the result
            if (resultSet.next()) {
                String dbVersion = resultSet.getString("version");
                System.out.println("Database Version: " + dbVersion);
            }

        } catch (ClassNotFoundException e) {
            System.err.println("JDBC Driver not found. Ensure the MySQL connector JAR is added to classpath.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.err.println("Database connection error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Step 6: Close resources in reverse order of opening to prevent resource leaks
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
                System.out.println("Database resources closed successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
