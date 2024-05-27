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

            //Type.createTypeTable(st);
            //Type newtype = Type.ajoutType();
            //System.out.println("New Type: " + newtype);
            //Type.deleteType();
            Categorie.showCategories();
            //Categorie.ajoutCategories();   
            Type.showTypes();
            //Type.ajoutType();
            //Type.showTypes();
            //Produit.createProduitTable(st);
            //Produit.displayStock(st);
            //Produit.createProduitSelledTable(st);
            //Produit.sellProduct();
            //Produit.showTrace();
            //Produit.StatMonth();
            //Produit.StatYear();
            //Produit.ajoutProduit();
            Produit.displayStock(st);
            //Type.showTypes();
            //Produit.showProduits();
            //Produit.deleteProduit();
            Menu menu =new Menu();
            menu.displayMainMenu();
        } finally {
            if (st != null) {
                st.close(); // Close the statement
            }
        }
        // Close the database connection
        DatabaseManager.closeConnection();
    }
    
}
