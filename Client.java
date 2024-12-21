import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class Client {
    public static void main(String[] args) {
        // Finestra principale del negozio (inizialmente non visibile)
        JFrame frame = new JFrame("Benvenuti nel nostro negozio");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new GridBagLayout());
        frame.setVisible(false);

        FrameClient frameClient = new FrameClient(() -> {
            // Azione da eseguire al successo del login
            frame.setVisible(true); // Mostra la finestra principale dopo il login
        });

        // Mostra la finestra di login
        frameClient.setVisible(true);



        // Magazzino prodotti (simulato)
        HashMap<String, HashMap<String, Integer>> magazzino = new HashMap<>();
        HashMap<String, Integer> magliettaColori = new HashMap<>();
        magliettaColori.put("Rosso", 10);
        magliettaColori.put("Blu", 5);
        magliettaColori.put("Verde", 8);
        magliettaColori.put("Nero", 12);

        HashMap<String, Integer> pantaloneColori = new HashMap<>();
        pantaloneColori.put("Rosso", 3);
        pantaloneColori.put("Blu", 7);
        pantaloneColori.put("Verde", 6);
        pantaloneColori.put("Nero", 9);

        HashMap<String, Integer> scarpeColori = new HashMap<>();
        scarpeColori.put("Rosso", 5);
        scarpeColori.put("Blu", 2);
        scarpeColori.put("Verde", 9);
        scarpeColori.put("Nero", 1);

        HashMap<String, Integer> giaccaColori = new HashMap<>();
        giaccaColori.put("Rosso", 2);
        giaccaColori.put("Blu", 7);
        giaccaColori.put("Verde", 1);
        giaccaColori.put("Nero", 4);

        magazzino.put("Maglietta", magliettaColori);
        magazzino.put("Pantalone", pantaloneColori);
        magazzino.put("Scarpe", scarpeColori);
        magazzino.put("Giacca", giaccaColori);

        // Creazione delle immagini
        ImageIcon resizedMaglia = resizeImage("immagini/magliaJava.jpg", 500, 400);
        JLabel imageLabel = new JLabel(resizedMaglia);
        ImageIcon resizedPantalone = resizeImage("immagini/pantaloneJava.jpg", 500, 400);
        JLabel imageLabel1 = new JLabel(resizedPantalone);
        ImageIcon resizedScarpa = resizeImage("immagini/scarpaJava.jpg", 500, 400);
        JLabel imageLabel2 = new JLabel(resizedScarpa);
        ImageIcon resizedGiacca = resizeImage("immagini/giaccaJava.jpg", 500, 400);
        JLabel imageLabel3 = new JLabel(resizedGiacca);

        // Logo
        ImageIcon imageIcon4 = new ImageIcon("immagini/logoJava.jpg");
        Image logoImage = imageIcon4.getImage().getScaledInstance(1000, 300, Image.SCALE_SMOOTH);
        ImageIcon resizedLogo = new ImageIcon(logoImage);
        JLabel imageLabel4 = new JLabel(resizedLogo);

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(Color.WHITE);
        logoPanel.add(imageLabel4);
        mostraNegozio(frame, logoPanel, magazzino, imageLabel, imageLabel1, imageLabel2, imageLabel3);

    }

    private static void mostraNegozio(JFrame frame, JPanel logoPanel, HashMap<String, HashMap<String, Integer>> magazzino, JLabel... imageLabels) {
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
        String[] immagini = {"immagini/magliaJava.jpg", "immagini/pantaloneJava.jpg", "immagini/scarpaJava.jpg", "immagini/giaccaJava.jpg"};
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

        frame.setVisible(false);
    }

    private static class ProductClickListener implements MouseListener {
        private final String prodotto;
        private final String immagine;
        private final String descrizione;
        private final String prezzo;
        private final HashMap<String, HashMap<String, Integer>> magazzino;

        public ProductClickListener(String prodotto, String immagine, String descrizione, String prezzo, HashMap<String, HashMap<String, Integer>> magazzino) {
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
                                           HashMap<String, HashMap<String, Integer>> magazzino) {
        JFrame newFrame = new JFrame(title);
        newFrame.setSize(400, 400);
        newFrame.setLayout(new BorderLayout());

        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(2, 1));

        // Menu a tendina per la scelta della taglia
        String[] taglie = {"","XS", "S", "M", "L", "XL"};
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

        JButton vaiCarrelloButton = new JButton("vai al carrello");
        vaiCarrelloButton.addActionListener(e -> {
            FrameCarrello.mostraCarrello();
        });

        JButton buttonCarrello = new JButton("aggiungi al carrello");
        buttonCarrello.addActionListener(e -> {
            String tagliaProdotto = comboBoxTaglie.getSelectedItem().toString();
            if (tagliaProdotto.isEmpty()) {
                JOptionPane.showMessageDialog(newFrame, "Seleziona una taglia prima di aggiungere al carrello.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String coloreProdotto = "";
            if (rossoButton.isSelected()) {
                coloreProdotto = "Rosso";
            } else if (bluButton.isSelected()) {
                coloreProdotto = "Blu";
            } else if (verdeButton.isSelected()) {
                coloreProdotto = "Verde";
            } else if (neroButton.isSelected()) {
                coloreProdotto = "Nero";
            } else {
                JOptionPane.showMessageDialog(newFrame, "Seleziona un colore prima di aggiungere al carrello.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Controlla la disponibilità per il colore selezionato
            HashMap<String, Integer> quantitaPerColore = magazzino.get(title);
            if (quantitaPerColore == null || quantitaPerColore.getOrDefault(coloreProdotto, 0) <= 0) {
                JOptionPane.showMessageDialog(newFrame, "Prodotto non disponibile nel colore selezionato!", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Aggiorna il magazzino
            int quantitaRimasta = quantitaPerColore.get(coloreProdotto) - 1;
            quantitaPerColore.put(coloreProdotto, quantitaRimasta);

            // Mostra messaggio di successo
            JOptionPane.showMessageDialog(newFrame, "Prodotto aggiunto al carrello! Quantità rimasta per " + coloreProdotto + ": " + quantitaRimasta);

            // Aggiungi prodotto al carrello
            Prodotto prodotto = new Prodotto(title, Double.parseDouble(price.replace("€", "").trim()), tagliaProdotto, coloreProdotto, imagePath);
            FrameCarrello.aggiungiProdotto(prodotto);
        });

        JButton chiudiButton = new JButton("chiudi");
        chiudiButton.addActionListener(e -> newFrame.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(vaiCarrelloButton);
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