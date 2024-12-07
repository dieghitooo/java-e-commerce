import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class NegozioVestiti {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Benvenuti nel nostro negozio"); // creo la finestra
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // imposta la grandezza della finestra
        frame.setLayout(new GridBagLayout());
        // Magazzino prodotti
        /*
        creo un "magazzino" dove definisco un prodotto e una quantita , dove per primo punto metto una chiave univoca
        in modo tale che si riconosca subito il prodotto, succesivamente inserisco la quantita "presente nel magazzino"
        dove ovviamente quando copra qualcosa la quantità dimunuise
         */
        HashMap<String, Integer> magazzino = new HashMap<>();
        magazzino.put("Maglietta_Rosso", 5);
        magazzino.put("Maglietta_Blu", 3);
        magazzino.put("Maglietta_Verde", 4);
        magazzino.put("Maglietta_Nero", 2);
        magazzino.put("Pantalone_Rosso", 5);
        magazzino.put("Pantalone_Blu", 3);
        magazzino.put("Pantalone_Verde", 2);
        magazzino.put("Pantalone_Nero", 4);
        magazzino.put("Scarpa_Rosso", 2);
        magazzino.put("Scarpa_Blu", 3);
        magazzino.put("Scarpa_Verde", 1);
        magazzino.put("Scarpa_Nero", 4);
        magazzino.put("Giacca_Rosso", 1);
        magazzino.put("Giacca_Blu", 4);
        magazzino.put("Giacca_Verde", 3);
        magazzino.put("Giacca_Nero", 2);

        // Creazione delle immagini
        ImageIcon resizedMaglia = resizeImage("magliaJava.jpg", 500, 400); // inserisco l'immagine e gli do la dimensione
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

        GridBagConstraints gbc = new GridBagConstraints(); // do le posizioni all'immagini tramite delle colonne e righe di java e margine dei bordi
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.fill = GridBagConstraints.CENTER;

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        frame.add(logoPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        frame.add(imageLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        frame.add(imageLabel1, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(imageLabel2, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        frame.add(imageLabel3, gbc);

        // Listener per gestire il clic sulle immagini
        MouseListener listener = new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getSource() == imageLabel) {
                    /*
                    in questo caso il nome del prodotto deve essere uguale alla prima stringa del nome del prodotto inserito all'interno del "magazzino"
                    se no mi darà di default prodotto esaurito
                     */
                    showProductDetails("Maglietta", "magliaJava.jpg",
                            "Maglietta", "€19.99", magazzino);
                } else if (e.getSource() == imageLabel1) {
                    showProductDetails("Pantalone", "pantaloneJava.jpg",
                            "Jeans", "€29.99", magazzino);
                } else if (e.getSource() == imageLabel2) {
                    showProductDetails("Scarpa", "scarpaJava.jpg",
                            "Scarpe da ginnastica", "€49.99", magazzino);
                } else if (e.getSource() == imageLabel3) {
                    showProductDetails("Giacca", "giaccaJava.jpg",
                            "Giacca", "€79.99", magazzino);
                }
            }

            @Override public void mousePressed(MouseEvent e) {}
            @Override public void mouseReleased(MouseEvent e) {}
            @Override public void mouseEntered(MouseEvent e) {}
            @Override public void mouseExited(MouseEvent e) {}
        };

        imageLabel.addMouseListener(listener);
        imageLabel1.addMouseListener(listener);
        imageLabel2.addMouseListener(listener);
        imageLabel3.addMouseListener(listener);

        frame.setVisible(true); // Rendo visibile il tutto
    }

    private static void showProductDetails(String title, String imagePath, String description, String price, HashMap<String, Integer> magazzino) {
        JFrame newFrame = new JFrame(title);
        newFrame.setSize(400, 400);
        newFrame.setLayout(new BorderLayout());

        // Creazione del pannello per la scelta della taglia e del colore
        JPanel optionsPanel = new JPanel();
        optionsPanel.setLayout(new GridLayout(2, 1));

        // Menu a tendina per la scelta della taglia
        String[] taglie = {"XS","S", "M", "L", "XL"};
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
// creo il guppo di bottoni dei colori e gli aggiungo i bottoni in modo tale che so e riesco a stampare il messaggio in base a che colore ha preso il capo d'abbiagliamento l'utente
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

        JButton confermaButton = new JButton("Conferma");
        confermaButton.addActionListener(e -> {
            String tagliaSelezionata = (String) comboBoxTaglie.getSelectedItem();
            String coloreSelezionato = null;

            if (rossoButton.isSelected()) coloreSelezionato = "Rosso";
            else if (bluButton.isSelected()) coloreSelezionato = "Blu";
            else if (verdeButton.isSelected()) coloreSelezionato = "Verde";
            else if (neroButton.isSelected()) coloreSelezionato = "Nero";
/*
controllo il colore, taglia  e disponibiltà del prodotto scelto in modo che sappia io e l'utente se il prodotto è disponibile
 */
            if (coloreSelezionato != null) {
                String chiaveProdotto = title + "_" + coloreSelezionato;
                int disponibilita = magazzino.getOrDefault(chiaveProdotto, 0);
// genero il messaggio più adatto in base alla condizione che si verifica
                if (disponibilita > 0) {
                    magazzino.put(chiaveProdotto, disponibilita - 1);
                    JOptionPane.showMessageDialog(newFrame, "Acquisto completato! Taglia " + tagliaSelezionata +
                            ", Colore " + coloreSelezionato + ". Rimangono " + (disponibilita - 1) + " pezzi.");
                } else {
                    JOptionPane.showMessageDialog(newFrame, "Spiacente, il colore " + coloreSelezionato + " è esaurito.");
                }
            } else {
                JOptionPane.showMessageDialog(newFrame, "Per favore, seleziona un colore.");
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(confermaButton);
        newFrame.add(buttonPanel, BorderLayout.SOUTH);

        newFrame.setVisible(true); // Rendi visibile la finestra dei dettagli del prodotto
    }

    public static ImageIcon resizeImage(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image resizedImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}