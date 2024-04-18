package vue;

import controleur.ControleurPaiement;
import modele.Loueur;
import modele.ReservationModel;
import modele.Voiture;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class AvantPaiement extends JFrame {

    private ControleurPaiement controleurPaiement; // Ajoutez une référence à ControleurPaiement

    private ReservationModel model;
    private JRadioButton basicInsuranceButton;
    private JRadioButton maxInsuranceButton;
    private JTextArea detailsTextArea;
    private JButton confirmButton;

    private JLabel modelLabel;
    private JLabel priceLabel;
    private JLabel startDateLabel;
    private JLabel endDateLabel;
    private Loueur LoueurConnectee;
    private Date startDate, endDate;
    private Voiture voiture;

    private static final double PRIX_ASSURANCE_BASE = 38.43; // Exemple de prix journalier
    private static final double PRIX_ASSURANCE_MAXIMALE = 53.26; // Exemple de prix journalier

    public AvantPaiement(ReservationModel model, ControleurPaiement controleurPaiement, Loueur loueurConnectee, Date Start, Date End,Voiture voiture) {
        this.model = model;
        this.controleurPaiement = controleurPaiement; // Initialisez la référence à ControleurPaiement
        this.LoueurConnectee = loueurConnectee;
        this.startDate = Start;
        this.endDate = End;
        this.voiture = voiture;

        long Days = getDaysBetweenDates(startDate, endDate);
        Days++;

        int reduc=0;

        if(LoueurConnectee.getType()==3)
        {
            reduc = 10;
        }

        setTitle("Réservation de voiture");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        getContentPane().setBackground(new Color(240, 240, 240)); // Couleur de fond

        JPanel panel = new JPanel(new GridLayout(1, 2, 20, 20)); // Utilisation d'une grille pour deux panneaux
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(Color.WHITE); // Couleur du panneau

        JPanel leftPanel = createOptionPanel("Assurance de base", "- Franchise : jusqu'à la valeur du véhicule\n" +
                "- Assurance dommages pour les dommages par collision, les rayures, les chocs et le vol\n" +
                "- Protection pneus et vitres\n" +
                "- Protection Intérieure\n" +
                "- Service de mobilité\n" +
                "- Inclus", "38.43 €", "\"C:\\Users\\timot\\OneDrive\\Bureau\\Paiement\\basic.jpg\"");
        panel.add(leftPanel);

        JPanel rightPanel = createOptionPanel("Assurance maximale", "- Aucune franchise\n" +
                "- Assurance dommages complète\n" +
                "- Protection pneus et vitres\n" +
                "- Service de mobilité\n" +
                "- Assistance routière 24/7\n" +
                "- Remplacement de véhicule\n" +
                "- Frais d'annulation flexibles", "53.26 €", "max.png");
        panel.add(rightPanel);

        add(panel, BorderLayout.CENTER);

        JPanel reservationPanel = new JPanel();
        reservationPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        reservationPanel.setBackground(Color.WHITE);

        JLabel recapLabel = new JLabel("Récapitulatif de la réservation");
        reservationPanel.add(recapLabel);

        double prixtotal = Days*voiture.getPrixJour();

        modelLabel = new JLabel("Modèle de voiture : "+voiture.getModele());
        priceLabel = new JLabel("Prix : "+prixtotal+"€ | Réduction : "+reduc+"%");
        startDateLabel = new JLabel("Date de début de location : "+startDate);
        endDateLabel = new JLabel("Date de fin de location : "+endDate);

        LoueurConnectee.getType();

        reservationPanel.add(modelLabel);
        reservationPanel.add(priceLabel);
        reservationPanel.add(startDateLabel);
        reservationPanel.add(endDateLabel);

        ButtonGroup insuranceGroup = new ButtonGroup();

        basicInsuranceButton = new JRadioButton("Assurance de base");
        maxInsuranceButton = new JRadioButton("Assurance maximale");

        insuranceGroup.add(basicInsuranceButton);
        insuranceGroup.add(maxInsuranceButton);

        reservationPanel.add(basicInsuranceButton);
        reservationPanel.add(maxInsuranceButton);

        confirmButton = new JButton("Confirmer la réservation");
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Actions à effectuer lors de la confirmation de la réservation
                long days = getDaysBetweenDates(startDate, endDate);
                double prixAssurance = basicInsuranceButton.isSelected() ? PRIX_ASSURANCE_BASE : PRIX_ASSURANCE_MAXIMALE;
                double totalCost = (days * (voiture.getPrixJour() + prixAssurance));
                if(LoueurConnectee.getType() == 3)
                {
                    totalCost = totalCost*0.10;
                }

                System.out.println("Prix total : "+totalCost);
                dispose();
                controleurPaiement.lancerPaiement(LoueurConnectee,voiture,startDate,endDate,prixtotal); // Appel de la méthode pour lancer le contrôleur de paiement
                //VuePaiement vuep = new VuePaiement(LoueurConnectee,voiture,prixtotal,startDate,endDate);
                //vuep.setVisible(true);
                // Par exemple, enregistrer les détails de la réservation dans la base de données
            }
        });
        reservationPanel.add(confirmButton);
        add(reservationPanel, BorderLayout.SOUTH);
    }


    public void setControleurPaiement(ControleurPaiement controleurPaiement) {
        this.controleurPaiement = controleurPaiement;
    }

    private JPanel createOptionPanel(String title, String description, String price, String iconFilename) {
        JPanel optionPanel = new JPanel(new BorderLayout());
        optionPanel.setBorder(BorderFactory.createTitledBorder(title)); // Titre du panneau
        optionPanel.setBackground(Color.WHITE); // Couleur du panneau

        ImageIcon icon = new ImageIcon(iconFilename);
        JLabel iconLabel = new JLabel(icon);
        optionPanel.add(iconLabel, BorderLayout.WEST);

        JTextArea descriptionArea = new JTextArea(description);
        descriptionArea.setEditable(false);
        descriptionArea.setLineWrap(true); // Permet le retour à la ligne automatique
        descriptionArea.setWrapStyleWord(true); // Coupe les mots entiers
        descriptionArea.setBackground(Color.WHITE); // Couleur de la zone de texte
        optionPanel.add(descriptionArea, BorderLayout.CENTER);

        JPanel pricePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pricePanel.setBackground(Color.WHITE); // Couleur du panneau de prix

        JLabel priceLabel = new JLabel("Prix : " + price);
        pricePanel.add(priceLabel);
        optionPanel.add(pricePanel, BorderLayout.SOUTH);

        return optionPanel;
    }


    public void afficher() {
        setLocationRelativeTo(null); // Centrer la fenêtre sur l'écran
        setVisible(true);
    }

    private long getDaysBetweenDates(Date start, Date end) {
        long diff = end.getTime() - start.getTime();
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

}
