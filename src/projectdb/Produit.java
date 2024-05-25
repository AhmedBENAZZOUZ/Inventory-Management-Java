package projectdb;

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

}
