package controleur;

import com.sun.tools.javac.Main;
import modele.*;
import vue.*;
import controleur.*;

import java.sql.*;


public class MainControlleur {

    private VueConsole vue;
    private Agence agence;
    private Loueur loueur;
    private Voiture voiture;
    private Connection connection;

    public MainControlleur()
    {
        this.loueur = new Loueur(0,null,null,null,null,0,null);
        this.vue = new VueConsole();
        this.agence = new Agence();
        this.voiture = new Voiture();
        this.connection = null;
    }

    public static Connection connecter() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost/bddprojetjavalocationvoiture";
        String username = "root";
        String password = "ruben2003"; // Modifier selon l'appareil
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public void tenterConnexion() throws SQLException {
        String[] ID = vue.DemandeIdentifiant();
        boolean isValid = VerificationLogin(ID[0], ID[1]);
        if (isValid) {
            vue.AffichageMessage("Connexion réussie !");
            // Redirection ou affichage de la vue suivante
        } else {
            vue.AffichageMessage("Erreur de connexion. Veuillez réessayer.");
        }
    }

    public void fermerConnexion() {
        if (this.connection != null) {
            try {
                this.connection.close();
            } catch (SQLException e) {
                System.out.println("Erreur lors de la fermeture de la connexion à la base de données: " + e.getMessage());
            }
        }
    }

    public boolean VerificationLogin(String name, String mdp) throws SQLException
    {
        this.connection = connecter();
        String sql = "SELECT IDLoueur, Nom, Prenom, Email, Tel, Type, MotdePasse FROM loueur WHERE Email = ? AND MotdePasse = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, name);
            statement.setString(2, mdp);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    this.loueur = new Loueur(
                            resultSet.getInt("IDLoueur"),
                            resultSet.getString("Nom"),
                            resultSet.getString("Prenom"),
                            resultSet.getString("Email"),
                            resultSet.getString("Tel"),
                            resultSet.getByte("Type"),
                            resultSet.getString("MotdePasse")
                    );
                    return true;
                }
            }
        }
        return false;
    }

    public Loueur getCurrentUser(){
        return this.loueur;
    }

    public void ajouterLoueur(String nom, String prenom, String email, String tel, String motDePasse) {
        String sql = "INSERT INTO loueur (Nom, Prenom, Email, Tel, Type, MotDePasse) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = this.connecter();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nom);
            pstmt.setString(2, prenom);
            pstmt.setString(3, email);
            pstmt.setString(4, tel);
            pstmt.setByte(5, (byte) 2); // Type à 2 pour nouveau membre
            pstmt.setString(6, motDePasse);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try (Connection connection = connecter()) {

            MainControlleur maincontrol = new MainControlleur();

            LoginVue loginPage = new LoginVue(null,maincontrol);
            loginPage.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
