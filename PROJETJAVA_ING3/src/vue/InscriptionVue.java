package vue;

import controleur.MainControlleur;
import modele.Loueur;
import modele.PageModelAccueil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InscriptionVue extends JDialog{


    private JTextField NomField;
    private JPanel panel1;
    private JTextField PrenomField;
    private JTextField EmailField;
    private JTextField TelField;
    private JTextField MDPField;
    private JButton ValidationInscription;
    public Loueur loueur;
    private MainControlleur controlleur;

    public InscriptionVue(LoginVue parent, MainControlleur controlleur)
    {
        super(parent);
        this.controlleur = controlleur;
        setTitle("Page Login");
        setContentPane(panel1);
        setMinimumSize(new Dimension(400,600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        ValidationInscription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nom = NomField.getText();
                String prenom = EmailField.getText();
                String email = MDPField.getText();
                String tel = TelField.getText();
                String mdp = PrenomField.getText();

                if(nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || tel.isEmpty() || mdp.isEmpty()){
                    JOptionPane.showMessageDialog(InscriptionVue.this, "Tous les champs doivent être remplis");
                }else
                {
                    try {
                        controlleur.ajouterLoueur(nom,prenom,email,tel,mdp);
                        JOptionPane.showMessageDialog(InscriptionVue.this, "Inscription réussie !");
                        dispose(); // Ferme la fenêtre d'inscription
                        Loueur Utilisateur = controlleur.getCurrentUser();
                        PageModelAccueil model = new PageModelAccueil();
                        PageAccueilView view = new PageAccueilView(model,Utilisateur);
                        view.setVisible(true); /// affichage nouvelle page

                        JOptionPane.showMessageDialog(InscriptionVue.this,"Compte trouvé, Connexion réussie");
                    }
                    catch (Exception ex){
                        JOptionPane.showMessageDialog(InscriptionVue.this, "Erreur d'inscription : "+ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
    }
}
