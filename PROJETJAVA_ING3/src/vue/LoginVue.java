package vue;

import controleur.MainControlleur;
import modele.Loueur;
import modele.PageModelAccueil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.sql.*;

public class LoginVue extends JDialog {
    private JTextField Email1;
    private JPanel panel1;
    private JPasswordField MDP1;
    private JButton validationButton;
    private JButton inscriptionButton;
    public Loueur loueur;
    private MainControlleur controlleur;

    public LoginVue(JFrame parent, MainControlleur controlleur){
        super(parent);
        this.controlleur = controlleur;
        setTitle("Page Login");
        setContentPane(panel1);
        setMinimumSize(new Dimension(400,600));
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        validationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = Email1.getText();
                String motdepasse = String.valueOf(MDP1.getPassword());
                System.out.println("Email "+email+"|MdP "+motdepasse);
                boolean estValide = false;

                if(email.isEmpty() || motdepasse.isEmpty())
                {
                    JOptionPane.showMessageDialog(LoginVue.this, "Tous les champs doivent être remplis");
                }
                else
                {
                    try {
                        estValide = controlleur.VerificationLogin(email,motdepasse);

                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                    if(estValide)
                    {
                        dispose();
                        ///Lancer page d'après
                        Loueur Utilisateur = controlleur.getCurrentUser();
                        PageModelAccueil model = new PageModelAccueil();
                        PageAccueilView view = new PageAccueilView(model,Utilisateur);
                        view.setVisible(true); /// affichage nouvelle page
                        JOptionPane.showMessageDialog(LoginVue.this,"Compte trouvé, Connexion réussie");
                    }
                    else {
                        JOptionPane.showMessageDialog(LoginVue.this, "Coordonnées non reconnues", "Erreur de connexion", JOptionPane.ERROR_MESSAGE);
                        Email1.setText(""); // Effacer le champ email
                        MDP1.setText(""); // Effacer le champ mot de passe
                    }
                }

            }
        });
        inscriptionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                InscriptionVue inscriptionVue = new InscriptionVue(LoginVue.this, controlleur);
                inscriptionVue.setVisible(true);
            }
        });
    }

}
