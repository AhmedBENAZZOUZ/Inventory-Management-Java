package projectdb;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.sql.ResultSet;

public class Type {

    private int idType;
    private String nomType;
    private Categorie cat;

    public Type(int idType, String nomType, Categorie cat) {
        this.idType = idType;
        this.nomType = nomType;
        this.cat = cat;
    }

    public int getIdType() {
        return idType;
    }

    public void setIdType(int idType) {
        this.idType = idType;
    }

    public String getNomType() {
        return nomType;
    }

    public void setNomType(String nomType) {
        this.nomType = nomType;
    }

    public Categorie getCat() {
        return cat;
    }

    public void setCat(Categorie cat) {
        this.cat = cat;
    }

    @Override
    public String toString() {
        return "Type{"
                + "idType=" + idType
                + ", nomType='" + nomType + '\''
                + ", cat=" + cat
                + '}';
    }

    public static void createTypeTable(Statement st) throws SQLException {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS Type ("
                + "idType INT PRIMARY KEY,"
                + "nomType VARCHAR(255),"
                + "idCat INT,"
                + "FOREIGN KEY (idCat) REFERENCES Categorie(idCat)"
                + ")";
        st.executeUpdate(createTableQuery);
        System.out.println("Type table created successfully.");
    }

    public void insertType() throws SQLException {
        Statement st = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String insertQuery = "INSERT INTO Type (idType, nomType, idCat) VALUES ("
                    + idType + ", '"
                    + nomType + "', "
                    + cat.getIdCat() + ")";
            st.executeUpdate(insertQuery);
            System.out.println("Type inserted successfully.");
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }                   

    public static Type ajoutType() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Donner le ID du Type:");
        int idType = scanner.nextInt();
        scanner.nextLine();  // Consume newline
        System.out.println("Donner le nom du Type:");
        String nomType = scanner.nextLine();
        System.out.println("Donner le ID du Categorie:");
        int idCat = scanner.nextInt();
        scanner.nextLine();
        Categorie cat = Categorie.getCategorieById(idCat);
        if (cat == null) {
            System.out.println("Categorie not found.");
            return null;
        }

        Type type = new Type(idType, nomType, cat);
        type.insertType();
        return type;
    }
    public static void deleteType() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("donner le ID du Type pour le supprimer:");
        int idtype = scanner.nextInt();
        scanner.nextLine();  
        Statement st = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String deleteQuery = "DELETE FROM Type WHERE idType = " + idtype;
            st.executeUpdate(deleteQuery);
            System.out.println("Type vaec ID " + idtype + " est supprime.");
        } finally {
            if (st != null) {
                st.close();
            }
        }
    }
    public static void showTypes() throws SQLException {
        Statement st = null;
        ResultSet rs = null;
        try {
            st = DatabaseManager.cnx.createStatement();
            String query = "SELECT * FROM Type";
            rs = st.executeQuery(query);
            System.out.println("Types:");
            while (rs.next()) {
                int idType = rs.getInt("idType");
                String nomType = rs.getString("nomType");
                int idCat = rs.getInt("idCat");
                System.out.println("ID: " + idType + ", Name: " + nomType + ", Category ID: " + idCat);
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
