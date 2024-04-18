package vue;

import modele.Loueur;
import controleur.MainControlleur;
import modele.PageModelAccueil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ReservationsView extends JFrame{
    private Loueur Utilisateur;
    private MainControlleur controlleur;

    public ReservationsView(Loueur utilisateur, MainControlleur control)
    {
        this.Utilisateur = utilisateur;
        this.controlleur = control;

        setTitle("Mes Réservations - Location de Voiture");
        setSize(1100, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setIconImage(new ImageIcon("/images/logo.png").getImage());

        setJMenuBar(createMenuBar());
        add(createHeaderPanel(), BorderLayout.NORTH);
        add(createReservationsTable(), BorderLayout.CENTER);
        add(createFooterPanel(), BorderLayout.SOUTH);

    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        String[] menuNames = {"Accueil", "Qui sommes nous ?", "Mes réservations", "Panier", "S'inscrire / Se connecter"};
        for (String name : menuNames) {
            JMenuItem menuItem = new JMenuItem(name);
            menuItem.addActionListener(e -> navigationVers(name));
            menuBar.add(menuItem);
        }
        return menuBar;
    }

    private void navigationVers(String nomPage) {
        switch (nomPage) {
            case "Accueil":
                // Implémentez la navigation vers la page d'accueil
                dispose();
                PageModelAccueil model = new PageModelAccueil();
                PageAccueilView view = new PageAccueilView(model,Utilisateur);
                view.setVisible(true);
                break;
            case "Qui sommes nous ?":
                dispose();
                QuiSommesNousVue qui = new QuiSommesNousVue(Utilisateur);
                qui.setVisible(true);
                break;
            case "Mes réservations":
                // Peut-être rafraîchir la page ou confirmer que l'utilisateur est déjà là
                dispose();
                ReservationsView reserv = new ReservationsView(Utilisateur,controlleur);
                reserv.setVisible(true);
                break;
            case "Panier":
                // Implémentez la navigation vers le panier d'achats
                break;
            case "S'inscrire / Se connecter":
                dispose();
                JFrame parentFrame = new JFrame();  // Potentially get the parent frame dynamically if needed
                LoginVue loginPage = new LoginVue(parentFrame, new MainControlleur());
                loginPage.setVisible(true);
                break;
        }
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(22, 56, 40));
        JLabel headerLabel = new JLabel("Mes Réservations");
        headerLabel.setForeground(Color.WHITE);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
        headerPanel.add(headerLabel);
        return headerPanel;
    }

    private JScrollPane createReservationsTable() {
        Vector<String> columnNames = new Vector<>();
        columnNames.add("ID Réservation");
        columnNames.add("Modèle");
        columnNames.add("Marque");
        columnNames.add("Date de début");
        columnNames.add("Date de fin");
        columnNames.add("Prix payé");

        Vector<Vector<Object>> data = fetchReservations();
        JTable table = new JTable(data, columnNames);
        return new JScrollPane(table);
    }

    private Vector<Vector<Object>> fetchReservations() {
        Vector<Vector<Object>> data = new Vector<>();
        String sql = "SELECT r.ID, v.Modele, v.Marque, r.DateDebut, r.DateFin, r.PrixPaye " +
                "FROM reservations r JOIN voiture v ON r.VoitureID = v.`ID Voiture` " +
                "WHERE r.LoueurID = ?";
        try (Connection conn = controlleur.connecter();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Utilisateur.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Vector<Object> row = new Vector<>();
                row.add(rs.getInt("ID"));
                row.add(rs.getString("Modele"));
                row.add(rs.getString("Marque"));
                row.add(rs.getDate("DateDebut"));
                row.add(rs.getDate("DateFin"));
                row.add(rs.getDouble("PrixPaye"));
                data.add(row);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la récupération des données : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        return data;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel();
        footerPanel.setBackground(new Color(22, 56, 40));
        JLabel footerLabel = new JLabel("© 2024 Package View - Location de Voiture. Tous droits réservés.");
        footerLabel.setForeground(Color.WHITE);
        footerPanel.add(footerLabel);
        return footerPanel;
    }


}
