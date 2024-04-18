package modele;
public class InfosPaiement {
    private String numeroCarte;
    private String titulaireCarte;
    private String dateExpiration;
    private int cvv;

    // Constructeur
    public InfosPaiement() {}

    // Setters
    public void setNumeroCarte(String numeroCarte) {
        this.numeroCarte = numeroCarte;
    }

    public void setTitulaireCarte(String titulaireCarte) {
        this.titulaireCarte = titulaireCarte;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public void setCvv(int cvv) {
        this.cvv = cvv;
    }

    // Getters (ajoutez si nécessaire)
}
