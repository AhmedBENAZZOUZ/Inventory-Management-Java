package projectdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Produit {

    private int id;
    private String nom;
    private Type typ;
    private MaDate date_expiration;

    // Constructor
    public Produit(int id, String nom, Type typ, MaDate date_expiration) {
        this.id = id;
        this.nom = nom;
        this.typ = typ;
        this.date_expiration = date_expiration;
    }

    // Getters and setters (if needed)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Type getTyp() {
        return typ;
    }

    public void setTyp(Type typ) {
        this.typ = typ;
    }

    public MaDate getDate_expiration() {
        return date_expiration;
    }

    public void setDate_expiration(MaDate date_expiration) {
        this.date_expiration = date_expiration;
    }

    @Override
    public String toString() {
        // Override the toString() method of the Object class
        return "Produit[id=" + id + ", nom=" + nom + ", typ=" + typ + ", date_expiration=" + date_expiration + "]";
    }

    public static void createProduitTable(Statement st) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Produit ("
                + "id INT PRIMARY KEY,"
                + "nom VARCHAR(255),"
                + "day INT,"
                + "month INT,"
                + "year INT,"
                + "idType INT,"
                + "FOREIGN KEY (idType) REFERENCES Type(idType)"
                + ")";
        st.executeUpdate(createTableQuery);
        System.out.println("Produit table created successfully.");
    }

    public void insertProduit() throws SQLException {
        Statement st = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String insertQuery = "INSERT INTO Produit (id, nom, day, month, year, idType) VALUES ("
                    + id + ", '"
                    + nom + "', "
                    + date_expiration.getJJ() + ", "
                    + date_expiration.getMM() + ", "
                    + date_expiration.getAA() + ", "
                    + typ.getIdType() + ")";
            st.executeUpdate(insertQuery);
            System.out.println("Produit inserted successfully.");
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public static Produit ajoutProduit() throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Prompt for Produit details
        System.out.println("Donner le ID du Produit:");
        int id = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.println("Donner le nom du Produit:");
        String nom = scanner.nextLine();

        System.out.println("Donner le ID du Type:");
        int idType = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Fetch the corresponding Type object
        Type type = Type.getTypeById(idType);
        if (type == null) {
            System.out.println("Type not found.");
            return null;
        }

        // Prompt for MaDate details
        System.out.println("Donner le jour de l'expiration (JJ):");
        int day = scanner.nextInt();

        System.out.println("Donner le mois de l'expiration (MM):");
        int month = scanner.nextInt();

        System.out.println("Donner l'année de l'expiration (AAAA):");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Create MaDate object
        MaDate dateExpiration;
        try {
            dateExpiration = new MaDate(day, month, year);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date: " + e.getMessage());
            return null;
        }

        // Create and insert the new Produit
        Produit produit = new Produit(id, nom, type, dateExpiration);
        produit.insertProduit();

        return produit;
    }

    public static void deleteProduit() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Donner le ID du Produit pour le supprimer:");
        int idProduit = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        Statement st = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String deleteQuery = "DELETE FROM Produit WHERE id = " + idProduit;
            st.executeUpdate(deleteQuery);
            System.out.println("Produit avec ID " + idProduit + " est supprimé.");
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }

    public static void showProduits() throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String query = "SELECT * FROM Produit";
            rs = st.executeQuery(query);
            if (!rs.isBeforeFirst()) {
                System.out.println("There are no products!");
            } else {
                System.out.println("Produits:");
                while (rs.next()) {
                    int idProduit = rs.getInt("id");
                    String nomProduit = rs.getString("nom");
                    int idType = rs.getInt("idType");
                    MaDate expirationDate = new MaDate(rs.getInt("day"), rs.getInt("month"), rs.getInt("year"));
                    Produit produit = new Produit(idProduit, nomProduit, Type.getTypeById(idType), expirationDate);
                    System.out.println(produit);
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
