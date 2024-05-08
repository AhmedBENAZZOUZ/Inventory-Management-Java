package projectdb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProjectDB {

    public static void main(String[] args) throws ClassNotFoundException {

        Connection cnx = null;
        Statement st = null;
        try {
            //chargement du pilote
            Class.forName("com.mysql.jdbc.Driver");
            //connection a la base de données
            System.out.println("connexion a la base de donnees");

            cnx = DriverManager.getConnection("jdbc:mysql://localhost:3306/mydatabase?characterEncoding=utf8", "root", "oracle");

            st = cnx.createStatement();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS Categorie ("
                    + "idCat INT PRIMARY KEY,"
                    + "nomCat VARCHAR(255)"
                    + ")";
            st.executeUpdate(createTableQuery);
            System.out.println("Categorie table created successfully.");

            String insertDataQuery1 = "INSERT INTO Categorie (idCat, nomCat) VALUES (1, 'Category1')";
            st.executeUpdate(insertDataQuery1);
            String insertDataQuery2 = "INSERT INTO Categorie (idCat, nomCat) VALUES (2, 'Category2')";
            st.executeUpdate(insertDataQuery2);
            System.out.println("Data inserted into Categorie table successfully.");

            ResultSet res = st.executeQuery("select * from Categorie");
            while (res.next()) {
                int id = res.getInt("idCat");
                String name = res.getString("nomCat");
                Categorie c = new Categorie(id, name);
                System.out.println(c);
            }

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
