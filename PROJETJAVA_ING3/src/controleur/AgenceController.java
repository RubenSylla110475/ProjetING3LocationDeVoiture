package controleur;

import modele.Agence;
import vue.VueConsole;
import java.sql.Connection;

public class AgenceController {
    private VueConsole vue;
    private Connection connection;

    public AgenceController(Connection connection, VueConsole vue) {
        this.connection = connection;
        this.vue = vue;
    }

    public void ajouterAgence() {
        String nom = vue.lireString("Entrez le nom de l'agence :");
        String adresse = vue.lireString("Entrez l'adresse de l'agence :");
        String email = vue.lireString("Entrez l'email de l'agence :");
        String tel = vue.lireString("Entrez le téléphone de l'agence :");
        try {
            Agence.ajouterAgence(connection, nom, adresse, email, tel);
            vue.afficherMessage("Agence ajoutée avec succès.");
        } catch (Exception e) {
            vue.afficherMessage("Erreur lors de l'ajout de l'agence : " + e.getMessage());
        }
    }

    public void supprimerAgence() {
        int idAgence = vue.lireInt("Entrez l'ID de l'agence à supprimer :");
        try {
            Agence.supprimerAgence(connection, idAgence);
            vue.afficherMessage("Agence supprimée avec succès.");
        } catch (Exception e) {
            vue.afficherMessage("Erreur lors de la suppression de l'agence : " + e.getMessage());
        }
    }
}
