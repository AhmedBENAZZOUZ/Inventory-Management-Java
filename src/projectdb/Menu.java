package projectdb;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Menu {

    public void displayMainMenu() throws SQLException {
        int choice;
        Scanner scanner = new Scanner(System.in);
        
        

        do {
            System.out.println("******Menu Principal******");
            System.out.println("1- Gestion de Stock");
            System.out.println("2- Vente et Statistiques");
            System.out.println("0- Quitter");
            choice = getChoice(scanner);

            switch (choice) {
                case 0:
                    System.out.println("Au revoir!");
                    break;
                case 1:
                    gestionStock();
                    break;
                case 2:
                    venteStat();
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre valide.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    public int getChoice(Scanner scanner) {
        int choice;
        while (true) {
            System.out.print("Entrez votre choix : ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
                return choice;
            } else {
                System.out.println("Choix invalide. Veuillez entrer un nombre entier.");
                scanner.nextLine();
            }
        }
    }

    public void venteStat() throws SQLException {
        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("******Vente et Statistiques******");
            System.out.println("0- Menu Principal");
            System.out.println("1- Vente de Produits");
            System.out.println("2- Statistiques");
            choice = getChoice(scanner);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    venteProduit();
                    break;
                case 2:
                    Stat();
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre entier.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    public void Stat() throws SQLException {
        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("******Statistiques******");
            System.out.println("0- Menu Principal");
            System.out.println("1- Statistiques par mois");
            System.out.println("2- Statistiques par annee");
            System.out.println("3- Retour Menu Vente et Statistiques");
            choice = getChoice(scanner);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    System.out.println("1- Statistiques par mois");
                    // Call your method for statistics by month here
                    Produit.StatMonth();
                    break;
                case 2:
                    System.out.println("2- Statistiques par annee");
                    // Call your method for statistics by year here
                    Produit.StatYear();
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre valide.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    public void venteProduit() throws SQLException {
        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("******Vente des Produits******");
            System.out.println("0- Menu Principal");
            System.out.println("1- vendre produit");
            System.out.println("2- Retour Menu Vente et Statistiques");
            choice = getChoice(scanner);

            switch (choice) {
                case 0:
                    this.displayMainMenu();
                    break;
                case 1:
                    Produit.sellProduct();
                    break;
                case 2:
                    this.venteStat();
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre entier.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    public void gestionStock() throws SQLException {
        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("******Gestion de Stock******");
            System.out.println("0- Menu Principal");
            System.out.println("1- Gestion des Categories");
            System.out.println("2- Gestion des Types");
            System.out.println("3- Gestion des Produits");
            System.out.println("4- Affichage du Stock");
            choice = getChoice(scanner);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    gestionCat();
                    break;
                case 2:
                    gestionType();
                    break;
                case 3:
                    gestionProduit();
                    break;
                case 4:
                    afficheStock();
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre entier.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    public void gestionCat() throws SQLException {
        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("******Gestion des Categories******");
            System.out.println("0- Menu Principal");
            System.out.println("1- Ajout d'une nouvelle Categorie");
            System.out.println("2- Suppression d'une Categorie");
            System.out.println("3- Retour Menu Gestion de Stock");
            choice = getChoice(scanner);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    // Call method to add a new category here
                    Categorie.ajoutCategories();
                    break;
                case 2:
                    // Call method to delete a category here
                    Categorie.deleteCategorie();
                    break;
                case 3:
                    this.gestionStock();
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre valide.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    public void gestionType() throws SQLException {
        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("******Gestion des Types******");
            System.out.println("0- Menu Principal");
            System.out.println("1- Ajout d'un nouveau Type");
            System.out.println("2- Suppression d'un Type");
            System.out.println("3- Retour Menu Gestion de Stock");
            choice = getChoice(scanner);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    // Call method to add a new type here
                    Type.ajoutType();
                    break;
                case 2:
                    // Call method to delete a type here
                    Type.deleteType();
                    break;
                case 3:
                    this.gestionType();
                    break;
                default:
                    System.out.println("Choix invalid");
                    
            }
        } while (choice != 0);

        scanner.close();
    }


    public void gestionProduit() throws SQLException {
        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("******Gestion des Produits******");
            System.out.println("0- Menu Principal");
            System.out.println("1- Ajout d'un nouveau Produit");
            System.out.println("2- Suppression d'un Produit");
            System.out.println("3- Retour Menu Gestion de Stock");
            choice = getChoice(scanner);

            switch (choice) {
                case 0:
                    break;
                case 1:
                    // Call method to add a new product here
                    Produit.ajoutProduit();
                    break;
                case 2:
                    // Call method to delete a product here
                    Produit.deleteProduit();
                    break;
                case 3:
                    this.gestionProduit();
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre valide.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    public void afficheStock() throws SQLException {
        int choice;
        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("******Affichage du Stock******");
            System.out.println("0- Menu Principal");
            System.out.println("1- Affiche Stock");
            System.out.println("2- Retour Menu Gestion de Stock");
            choice = getChoice(scanner);

            switch (choice) {
                case 0:
                    this.displayMainMenu();
                    break;
                case 1:
                {
                    Statement st = null;
                    st = DatabaseManager.cnx.createStatement();
                    // Call method to display stock here
                    Produit.displayStock(st);
                }
                    break;


                case 2:
                    this.gestionStock();
                    break;
                default:
                    System.out.println("Choix invalide. Veuillez entrer un nombre valide.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }
}
