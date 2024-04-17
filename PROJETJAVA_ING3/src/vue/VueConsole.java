package vue;

import java.util.Scanner;

public class VueConsole {
    private Scanner scanner;

    public VueConsole() {
        this.scanner = new Scanner(System.in);
    }

    // Affiche le menu principal et retourne le choix de l'utilisateur
    public int afficherMenuPrincipal() {
        System.out.println("\nMenu Principal:");
        System.out.println("1. Ajouter une agence");
        System.out.println("2. Supprimer une agence");
        System.out.println("3. Ajouter un loueur");
        System.out.println("4. Supprimer un loueur");
        System.out.println("5. Ajouter une voiture");
        System.out.println("6. Supprimer une voiture");
        System.out.println("7. Quitter");
        System.out.print("Veuillez choisir une option: ");
        return scanner.nextInt();
    }

    // Méthodes pour lire les entrées pour chaque action spécifique
    public String lireString(String message) {
        System.out.println(message);
        scanner.nextLine(); // Nettoie le buffer si nécessaire
        return scanner.nextLine();
    }

    public int lireInt(String message) {
        System.out.println(message);
        return scanner.nextInt();
    }

    public double lireDouble(String message) {
        System.out.println(message);
        return scanner.nextDouble();
    }

    public byte lireByte(String message) {
        System.out.println(message);
        return scanner.nextByte();
    }

    // Méthode pour afficher des messages génériques à l'utilisateur
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    public String[] DemandeIdentifiant(){
        System.out.println("Email : ");
        String email = scanner.nextLine();

        System.out.println("Mot de Passe : ");
        String mdp = scanner.nextLine();

        return new String[] {email,mdp};
    }

    public void AffichageMessage(String mess)
    {
        System.out.println(mess);
    }
}
