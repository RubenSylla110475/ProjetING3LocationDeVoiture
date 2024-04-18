package vue;

import controleur.MainControlleur;
import modele.Loueur;
import modele.PageModelAccueil;

import javax.swing.*;
import javax.swing.text.Utilities;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuiSommesNousVue extends JFrame{

    private Loueur Utilisateur;

    public JLabel labelInformation;

    private JMenuBar createMenuBar() {

        JMenuBar menuBar = new JMenuBar();
        String[] menuNames = {"Accueil", "Qui sommes nous ?", "Mes réservations", "Panier", "S'inscrire / Se connecter"};
        for (String name : menuNames) {
            JMenuItem menuItem = new JMenuItem(name);
            menuBar.add(menuItem);
            menuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    navigationVers(name);
                }
            });
        }
        return menuBar;
    }

    private void navigationVers(String nomPage)
    {
        switch (nomPage){
            case "Accueil":
                dispose();
                PageModelAccueil model = new PageModelAccueil();
                PageAccueilView view = new PageAccueilView(model,Utilisateur);
                view.setVisible(true);
                break;
            case "Qui sommes nous ?":

                break;
            case "Mes réservations":
                dispose();
                ReservationsView vueReservation = new ReservationsView(Utilisateur,new MainControlleur());
                vueReservation.setVisible(true);
                break;
            case "Panier":

                break;
            case "S'inscrire / Se connecter":
                dispose();
                LoginVue loginPage = new LoginVue(this, new MainControlleur());
                loginPage.setVisible(true);
                break;
        }
    }

    public QuiSommesNousVue(Loueur Utilisateur) {

        this.Utilisateur = Utilisateur;
        setTitle("Qui Sommes Nous ?");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setJMenuBar(createMenuBar());

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        ImageIcon imageIcon = new ImageIcon("TIGROU LOCATION + fond.png");
        Image image = imageIcon.getImage();
        Image newImage = image.getScaledInstance(250, 200, Image.SCALE_SMOOTH);
        ImageIcon resizedImageIcon = new ImageIcon(newImage);
        JLabel labelImage = new JLabel(resizedImageIcon);
        labelImage.setHorizontalAlignment(JLabel.CENTER);
        panel.add(labelImage, BorderLayout.NORTH);

        labelInformation = new JLabel();
        labelInformation.setText("<html><div style='text-align: center;'>TIGROU LOCATION est une agence de location de voiture disponible dans les plus grandes villes françaises. Créée par 4 passionnés d'automobiles, il était pour eux indispensable de rendre la location de voitures plus simple et plus accessible pour tous. TIGROU LOCATION, c'est un moyen simple, rapide et efficace de rendre ses déplacements meilleurs.</div></html>");
        labelInformation.setForeground(new Color(246, 219, 239));
        panel.add(labelInformation, BorderLayout.CENTER);
        panel.setBackground(new Color(22, 57, 40));
        getContentPane().add(panel);

        JPanel photosPanel = new JPanel();
        photosPanel.setBackground(new Color(203, 182, 44));
        photosPanel.setLayout(new GridLayout(1, 4));

        for (int i = 1; i <= 4; i++) {
            ImageIcon photoIcon = new ImageIcon("image" + i + ".jpg");
            Image photoImage = photoIcon.getImage();
            Image resizedPhotoImage = photoImage.getScaledInstance(115, 150, Image.SCALE_SMOOTH);
            ImageIcon resizedPhotoIcon = new ImageIcon(resizedPhotoImage);
            JLabel photoLabel = new JLabel(resizedPhotoIcon);
            photoLabel.setHorizontalAlignment(JLabel.CENTER);
            photosPanel.add(photoLabel);

            JLabel descriptionLabel = new JLabel();
            descriptionLabel.setHorizontalAlignment(JLabel.CENTER);

            switch (i) {
                case 1:
                    descriptionLabel.setText("<html>SYLLA<br/>Ruben</html>");
                    break;
                case 2:
                    descriptionLabel.setText("<html>RUHOMUTALLY<br/>Lucas</html>");
                    break;
                case 3:
                    descriptionLabel.setText("<html>DO OLIVAL<br/>Timothé</html>");
                    break;
                case 4:
                    descriptionLabel.setText("<html>CORDONNIER<br/>Iris</html>");
                    break;
            }
            photosPanel.add(descriptionLabel);
        }

        panel.add(photosPanel, BorderLayout.SOUTH);
        panel.setBackground(new Color(22, 57, 40));
        getContentPane().add(panel);
    }

}
