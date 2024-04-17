package modele;

import java.sql.*;

public class Agence {
    private int id;
    private String nom;
    private String adresse;
    private String email;
    private String tel;


    // Méthodes pour interagir avec la base de données
    public static void ajouterAgence(Connection connection, String nom, String adresse, String email, String tel) throws SQLException {
        String sql = "INSERT INTO agence (Nom, Adresse, Email, Tel) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, nom);
            statement.setString(2, adresse);
            statement.setString(3, email);
            statement.setString(4, tel);
            statement.executeUpdate();
        }
    }

    public static void supprimerAgence(Connection connection, int idAgence) throws SQLException {
        String sql = "DELETE FROM agence WHERE `ID Agence` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idAgence);
            statement.executeUpdate();
        }
    }
}
