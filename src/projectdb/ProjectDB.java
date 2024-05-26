package projectdb;

import java.sql.SQLException;
import java.sql.Statement;

public class ProjectDB {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // Connect to the database
        DatabaseManager.connectToDatabase();
        Statement st = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            //Categorie.deleteCategorie();
            //Type newtype = Type.ajoutType();
            //System.out.println("New Type: " + newtype);
            //Type.deleteType();
            Categorie.showCategories();
            Categorie.ajoutCategories();
            Type.ajoutType();
            Type.showTypes();
            Produit.createProduitTable(st);
            Produit.ajoutProduit();
            Produit.showProduits();
            Produit.deleteProduit();
        } finally {
            if (st != null) {
                st.close(); // Close the statement
            }
        }
        // Close the database connection
        DatabaseManager.closeConnection();
    }
}
