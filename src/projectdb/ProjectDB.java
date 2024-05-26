package projectdb;

import java.sql.SQLException;

public class ProjectDB {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Connect to the database
        DatabaseManager.connectToDatabase();

        //Categorie.deleteCategorie();
        //Type newtype = Type.ajoutType();
        //System.out.println("New Type: " + newtype);
        //Type.deleteType();
        Categorie.showCategories();
         Type.showTypes();
        // Close the database connection
        DatabaseManager.closeConnection();
    }
}
