package controleur;

import modele.InfosPaiement;
import modele.Loueur;
import modele.Voiture;
import vue.VuePaiement;

import java.util.Date;

public class ControleurPaiement {
    private VuePaiement vue;
    private InfosPaiement modele;

    public ControleurPaiement(VuePaiement vue, InfosPaiement modele) {
        this.vue = vue;
        this.modele = modele;
    }

    // Méthode pour traiter le paiement
    public void traiterPaiement() {
        // Récupérer les données de la vue
        String numeroCarte = vue.getNumeroCarte();
        String titulaireCarte = vue.getTitulaireCarte();
        String dateExpiration = vue.getDateExpiration();
        String cvv = vue.getCVV();


        modele.setNumeroCarte(numeroCarte);
        modele.setTitulaireCarte(titulaireCarte);
        modele.setDateExpiration(dateExpiration);
        modele.setCvv(Integer.parseInt(cvv));

    }

    public void lancerPaiement(Loueur Utilisateur, Voiture car, Date start, Date end, double prix) {
        VuePaiement vuePaiement = new VuePaiement(Utilisateur,car,prix,start,end);
        InfosPaiement infosPaiement = new InfosPaiement();
        vuePaiement.setControleur(this); // Définit le contrôleur pour la vue de paiement
        vuePaiement.setInfosPaiement(infosPaiement); // Définit le modèle pour la vue de paiement
        vuePaiement.afficher();

    }

}
