package vue;

import controleur.ControleurPaiement;
import modele.InfosPaiement;
import modele.Loueur;
import modele.PageModelAccueil;
import modele.Voiture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class VuePaiement extends JFrame{

    private JFrame fenetre; // Référence à l'objet JFrame
    private ControleurPaiement controleur;
    private InfosPaiement infosPaiement;

    private JTextField champNumeroCarte;
    private JTextField champTitulaireCarte;
    private JTextField champDateExpiration;
    private JPasswordField champCVV;
    private JButton boutonPayer;
    private Connection connection;
    private Loueur Utilisateur;
    private Voiture resa;
    private double prix;
    private Date start,end;


    public VuePaiement(Loueur Utilisatur, Voiture car, double prix, Date start, Date end) {

        this.Utilisateur = Utilisatur;
        this.resa = car;
        this.prix = prix;
        this.start = start;
        this.end = end;

        JFrame fenetre = new JFrame("Paiement");
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());

        this.fenetre = fenetre;
        panel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        JLabel etiquetteNumeroCarte = new JLabel("Numéro de carte :");
        champNumeroCarte = new JTextField(20);

        JLabel etiquetteTitulaireCarte = new JLabel("Nom du titulaire :");
        champTitulaireCarte = new JTextField(20);

        JLabel etiquetteDateExpiration = new JLabel("Date d'expiration (MM/AA) :");
        champDateExpiration = new JTextField(7);

        JLabel etiquetteCVV = new JLabel("Code de sécurité (CVV) :");
        champCVV = new JPasswordField(4);

        // Chargement des icônes réduites
        JLabel iconVisa = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/images/visa.jpg")).getImage().getScaledInstance(50, 30, Image.SCALE_SMOOTH)));
        JLabel iconMastercard = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/images/mastercard.png")).getImage().getScaledInstance(50, 30, Image.SCALE_SMOOTH)));
        JLabel iconAmex = new JLabel(new ImageIcon(new ImageIcon(getClass().getResource("/images/amex.jpg")).getImage().getScaledInstance(50, 30, Image.SCALE_SMOOTH)));

        // Texte descriptif
        JLabel description = new JLabel("Veuillez saisir les informations de votre carte de crédit :");

        // Bouton Payer
        boutonPayer = new JButton("Payer");

        // Ajout des composants au panneau principal
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(description, gbc);
        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(etiquetteNumeroCarte, gbc);
        gbc.gridy++;
        panel.add(etiquetteTitulaireCarte, gbc);
        gbc.gridy++;
        panel.add(etiquetteDateExpiration, gbc);
        gbc.gridy++;
        panel.add(etiquetteCVV, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(champNumeroCarte, gbc);
        gbc.gridy++;
        panel.add(champTitulaireCarte, gbc);
        gbc.gridy++;
        panel.add(champDateExpiration, gbc);
        gbc.gridy++;
        panel.add(champCVV, gbc);


        JPanel panelIcones = new JPanel();
        panelIcones.add(iconVisa);
        panelIcones.add(iconMastercard);
        panelIcones.add(iconAmex);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(panelIcones, gbc);

        gbc.gridy++;
        panel.add(boutonPayer, gbc);

        fenetre.add(panel);
        fenetre.pack();

        //fenetre.setVisible(true);

        // Ajout d'un écouteur pour le bouton Payer
        boutonPayer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Appeler la méthode pour traiter le paiement dans le contrôleur
                try {
                    connection = connecter();
                    PreparedStatement pst = connection.prepareStatement("INSERT INTO reservations (VoitureID, LoueurID, AgenceID, DateDebut, DateFin, PrixPaye) VALUES (?, ?, ?, ?, ?, ?)");
                    pst.setInt(1, resa.getId()); // Supposons que vous avez une méthode getId() dans votre classe Voiture
                    pst.setInt(2, Utilisateur.getId()); // Supposons que vous avez une méthode getId() pour Loueur
                    pst.setInt(3, resa.getLocalisation()); // ID de l'agence, vous devez avoir cette valeur disponible
                    pst.setDate(4, new java.sql.Date(start.getTime()));
                    pst.setDate(5, new java.sql.Date(end.getTime()));
                    pst.setDouble(6, prix);

                    pst.executeUpdate();
                    JOptionPane.showMessageDialog(null,"Réservation effectuée avec succès");

                    PreparedStatement pstUpdateCar = connection.prepareStatement("UPDATE voiture SET Louée = ?, `Date début de Location` = ?, `Date fin de Location` = ?, `IDLoueur` = ? WHERE `ID Voiture` = ?");
                    pstUpdateCar.setInt(1,1);
                    pstUpdateCar.setInt(2,Utilisateur.getId());
                    pstUpdateCar.setDate(3,new java.sql.Date(start.getTime()));
                    pstUpdateCar.setDate(4, new java.sql.Date(end.getTime())); // Date de fin de location
                    pstUpdateCar.setInt(5, resa.getId()); // ID de la voiture
                    pstUpdateCar.executeUpdate();

                    fenetre.dispose();
                    PageModelAccueil model = new PageModelAccueil();
                    PageAccueilView view = new PageAccueilView(model,Utilisateur);
                    view.setVisible(true);

                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(null, "Erreur de base de données : " + ex.getMessage());
                } finally {
                    try {
                        if (connection != null) {connection.close();dispose();} // Fermer la connexion
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null, "Erreur lors de la fermeture de la connexion : " + ex.getMessage());
                    }
                }

                System.out.println("Paiement effectué !");

                dispose();

            }
        });
    }

    // Méthode pour configurer le contrôleur de paiement
    public void setControleur(ControleurPaiement controleur) {
        this.controleur = controleur;
    }

    public static Connection connecter() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost/bddprojetjavalocationvoiture";
        String username = "root";
        String password = "ruben2003"; // Modifier selon l'appareil
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    // Méthode pour définir les informations de paiement
    public void setInfosPaiement(InfosPaiement infosPaiement) {
        this.infosPaiement = infosPaiement;
    }
    // Méthode pour afficher la vue de paiement
    public void afficher() {
        fenetre.setVisible(true); // Utilisation de l'objet JFrame pour rendre visible la fenêtre
    }

    public String getNumeroCarte() {
        return champNumeroCarte.getText();
    }

    public String getTitulaireCarte() {
        return champTitulaireCarte.getText();
    }

    public String getDateExpiration() {
        return champDateExpiration.getText();
    }

    public String getCVV() {
        return new String(champCVV.getPassword());
    }

    // Méthode pour ajouter un écouteur au bouton Payer
    public void ajouterEcouteurPayer(ActionListener listener) {
        boutonPayer.addActionListener(listener);
    }

}
