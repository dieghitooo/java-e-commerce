import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.HashMap;

public class Client {
    public static void main(String[] args) {
        // Finestra principale del negozio (inizialmente non visibile)
        JFrame frame = new JFrame("Benvenuti nel nostro negozio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridBagLayout());

        JFrame frameCarrello = new JFrame("Ecco il tuo carrello");
        frameCarrello.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frameCarrello.setSize(800, 600);
        frameCarrello.setLayout(new GridBagLayout());
        frameCarrello.setVisible(false);

        // Magazzino prodotti (simulato)
        HashMap<String, Integer> magazzino = new HashMap<>();

        // Creazione delle immagini
        ImageIcon resizedMaglia = resizeImage("magliaJava.jpg", 500, 400);
        JLabel imageLabel = new JLabel(resizedMaglia);
        ImageIcon resizedPantalone = resizeImage("pantaloneJava.jpg", 500, 400);
        JLabel imageLabel1 = new JLabel(resizedPantalone);
        ImageIcon resizedScarpa = resizeImage("scarpaJava.jpg", 500, 400);
        JLabel imageLabel2 = new JLabel(resizedScarpa);
        ImageIcon resizedGiacca = resizeImage("giaccaJava.jpg", 500, 400);
        JLabel imageLabel3 = new JLabel(resizedGiacca);

        // Logo
        ImageIcon imageIcon4 = new ImageIcon("logoJava.jpg");
        Image logoImage = imageIcon4.getImage().getScaledInstance(1000, 300, Image.SCALE_SMOOTH);
        ImageIcon resizedLogo = new ImageIcon(logoImage);
        JLabel imageLabel4 = new JLabel(resizedLogo);

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        logoPanel.add(imageLabel4);
        mostraNegozio(frame, logoPanel, magazzino, imageLabel, imageLabel1, imageLabel2, imageLabel3);
        frame.setVisible(true);
    }

    private static void mostraNegozio(JFrame frame, JPanel logoPanel, HashMap<String, Integer> magazzino, JLabel... imageLabels) {
        GridBagConstraints gbc = new GridBagConstraints(); // Dichiarazione e inizializzazione di gbc
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.fill = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(logoPanel, gbc);

        gbc.gridwidth = 1;

        // Associa un prodotto a ogni immagine
        String[] prodotti = {"Maglietta", "Pantalone", "Scarpa", "Giacca"};
        String[] immagini = {"magliaJava.jpg", "pantaloneJava.jpg", "scarpaJava.jpg", "giaccaJava.jpg"};
        String[] prezzi = {"€19.99", "€29.99", "€39.99", "€49.99"};

        for (int i = 0; i < imageLabels.length; i++) {
            gbc.gridx = i % 2;
            gbc.gridy = 1 + i / 2;
            frame.add(imageLabels[i], gbc);

            // Aggiungi il listener con i metadati corretti
            imageLabels[i].addMouseListener(new ProductClickListener(
                    prodotti[i], immagini[i], "Descrizione di " + prodotti[i], prezzi[i], magazzino
            ));
        }

        frame.setVisible(true);
    }

    private static class ProductClickListener implements MouseListener {
        private final String prodotto;
        private final String immagine;
        private final String descrizione;
        private final String prezzo;
        private final HashMap<String, Integer> magazzino;

        public ProductClickListener(String prodotto, String immagine, String descrizione, String prezzo, HashMap<String, Integer> magazzino) {
            this.prodotto = prodotto;
            this.immagine = immagine;
            this.descrizione = descrizione;
            this.prezzo = prezzo;
            this.magazzino = magazzino;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // Mostra i dettagli del prodotto cliccato
            showProductDetails(prodotto, immagine, descrizione, prezzo, magazzino);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    private static void showProductDetails(String title, String imagePath, String description, String price,
                                           HashMap<String, Integer> magazzino) {
        JFrame newFrame = new JFrame(title);
        newFrame.setSize(400, 400);
        newFrame.setLayout(new BorderLayout());

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(2, 1));

        // Menu a tendina per la scelta della taglia
        String[] taglie = {"XS", "S", "M", "L", "XL"};
        JComboBox<String> comboBoxTaglie = new JComboBox<>(taglie);
        JPanel panelTaglia = new JPanel();
        panelTaglia.add(new JLabel("Taglia:"));
        panelTaglia.add(comboBoxTaglie);
        optionsPanel.add(panelTaglia);

        // Selezione del colore tramite pulsanti
        JPanel panelColori = new JPanel();
        panelColori.setLayout(new FlowLayout());
        panelColori.add(new JLabel("Colore:"));

        JRadioButton rossoButton = new JRadioButton("Rosso");
        JRadioButton bluButton = new JRadioButton("Blu");
        JRadioButton verdeButton = new JRadioButton("Verde");
        JRadioButton neroButton = new JRadioButton("Nero");
        ButtonGroup gruppoColori = new ButtonGroup();
        gruppoColori.add(rossoButton);
        gruppoColori.add(bluButton);
        gruppoColori.add(verdeButton);
        gruppoColori.add(neroButton);

        panelColori.add(rossoButton);
        panelColori.add(bluButton);
        panelColori.add(verdeButton);
        panelColori.add(neroButton);
        optionsPanel.add(panelColori);

        newFrame.add(optionsPanel, BorderLayout.NORTH);

        JLabel productImage = new JLabel(resizeImage(imagePath, 200, 200));
        newFrame.add(productImage, BorderLayout.CENTER);

        JPanel detailsPanel = new JPanel(new GridLayout(2, 1));
        detailsPanel.add(new JLabel(description));
        detailsPanel.add(new JLabel("Prezzo: " + price));
        newFrame.add(detailsPanel, BorderLayout.SOUTH);

        JButton buttonLogin = new JButton("vai al carrello");
        buttonLogin.addActionListener(e -> {
            String nomeProdotto = title;
            double prezzoProdotto = Double.parseDouble(price.replace("€", "").trim()); // Rimuovi il simbolo dell'euro e converti in double
            String tagliaProdotto = comboBoxTaglie.getSelectedItem().toString();
            String immagineProdotto= imagePath;
            Prodotto prodotto = new Prodotto(nomeProdotto, prezzoProdotto, tagliaProdotto, immagineProdotto);
            FrameCarrello.aggiungiProdotto(prodotto);
            JOptionPane.showMessageDialog(newFrame, "Prodotto aggiunto al carrello!");
            FrameCarrello.mostraCarrello();

        });

        JButton buttonCarrello = new JButton("aggiungi al carrello");
        buttonCarrello.addActionListener(e ->{
            String nomeProdotto = title;
            double prezzoProdotto = Double.parseDouble(price.replace("€", "").trim()); // Rimuovi il simbolo dell'euro e converti in double
            String tagliaProdotto = comboBoxTaglie.getSelectedItem().toString();
            String immagineProdotto= imagePath;
            Prodotto prodotto = new Prodotto(nomeProdotto, prezzoProdotto, tagliaProdotto, immagineProdotto);
            FrameCarrello.aggiungiProdotto(prodotto);
            JOptionPane.showMessageDialog(newFrame, "Prodotto aggiunto al carrello!");
        });
        JButton chiudiButton = new JButton("chiudi");
        chiudiButton.addActionListener(e -> newFrame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(buttonLogin);
        buttonPanel.add(chiudiButton);
        buttonPanel.add(buttonCarrello);

        newFrame.add(buttonPanel, BorderLayout.SOUTH);
        newFrame.setVisible(true);
    }

    private static ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image resizedImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
