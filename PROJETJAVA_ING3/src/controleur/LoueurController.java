package controleur;

import modele.Loueur;
import vue.VueConsole;
import java.sql.Connection;

public class LoueurController {
    private VueConsole vue;
    private Connection connection;

    public LoueurController(Connection connection, VueConsole vue) {
        this.connection = connection;
        this.vue = vue;
    }

    public void ajouterLoueur() {
        String nom = vue.lireString("Entrez le nom du loueur :");
        String prenom = vue.lireString("Entrez le prénom du loueur :");
        String email = vue.lireString("Entrez l'email du loueur :");
        String tel = vue.lireString("Entrez le téléphone du loueur :");
        byte type = vue.lireByte("Entrez le type du loueur (0 ou 1) :");
        String motDePasse = vue.lireString("Entrez le mot de passe du loueur :");

        try {
            Loueur.ajouterLoueur(connection, nom, prenom, email, tel, type, motDePasse);
            vue.afficherMessage("Loueur ajouté avec succès.");
        } catch (Exception e) {
            vue.afficherMessage("Erreur lors de l'ajout du loueur : " + e.getMessage());
        }
    }

    public void supprimerLoueur() {
        int idLoueur = vue.lireInt("Entrez l'ID du loueur à supprimer :");
        try {
            Loueur.supprimerLoueur(connection, idLoueur);
            vue.afficherMessage("Loueur supprimé avec succès.");
        } catch (Exception e) {
            vue.afficherMessage("Erreur lors de la suppression du loueur : " + e.getMessage());
        }
    }
}
