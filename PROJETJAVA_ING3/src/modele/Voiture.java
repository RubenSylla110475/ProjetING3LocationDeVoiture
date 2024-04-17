package modele;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Voiture {
    private int id;
    private String modele;
    private String marque;
    private String immatriculation;
    private double kilometrage;
    private String couleur;
    private byte louee; // 0 pour non, 1 pour oui
    private int idLoueur;
    private int localisation; // Suppose que c'est l'ID de l'agence où la voiture est localisée
    private Date dateLocation;
    private double prixJour;
    private String image;
    private String classe;

    // Constructeur par défaut
    public Voiture() {}

    // Constructeur avec paramètres
    public Voiture(int id, String modele, String marque, String immatriculation, double kilometrage, String couleur, byte louee, int idLoueur, int localisation, Date dateLocation, double prixJour, String image, String Classe) {
        this.id = id;
        this.modele = modele;
        this.marque = marque;
        this.immatriculation = immatriculation;
        this.kilometrage = kilometrage;
        this.couleur = couleur;
        this.louee = louee;
        this.idLoueur = idLoueur;
        this.localisation = localisation;
        this.dateLocation = dateLocation;
        this.prixJour = prixJour;
        this.image = image;
        this.classe = Classe;
    }

    public Voiture(String immatriculation, double kilometrage, String couleur, int localisation,
                  String modele, String marque, double prixJour, String image)
    {
        this.immatriculation = immatriculation;
        this.kilometrage = kilometrage;
        this.couleur = couleur;
        this.localisation = localisation;
        this.modele = modele;
        this.marque = marque;
        this.prixJour = prixJour;
        this.image = image;
    }

    public static List<Voiture> searchAvailableCars(String agency, Date startDate, Date endDate, String Classe) throws SQLException {
        List<Voiture> availableCars = new ArrayList<>();
        String sql = "SELECT * FROM voiture " +
                "WHERE Localisation = (SELECT `ID Agence` FROM agence WHERE Nom = ?) " +
                "AND (Louée = 0 OR (`Date début de Location` > ? OR `Date fin de Location` < ?)) " +
                "AND Classe = ?";

        try (Connection conn = connecter();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, agency);
            pstmt.setDate(2, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(3, new java.sql.Date(endDate.getTime()));
            pstmt.setString(4,Classe);

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    availableCars.add(new Voiture(
                            rs.getInt("ID Voiture"),
                            rs.getString("Modele"),
                            rs.getString("Marque"),
                            rs.getString("Immatriculation"),
                            rs.getDouble("Kilometrage"),
                            rs.getString("Couleur"),
                            rs.getByte("Louée"),
                            rs.getInt("IDLoueur"),
                            rs.getInt("Localisation"),
                            rs.getDate("Date début de Location"),
                            rs.getDouble("Prix/Jour"),
                            rs.getString("Image"),
                            rs.getString("Classe")
                    ));
                }
            }
        }
        return availableCars;
    }

    private static Connection connecter() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost/bddprojetjavalocationvoiture";
        String username = "root";
        String password = "ruben2003"; // Modifier selon l'appareil
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    /*public ResultSet searchAvailableCars(String agency, Date startDate, Date endDate) throws SQLException {
        String sql = "SELECT Modele, Marque, Prix/Jour, Image FROM voiture " +
                "WHERE Localisation = (SELECT `ID Agence` FROM agence WHERE Nom = ?) AND " +
                "`Date début de Location` <= ? AND `Date fin de Location` >= ? AND `Louée` = 0";
        try (PreparedStatement pstmt = connecter().prepareStatement(sql)) {
            pstmt.setString(1, agency);
            pstmt.setDate(2, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(3, new java.sql.Date(endDate.getTime()));
            return pstmt.executeQuery();
        }
    }*/

    
    // Méthodes pour interagir avec la base de données
    public static void ajouterVoiture(Connection connection, String modele, String marque, String immatriculation, double kilometrage, String couleur, byte louee, int idLoueur, int localisation, Date dateLocation, double prixJour, String image) throws SQLException {
        String sql = "INSERT INTO voiture (Modele, Marque, Immatriculation, Kilometrage, Couleur, Louée, IDLoueur, Localisation, `Date de Location`, `Prix/Jour`, Image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, modele);
            statement.setString(2, marque);
            statement.setString(3, immatriculation);
            statement.setDouble(4, kilometrage);
            statement.setString(5, couleur);
            statement.setByte(6, louee);
            statement.setInt(7, idLoueur);
            statement.setInt(8, localisation);
            if (dateLocation != null) {
                statement.setDate(9, new java.sql.Date(dateLocation.getTime()));
            } else {
                statement.setNull(9, Types.DATE);
            }
            statement.setDouble(10, prixJour);
            statement.setString(11, image);
            statement.executeUpdate();
        }
    }

    public static void supprimerVoiture(Connection connection, int idVoiture) throws SQLException {
        String sql = "DELETE FROM voiture WHERE `ID Voiture` = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, idVoiture);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Voiture supprimée avec succès.");
            } else {
                System.out.println("Aucune voiture trouvée avec l'ID : " + idVoiture);
            }
        }
    }

    public String getImage() {
        return image;
    }

    public byte getLouee() {
        return louee;
    }

    public Date getDateLocation() {
        return dateLocation;
    }

    public double getKilometrage() {
        return kilometrage;
    }

    public double getPrixJour() {
        return prixJour;
    }

    public int getId() {
        return id;
    }

    public int getIdLoueur() {
        return idLoueur;
    }

    public int getLocalisation() {
        return localisation;
    }

    public String getCouleur() {
        return couleur;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public String getMarque() {
        return marque;
    }

    public String getModele() {
        return modele;
    }

    public String getClasse() {
        return classe;
    }
}
