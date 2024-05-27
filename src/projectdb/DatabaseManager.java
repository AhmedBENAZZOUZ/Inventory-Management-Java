package projectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {

    static Connection cnx = null;
    private static Statement st = null;

    public static void connectToDatabase() throws ClassNotFoundException, SQLException {
        // Load the JDBC driver
        Class.forName("com.mysql.jdbc.Driver");
        // Establish connection to the database
        System.out.println("Connection to the database");

        cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase?characterEncoding=utf8", "root", "oracle");
        st = cnx.createStatement();
    }

    public static void closeConnection() throws SQLException {
        if (st != null) st.close();
        if (cnx != null) cnx.close();
    }
}
