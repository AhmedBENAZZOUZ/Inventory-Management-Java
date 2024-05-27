package projectdb;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.time.LocalDate;

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

            String updateQuery = "UPDATE Type SET Qte = Qte + 1 WHERE idType = " + typ.getIdType();
            st.executeUpdate(updateQuery);
            System.out.println("Type quantity updated successfully.");
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

    public static void displayStock(Statement stmt) throws SQLException {
        ResultSet rs = null;
        try {

            String query = "SELECT T.nomType AS Type, "
                    + "C.nomCat AS Category, "
                    + "T.Qte AS Qte, "
                    + "P.id AS id_Pdt, "
                    + "P.day AS Day, "
                    + "P.month AS Month, "
                    + "P.year AS Year "
                    + "FROM Produit P "
                    + "JOIN Type T ON P.idType = T.idType "
                    + "JOIN Categorie C ON T.idCat = C.idCat "
                    + "ORDER BY T.nomType, P.id;";

            rs = stmt.executeQuery(query);
            System.out.println("*********************************************************************************");
            System.out.println("* Etat du stock : ***************************************************************");
            System.out.println("*                                                                               *");
            System.out.println("* Category     | Type           | Qte  | id-Pdt | date_exp             *");
            System.out.println("* ----------------------------------------------------------------------------- *");

            String previousType = "";
            while (rs.next()) {
                String category = rs.getString("Category");
                String type = rs.getString("Type");
                int qte = rs.getInt("Qte");
                int idPdt = rs.getInt("id_Pdt");
                int day = rs.getInt("Day");
                int month = rs.getInt("Month");
                int year = rs.getInt("Year");

                if (!type.equals(previousType)) {
                    System.out.printf("* %-13s | %-15s | %-4d | %-6d | %2d / %2d / %d  *\n", category, type, qte, idPdt, day, month, year);
                } else {
                    System.out.printf("* %-13s | %-15s |       | %-6d | %2d / %2d / %d  *\n", "", "", idPdt, day, month, year);
                }
                previousType = type;
            }

            System.out.println("* ----------------------------------------------------------------------------- *");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
        }
    }

    public static Produit getProduitById(int idProduit) throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String query = "SELECT * FROM Produit WHERE id = " + idProduit;
            rs = st.executeQuery(query);

            if (rs.next()) {
                String nom = rs.getString("nom");
                int idType = rs.getInt("idType");
                int day = rs.getInt("day");
                int month = rs.getInt("month");
                int year = rs.getInt("year");

                // Fetch the corresponding Type object
                Type type = Type.getTypeById(idType);
                if (type == null) {
                    System.out.println("Type not found for the given idType.");
                    return null;
                }

                // Create MaDate object
                MaDate dateExpiration;
                try {
                    dateExpiration = new MaDate(day, month, year);
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid date: " + e.getMessage());
                    return null;
                }

                // Create and return the Produit object
                return new Produit(idProduit, nom, type, dateExpiration);
            } else {
                System.out.println("Produit not found.");
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

    public static void createProduitSelledTable(Statement st) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Trace ("
                + "idPdt INT PRIMARY KEY,"
                + "nomPdt VARCHAR(255),"
                + "idType INT,"
                + "nomType VARCHAR(255),"
                + "idCat INT,"
                + "nomCat VARCHAR(255),"
                + "day INT,"
                + "month INT,"
                + "year INT"
                + ")";
        st.executeUpdate(createTableQuery);
        System.out.println("Produit table created successfully.");
    }

    public void insertTrace() throws SQLException {
        Statement st = null;
        try {
            st = DatabaseManager.cnx.createStatement();

            // Get the current date
            MaDate currentDate = new MaDate(LocalDate.now().getDayOfMonth(), LocalDate.now().getMonthValue(), LocalDate.now().getYear());

            String insertQuery = "INSERT INTO Trace (idPdt, nomPdt, idType, nomType, idCat, nomCat, day, month, year) VALUES ("
                    + id + ", '"
                    + nom + "', "
                    + typ.getIdType() + ", '"
                    + typ.getNomType() + "', "
                    + typ.getCat().getIdCat() + ", '"
                    + typ.getCat().getNomCat() + "', "
                    + currentDate.getJJ() + ", "
                    + currentDate.getMM() + ", "
                    + currentDate.getAA() + ")";

            st.executeUpdate(insertQuery);
            System.out.println("Product sold inserted successfully.");
        } finally {
            if (st != null) {
                try {
                    st.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void sellProduct() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Donner le ID du Produit pour le vendre:");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        // Get the product details by ID
        Produit produit = Produit.getProduitById(productId);
        if (produit == null) {
            System.out.println("Produit not found.");
            return;
        }
        produit.insertTrace();
        Statement st = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String deleteQuery = "DELETE FROM Produit WHERE id = " + productId;
            st.executeUpdate(deleteQuery);
            System.out.println("Produit avec ID " + productId + " est supprime.");
        } finally {
            if (st != null) {
                st.close();
            }

        }

    }

    public static void showTrace() throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String query = "SELECT * FROM Trace";
            rs = st.executeQuery(query);
            if (!rs.isBeforeFirst()) {
                System.out.println("There are no products in the trace!");
            } else {
                System.out.println("Trace:");
                while (rs.next()) {
                    int idProduit = rs.getInt("idPdt");
                    String nomProduit = rs.getString("nomPdt");
                    int idType = rs.getInt("idType");
                    String nomType = rs.getString("nomType");
                    int idCat = rs.getInt("idCat");
                    String nomCat = rs.getString("nomCat");
                    int day = rs.getInt("day");
                    int month = rs.getInt("month");
                    int year = rs.getInt("year");

                    // Display the product details
                    System.out.println("ID: " + idProduit + ", Nom: " + nomProduit + ", ID Type: " + idType + ", Nom Type: " + nomType + ", ID Categorie: " + idCat + ", Nom Categorie: " + nomCat + ", Date expiration: " + day + "/" + month + "/" + year);
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

    public static void StatMonth() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the month:");
        int month = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Enter the year:");
        int year = scanner.nextInt();
        scanner.nextLine();

        Statement st = null;
        ResultSet rs = null;

        try {
            st = DatabaseManager.cnx.createStatement();

            // Total number of products sold in the specified month and year
            String totalQuery = "SELECT COUNT(*) AS total FROM Trace WHERE month = " + month + " AND year = " + year;
            rs = st.executeQuery(totalQuery);

            if (rs.next()) {
                int total = rs.getInt("total");
                System.out.println("Total number of products sold in " + month + "/" + year + ": " + total);
            } else {
                System.out.println("No products sold in the specified month and year.");
            }

            // Number of sales per category
            String categoryQuery = "SELECT nomCat, COUNT(*) AS total FROM Trace WHERE month = " + month + " AND year = " + year + " GROUP BY nomCat";
            rs = st.executeQuery(categoryQuery);

            while (rs.next()) {
                String category = rs.getString("nomCat");
                int categoryTotal = rs.getInt("total");
                System.out.println("Number of products sold in category " + category + ": " + categoryTotal);
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

    public static void StatYear() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the year:");
        int year = scanner.nextInt();
        scanner.nextLine();

        Statement st = null;
        ResultSet rs = null;

        try {
            st = DatabaseManager.cnx.createStatement();

            // Total number of products sold in the specified year
            String totalQuery = "SELECT COUNT(*) AS total FROM Trace WHERE year = " + year;
            rs = st.executeQuery(totalQuery);

            if (rs.next()) {
                int total = rs.getInt("total");
                System.out.println("Total number of products sold in " + year + ": " + total);
            } else {
                System.out.println("No products sold in the specified year.");
            }

            // Number of sales per category
            String categoryQuery = "SELECT nomCat, COUNT(*) AS total FROM Trace WHERE year = " + year + " GROUP BY nomCat";
            rs = st.executeQuery(categoryQuery);

            while (rs.next()) {
                String category = rs.getString("nomCat");
                int categoryTotal = rs.getInt("total");
                System.out.println("Number of products sold in category " + category + ": " + categoryTotal);
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
