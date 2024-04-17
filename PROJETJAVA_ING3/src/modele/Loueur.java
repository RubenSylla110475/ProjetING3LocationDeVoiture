package modele;

import java.sql.*;

public class Loueur {
    private int id;
    private String nom;
    private String prenom;
    private String email;
    private String tel;
    private int type;
    private String motDePasse;

    public Loueur(int id, String nom, String prenom, String email, String tel, int type, String mdp)
    {
        this.id = id;
        this.email = email;
        this.type = type;
        this.nom = nom;
        this.prenom = prenom;
        this.motDePasse = mdp;
        this.tel = tel;
    }


    public static void ajouterLoueur(Connection connection, String nom, String prenom, String email, String tel, byte type, String motDePasse) throws SQLException {
        String sql = "INSERT INTO loueur (Nom, Prenom, Email, Tel, Type, MotdePasse) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nom);
            statement.setString(2, prenom);
            statement.setString(3, email);
            statement.setString(4, tel);
            statement.setByte(5, type);
            statement.setString(6, motDePasse);
            statement.executeUpdate();
        }
    }

    public static void supprimerLoueur(Connection connection, int idLoueur) throws SQLException {
        String sql = "DELETE FROM loueur WHERE IDLoueur = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idLoueur);
            statement.executeUpdate();
        }
    }

    public int getId() {
        return id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public int getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTel() {
        return tel;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public void setType(byte type) {
        this.type = type;
    }
}

