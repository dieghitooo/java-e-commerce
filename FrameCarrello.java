import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

class Carrello {
    private java.util.List<Prodotto> prodotti = new ArrayList<>();
    private double totale = 0;

    public void aggiungiProdotto(Prodotto prodotto) {
        prodotti.add(prodotto);
        totale += prodotto.getPrezzo();
    }

    public void rimuoviProdotto(Prodotto prodotto) {
        prodotti.remove(prodotto);
        totale -= prodotto.getPrezzo();
    }

    public double getTotale() {
        return totale;
    }

    public List<Prodotto> getProdotti() {
        return new ArrayList<>(prodotti); // Restituisce una copia per evitare modifiche esterne
    }

    public void svuotaCarrello() {
        prodotti.clear();
        totale = 0;
    }
}
class Prodotto {
    private String nome;
    private double prezzo;
    private String taglia;
    private String immagine;
    private String colore;

    public Prodotto(String nome, double prezzo, String taglia, String colore, String immagine) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.taglia = taglia;
        this.colore = colore;
        this.immagine = immagine;
    }

    public String getNome() {
        return nome;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public String getTaglia() {
        return taglia;
    }

    public String getImmagine() {return immagine;}

    public String getColore() {return colore;}

    @Override
    public String toString() {
        return nome + " - " + taglia + " - " + String.format("€%.2f", prezzo);
    }
}

public class FrameCarrello extends JFrame {
    private static ArrayList<Prodotto> carrello = new ArrayList<>();
    private static JFrame carrelloFrame;
    private static String username;
    private static String email;
    private static String indirizzo;
    private static String numero;

    public static void mostraCarrello() {
        if (carrelloFrame != null && carrelloFrame.isVisible()) {
            carrelloFrame.toFront(); // Porta il carrello in primo piano
            return;
        }

        carrelloFrame = new JFrame("Carrello");
        carrelloFrame.setSize(400, 300);
        carrelloFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel carrelloPanel = new JPanel();
        carrelloPanel.setLayout(new BoxLayout(carrelloPanel, BoxLayout.Y_AXIS));

        // Aggiungi gli articoli del carrello
        if (carrello.isEmpty()) {
            carrelloPanel.add(new JLabel("Il carrello è vuoto"));
        } else {
            for (Prodotto prodotto : carrello) {
                carrelloPanel.add(creaElementoCarrello(prodotto));
            }
        }

        JButton chiudiButton = new JButton("Chiudi");
        chiudiButton.addActionListener(e -> carrelloFrame.dispose());
        carrelloPanel.add(chiudiButton);

        JButton acqusitaButton = new JButton("Acquista");
        if (carrello.isEmpty()) {
            acqusitaButton.setVisible(false);
        } else {
            acqusitaButton.addActionListener(e -> {
                // Crea il frame login e specifica cosa fare dopo il successo del login
                FrameLogin frameLogin = new FrameLogin(() -> {
                    // Invio dei dati al server dopo il login
                    try {
                        // Crea un'istanza di ClientService
                        ClientService clientService = new ClientService("localhost", 12345);

                        if (clientService.connect()) {
                            System.out.println("Connesso al server!");

                            // Prepara i dati del carrello come stringa
                            StringBuilder datiCarrello = new StringBuilder();
                            for (Prodotto prodotto : carrello) {
                                datiCarrello.append(prodotto.getNome())
                                        .append(", ")
                                        .append(prodotto.getPrezzo())
                                        .append(", ")
                                        .append(prodotto.getTaglia())
                                        .append(", ")
                                        .append(prodotto.getColore())
                                        .append("\n");
                            }
                            double totale = 0;//-------------------------------
                            for (Prodotto prodotto : carrello) {
                                totale += prodotto.getPrezzo();
                            }
                            // Creazione del timer per aggiornare lo stato dell'ordine
                            Timer timer = new Timer();
                            TimerTask task = new TimerTask() {
                                private int count = 0;

                                @Override
                                public void run() {
                                    switch (count) {
                                        case 0:
                                            System.out.println("Ordine in preparazione");
                                            break;
                                        case 1:
                                            System.out.println("Ordine in consegna");
                                            break;
                                        case 2:
                                            System.out.println("Ordine cosegnato");
                                            timer.cancel(); // Ferma il timer dopo il messaggio "consegnato"
                                            break;
                                    }
                                    count++;
                                }
                            };
                            timer.schedule(task, 0, 5000); // Esegui il task ogni 5 secondi a partire da subito

                            // Invia i dati al server
                            clientService.sendMessage("Dati Carrello:\n" + datiCarrello.toString());
                            clientService.sendMessage( "\nTotale: €" + totale);//------------------
                            // Ricezione della risposta dal server
                            String response = clientService.receiveMessage();
                            System.out.println("Risposta dal server: " + response);

                            clientService.close();
                            scontrino(username, email, indirizzo, numero);
                            // Mostra un messaggio di conferma
                            JOptionPane.showMessageDialog(carrelloFrame, "Acquisto completato!");
                            carrello.clear(); // Svuota il carrello dopo l'acquisto
                            carrelloFrame.dispose(); // Chiudi il carrello
                        } else {
                            JOptionPane.showMessageDialog(carrelloFrame, "Impossibile connettersi al server.", "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(carrelloFrame, "Errore durante l'invio dei dati: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                });
                frameLogin.setVisible(true);
            });
        }
        carrelloPanel.add(acqusitaButton);

        carrelloFrame.add(new JScrollPane(carrelloPanel));
        carrelloFrame.setVisible(true);
    }

    private static JPanel creaElementoCarrello(Prodotto prodotto) {
        JPanel prodottoPanel = new JPanel();
        prodottoPanel.setLayout(new BorderLayout());
        prodottoPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        // Aggiungi l'immagine del prodotto
        String imagePath = prodotto.getImmagine();
        ImageIcon productImage = new ImageIcon(imagePath);
        JLabel imageLabel = new JLabel();
        imageLabel.setIcon(new ImageIcon(productImage.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));

        // Aggiungi i dettagli del prodotto
        JLabel detailsLabel = new JLabel(prodotto.toString());
        detailsLabel.setVerticalAlignment(SwingConstants.CENTER);

        prodottoPanel.add(imageLabel, BorderLayout.WEST);
        prodottoPanel.add(detailsLabel, BorderLayout.CENTER);

        return prodottoPanel;
    }

    public static void aggiungiProdotto(Prodotto prodotto) {
        carrello.add(prodotto);
        System.out.println("Prodotto aggiunto al carrello: " + prodotto);
    }
    public static void scontrino(String username, String email, String indirizzo, String numero) {
        try (FileWriter writer = new FileWriter("carrello.txt", true)) { // Modalità append
            // Scrive i dati dell'utente

            // Scrive i dati del carrello
            for (Prodotto prodotto : carrello) {
                writer.write(prodotto.getNome() + ", " +
                        prodotto.getPrezzo() + ", " +
                        prodotto.getTaglia() + ", " +
                        prodotto.getColore() + "\n");
                writer.write(" Utente: " + username + ", Email: " + email + " indirizzo: " + indirizzo + " numero di telefono: " + numero + "\n");

            }
            System.out.println("Dati salvati su file carrello.txt.");
        } catch (IOException e) {
            System.out.println("Errore durante la scrittura del file.");
            e.printStackTrace();
        }

    }
}


