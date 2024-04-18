package vue;

import controleur.ControleurPaiement;
import controleur.MainControlleur;
import modele.*;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.*;
import java.util.List;


public class ResultsView extends JFrame {
    private List<Voiture> cars;
    private Loueur LoueurConnectee;
    private Date startDate, endDate;
    private String agency;
    private String Classe;
    public ResultsView(List<Voiture> cars, Loueur loueur1, Date startDate, Date endDate, String agency, String Classe) {

        this.LoueurConnectee = loueur1;
        this.cars = cars;
        this.startDate = startDate;
        this.endDate = endDate;
        this.agency = agency;
        this.Classe = Classe;

        setTitle("Résultats de la recherche de voitures");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon("/images/logo.png").getImage());

        setJMenuBar(createMenuBar());
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createCarsPanel(cars), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        String[] menuNames = {"Accueil", "Qui sommes nous ?", "Mes réservations", "Panier", "S'inscrire / Se connecter"};
        for (String name : menuNames) {
            JMenuItem menuItem = new JMenuItem(name);
            menuItem.addActionListener(e->navigationVers(name));
            menuBar.add(menuItem);
        }
        return menuBar;
    }

    private void navigationVers(String nomPage) {
        switch (nomPage) {
            case "Accueil":
                // Implémentez la navigation vers la page d'accueil
                dispose();
                PageModelAccueil model = new PageModelAccueil();
                PageAccueilView view = new PageAccueilView(model,LoueurConnectee);
                view.setVisible(true);
                break;
            case "Qui sommes nous ?":
                dispose();
                QuiSommesNousVue qui = new QuiSommesNousVue(LoueurConnectee);
                qui.setVisible(true);
                break;
            case "Mes réservations":
                // Implémentez la navigation vers la page de location
                dispose();
                ReservationsView reserv = new ReservationsView(LoueurConnectee,new MainControlleur());
                reserv.setVisible(true);
                break;
            case "Panier":
                // Implémentez la navigation vers la gestion des réservations
                break;
            case "S'inscrire / Se connecter":
                dispose();
                JFrame parentFrame = new JFrame();  // Potentially get the parent frame dynamically if needed
                LoginVue loginPage = new LoginVue(parentFrame, new MainControlleur());
                loginPage.setVisible(true);
                break;
        }
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(22, 56, 40)); // Couleur vert foncé
        JLabel headerLabel = new JLabel("Résultats de la recherche de voitures");
        headerLabel.setForeground(Color.WHITE); // Couleur de police blanche
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        return headerPanel;
    }

    private JScrollPane createCarsPanel(List<Voiture> cars) {
        JPanel carsPanel = new JPanel();
        carsPanel.setLayout(new BoxLayout(carsPanel, BoxLayout.Y_AXIS));
        carsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Marges

        for (Voiture car : cars) {

            JPanel carPanel = new JPanel(new BorderLayout());
            carPanel.setBorder(BorderFactory.createLineBorder(new Color(120, 144, 156), 2));;

            JPanel infoPanel = new JPanel();
            infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
            infoPanel.setBackground(new Color(224, 242, 241)); // Light teal background

            String carInfoHtml = String.format("<html>Modèle: %s<br>Marque: %s<br>Prix par jour: %.2f€<br>" +
                    "Immatriculation: %s<br>Kilométrage: %.0f km<br>Couleur: %s<br>" +
                    "Localisation: %d</html>",car.getModele(), car.getMarque(), car.getPrixJour(),
                    car.getImmatriculation(), car.getKilometrage(), car.getCouleur(),
                    car.getLocalisation());


            JLabel carLabel = new JLabel(carInfoHtml);
            carLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            infoPanel.add(carLabel);

            JPanel pricePanel = new JPanel();
            pricePanel.setLayout(new FlowLayout(FlowLayout.CENTER));
            pricePanel.setBackground(new Color(224, 242, 241));

            JLabel priceLabel = new JLabel(String.format("%.2f€ par jour", car.getPrixJour()));
            priceLabel.setFont(new Font("Arial", Font.BOLD, 16));
            JButton bookButton = new JButton("Réserver");
            bookButton.addActionListener(e -> {
                        if (LoueurConnectee == null) {
                            JOptionPane.showMessageDialog(this, "Vous devez être connecté pour réserver.", "Erreur de réservation", JOptionPane.ERROR_MESSAGE);
                            return;
                        }

                        Date debutLoc = startDate;
                        Date finLoc = endDate;

                        JOptionPane.showMessageDialog(this, "Réservation pour " + car.getModele());

                        AffichageResultat(car, debutLoc, finLoc, LoueurConnectee);

                        //ReservationModel modeleRes = new ReservationModel();
                        //VuePaiement vueP = new VuePaiement(LoueurConnectee,car,0,debutLoc,finLoc);
                        //ControleurPaiement control = new ControleurPaiement(vueP, new InfosPaiement());
                        dispose();
                        AvantPaiement avant = new AvantPaiement(new ReservationModel(), new ControleurPaiement(null,null), LoueurConnectee, startDate, endDate, car);
                        avant.setVisible(true);
                    }
            );

            pricePanel.add(priceLabel);
            pricePanel.add(bookButton);
            infoPanel.add(pricePanel);

            carPanel.add(infoPanel, BorderLayout.CENTER);

            if (car.getImage() != null && !car.getImage().isEmpty()) {
                URL imageUrl = getClass().getResource("/images/" + car.getImage());
                if (imageUrl != null) {
                    ImageIcon icon = new ImageIcon(new ImageIcon(imageUrl).getImage().getScaledInstance(400, 240, Image.SCALE_SMOOTH));
                    JLabel imageLabel = new JLabel(icon, JLabel.CENTER);
                    carPanel.add(imageLabel, BorderLayout.NORTH);
                } else {
                    System.out.println("Image file not found: " + car.getImage());
                }
            }

            carsPanel.add(carPanel);
        }

        JScrollPane scrollPane = new JScrollPane(carsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        return scrollPane;
    }

    private void AffichageResultat(Voiture car, Date startDate, Date endDate, Loueur user) {
        // Implement booking logic here, possibly interacting with the database
        System.out.println("Booking: " + car.getModele() + " by " + user.getEmail() + " | ID Client : " + user.getId());
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(22, 56, 40)); // Reprendre la couleur de l'en-tête
        JLabel footerLabel = new JLabel("© 2024 Package View - Location de Voiture. Tous droits réservés.");
        footerLabel.setForeground(Color.WHITE); // Couleur de police blanche
        footerPanel.add(footerLabel);
        return footerPanel;
    }

}
