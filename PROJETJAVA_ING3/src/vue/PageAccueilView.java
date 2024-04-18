package vue;

import controleur.MainControlleur;
import modele.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Date;

import java.util.*;
import java.util.List;

public class PageAccueilView extends JFrame {
    private Loueur Utilisateur;
    private PageModelAccueil model;
    private JButton searchButton;
    private JSpinner ageSpinner, pickupDateSpinner, returnDateSpinner;
    private JComboBox<String> locationComboBox;

    public int getDriverAge() {
        return (Integer) ageSpinner.getValue();
    }

    public Date getStartDate() {
        return ((SpinnerDateModel) pickupDateSpinner.getModel()).getDate();
    }

    public Date getEndDate() {
        return ((SpinnerDateModel) returnDateSpinner.getModel()).getDate();
    }

    public PageAccueilView(PageModelAccueil model, Loueur loueur1) {
        this.model = model;
        this.Utilisateur = loueur1;

        setTitle("QuoicoupagnanBAKA - Location de Voiture");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 600);
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        ImageIcon img = new ImageIcon("/images/logo.png");
        this.setIconImage(img.getImage());


        setJMenuBar(createMenuBar());
        addComponents();

    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        String[] menuNames = {"Accueil", "Qui sommes nous ?", "Mes réservations", "Panier", "S'inscrire / Se connecter"};
        for (String name : menuNames) {
            JMenuItem menuItem = new JMenuItem(name);
            menuBar.add(menuItem);
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    navigationVers(name);
                }
            });
        }
        return menuBar;
    }

    private void navigationVers(String nomPage)
    {
        switch (nomPage){
            case "Accueil":

                break;
            case "Qui sommes nous ?":
                dispose();
                QuiSommesNousVue qui = new QuiSommesNousVue(Utilisateur);
                qui.setVisible(true);
                break;
            case "Mes réservations":
                dispose();
                ReservationsView vueReservation = new ReservationsView(Utilisateur,new MainControlleur());
                vueReservation.setVisible(true);
                break;
            case "Panier":

                break;
            case "S'inscrire / Se connecter":
                dispose();
                LoginVue loginPage = new LoginVue(this, new MainControlleur());
                loginPage.setVisible(true);
                break;
        }
    }

    private void addComponents() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(22, 56, 40)); // Couleur vert foncé
        JLabel headerLabel = new JLabel("Quoicoupagnan - Location de Voiture");
        headerLabel.setForeground(Color.WHITE); // Couleur de police blanche
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);

        JPanel searchPanel = new JPanel(new GridBagLayout()); // Utilisation d'un GridBagLayout pour centrer les composants
        searchPanel.setBorder(BorderFactory.createEmptyBorder(40, 20, 40, 20)); // Ajouter des marges

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);


        JLabel locationLabel = new JLabel("Choisissez votre agence de départ:");
        searchPanel.add(locationLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> locationComboBox = new JComboBox<>(new String[]{"Paris", "Nice", "Marseille", "Lyon", "Lille", "Nantes"});
        searchPanel.add(locationComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel vehicleClassLabel = new JLabel("Choisissez la classe de véhicule:");
        searchPanel.add(vehicleClassLabel, gbc);

        gbc.gridx = 1;
        JComboBox<String> vehicleClassComboBox  = new JComboBox<>(new String[]{"Mini", "Compacte", "SUV", "Electrique", "Berline", "4X4"});
        searchPanel.add(vehicleClassComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel pickupDateLabel = new JLabel("Date de prise en charge:");
        searchPanel.add(pickupDateLabel, gbc);

        gbc.gridx = 1;
        JSpinner pickupDateSpinner = new JSpinner(new SpinnerDateModel());
        searchPanel.add(pickupDateSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel returnDateLabel = new JLabel("Date de retour:");
        searchPanel.add(returnDateLabel, gbc);

        gbc.gridx = 1;
        JSpinner returnDateSpinner = new JSpinner(new SpinnerDateModel());
        searchPanel.add(returnDateSpinner, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel ageLabel = new JLabel("Âge du conducteur:");
        searchPanel.add(ageLabel, gbc);

        gbc.gridx = 1;
        JSpinner ageSpinner = new JSpinner(new SpinnerNumberModel(25, 18, 99, 1));
        searchPanel.add(ageSpinner, gbc);

        gbc.gridy++;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JButton searchButton = new JButton("Rechercher");
        searchButton.setBackground(new Color(203, 182, 44)); // Couleur jaune
        searchButton.setForeground(Color.WHITE); // Couleur de police blanche
        searchButton.setFocusPainted(false); // Supprimer le contour de focus
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if((Integer) ageSpinner.getValue() <= 18){
                    JOptionPane.showMessageDialog(null,"Conducteur trop jeune");
                    return;
                }

                Date pickupDate = (Date) pickupDateSpinner.getValue();
                Date returnDate = (Date) returnDateSpinner.getValue();
                String Agence = (String) locationComboBox.getSelectedItem();
                String Classe = (String) vehicleClassComboBox.getSelectedItem();

                searchAvailableCars(pickupDate,returnDate,Agence, Classe);

            }
        });
        searchPanel.add(searchButton, gbc);

        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(246, 219, 239)); // Couleur rose pâle
        JLabel footerLabel = new JLabel("© 2024 Package View - Location de Voiture. Tous droits réservés.");
        footerLabel.setForeground(Color.WHITE); // Couleur de police blanche
        footerPanel.add(footerLabel);

        setLayout(new BorderLayout());
        add(headerPanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }

    public int getAgeSpinnerValue() {
        return (Integer) ageSpinner.getValue();
    }

    private void searchAvailableCars(Date pickup, Date returnDate, String agency, String Classe) {

        try{
            List<Voiture> cars = Voiture.searchAvailableCars(agency, pickup, returnDate,Classe);
            if (cars.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Aucune voiture disponible.");
            } else {
                dispose();
                ResultsView resultsView = new ResultsView(cars,Utilisateur,pickup,returnDate,agency,Classe);
                resultsView.setVisible(true);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la recherche de voitures: " + ex.getMessage());
            ex.printStackTrace();
        }

    }

    private static Connection connecter() throws SQLException {
        String jdbcUrl = "jdbc:mysql://localhost/bddprojetjavalocationvoiture";
        String username = "root";
        String password = "ruben2003"; // Modifier selon l'appareil
        return DriverManager.getConnection(jdbcUrl, username, password);
    }

    public Date getPickupDateSpinnerValue() {
        return (Date) pickupDateSpinner.getValue();
    }

    public Date getReturnDateSpinnerValue() {
        return (Date) returnDateSpinner.getValue();
    }

    public String getSelectedAgency() {
        return (String) locationComboBox.getSelectedItem();
    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public void updateCarList(String marque, String modele, double prixJour, String image) {
        // Logique pour ajouter des voitures à la liste affichée
    }

    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
