package controleur;

import modele.Voiture;
import vue.PageAccueilView;
import vue.VueConsole;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.util.Date;

public class VoitureController {
    private Connection connection;
    private Voiture model;
    private PageAccueilView view;

    /*public VoitureController(Connection connection, PageAccueilView vue) {
        this.connection = connection;
        this.view = vue;
        initController();
    }*/

    /*private void initController() {
        view.getSearchButton().addActionListener(e -> handleSearch());
    }

    private void handleSearch() {
        int age = view.getAgeSpinnerValue();
        if (age < 18) {
            view.displayMessage("Conducteur trop jeune");
            return;
        }

        Date pickupDate = view.getPickupDateSpinnerValue();
        Date returnDate = view.getReturnDateSpinnerValue();
        String agency = view.getSelectedAgency();

        try {
            ResultSet rs = model.searchAvailableCars(agency,pickupDate, returnDate);
            if (!rs.next()) {
                view.displayMessage("Aucune voiture disponible");
            } else {
                do {
                    view.updateCarList(rs.getString("Marque"), rs.getString("Modele"), rs.getDouble("Prix/Jour"), rs.getString("Image"));
                } while (rs.next());
            }
        } catch (Exception e) {
            view.displayMessage("Erreur lors de la recherche des voitures: " + e.getMessage());
        }
    }*/

    /*public void ajouterVoiture() {
        try {
            String modele = vue.lireString("Entrez le modèle de la voiture :");
            String marque = vue.lireString("Entrez la marque de la voiture :");
            String immatriculation = vue.lireString("Entrez l'immatriculation de la voiture :");
            double kilometrage = vue.lireDouble("Entrez le kilométrage de la voiture :");
            String couleur = vue.lireString("Entrez la couleur de la voiture :");
            byte louee = vue.lireByte("La voiture est-elle louée ? (0: non, 1: oui) :");
            int idLoueur = vue.lireInt("Entrez l'ID du loueur :");
            int localisation = vue.lireInt("Entrez l'ID de l'agence de localisation :");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = vue.lireString("Entrez la date de location (yyyy-mm-dd) ou laissez vide si non applicable :");
            java.util.Date dateLocation = dateString.isEmpty() ? null : sdf.parse(dateString);
            double prixJour = vue.lireDouble("Entrez le prix par jour de la location :");
            String image = vue.lireString("Entrez l'URL de l'image de la voiture :");

            Voiture.ajouterVoiture(connection, modele, marque, immatriculation, kilometrage, couleur, louee, idLoueur, localisation, dateLocation, prixJour, image);
            vue.afficherMessage("Voiture ajoutée avec succès.");
        } catch (Exception e) {
            vue.afficherMessage("Erreur lors de l'ajout de la voiture : " + e.getMessage());
        }
    }

    public void supprimerVoiture() {
        int idVoiture = vue.lireInt("Entrez l'ID de la voiture à supprimer :");
        try {
            Voiture.supprimerVoiture(connection, idVoiture);
            vue.afficherMessage("Voiture supprimée avec succès.");
        } catch (Exception e) {
            vue.afficherMessage("Erreur lors de la suppression de la voiture : " + e.getMessage());
        }
    }*/
}
