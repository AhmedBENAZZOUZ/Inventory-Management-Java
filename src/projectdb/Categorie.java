package projectdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Categorie {

    private int idCat;
    private String nomCat;

    // Constructor
    public Categorie(int idCat, String nomCat) {
        this.idCat = idCat;
        this.nomCat = nomCat;
    }

    // Getters and setters
    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getNomCat() {
        return nomCat;
    }

    public void setNomCat(String nomCat) {
        this.nomCat = nomCat;
    }

    // toString method for displaying Categorie information
    @Override
    public String toString() {
        return "Categorie{"
                + "idCat=" + idCat
                + ", nomCat='" + nomCat + '\''
                + '}';
    }

    public static void createCategorieTable() throws SQLException {
        Statement st = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Categorie ("
                    + "idCat INT PRIMARY KEY,"
                    + "nomCat VARCHAR(255)"
                    + ")";
            st.executeUpdate(createTableQuery);
            System.out.println("Categorie table created successfully.");
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    // Method to insert this Categorie into the database
    public void insertCategorie() throws SQLException {
        Statement st = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String insertQuery = "INSERT INTO Categorie (idCat, nomCat) VALUES ("
                    + this.idCat + ", '"
                    + this.nomCat + "')";
            st.executeUpdate(insertQuery);
            System.out.println("Categorie inserted successfully.");
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public static Categorie getCategorieById(int idCat) throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String query = "SELECT * FROM Categorie WHERE idCat = " + idCat;
            rs = st.executeQuery(query);
            if (rs.next()) {
                int id = rs.getInt("idCat");
                String name = rs.getString("nomCat");
                return new Categorie(id, name);
            } else {
                return null;
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }

    public static Categorie ajoutCategories() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Donner le ID de la Categorie:");
        int idCat = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.println("Donner le nom de la Categorie:");
        String nomCat = scanner.nextLine();
        Categorie categorie = new Categorie(idCat, nomCat);
        categorie.insertCategorie();
        return categorie;
    }

    public static void deleteCategorie() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the ID of the category you want to delete:");
        int idCat = scanner.nextInt();
        scanner.nextLine();
        Statement st = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String deleteQuery = "DELETE FROM Categorie WHERE idCat = " + idCat;
            st.executeUpdate(deleteQuery);
            System.out.println("Category with ID " + idCat + " deleted successfully.");
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public static void showCategories() throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String query = "SELECT * FROM Categorie";
            rs = st.executeQuery(query);
            if (!rs.isBeforeFirst()) { // rs.isBeforeFirst() returns false if the ResultSet is empty
                System.out.println("There are no categories!");
            } else {
                System.out.println("Categories:");
                while (rs.next()) {
                    int idCat = rs.getInt("idCat");
                    String nomCat = rs.getString("nomCat");
                    System.out.println("ID: " + idCat + ", Name: " + nomCat);
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (st != null) {
                st.close();
            }
        }
    }
}
