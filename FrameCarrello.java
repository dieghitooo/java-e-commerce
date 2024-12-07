import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

class Prodotto {
    private String nome;
    private double prezzo;
    private String taglia;
    private String immagine;

    public Prodotto(String nome, double prezzo, String taglia, String immagine) {
        this.nome = nome;
        this.prezzo = prezzo;
        this.taglia = taglia;
        this.immagine = immagine;
    }

    @Override
    public String toString() {
        return nome + " - " + taglia + " - " + String.format("€%.2f", prezzo);
    }
}

public class FrameCarrello extends JFrame {
    private static ArrayList<Prodotto> carrello = new ArrayList<>();
    private static JFrame carrelloFrame;

    public FrameCarrello() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        // Codice aggiuntivo opzionale
    }

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
                carrelloPanel.add(new JLabel(prodotto.toString()));
            }
        }

        JButton chiudiButton = new JButton("Chiudi");
        chiudiButton.addActionListener(e -> carrelloFrame.dispose());
        carrelloPanel.add(chiudiButton);

        carrelloFrame.add(carrelloPanel);
        carrelloFrame.setVisible(true);
    }

    public static void aggiungiProdotto(Prodotto prodotto) {
        carrello.add(prodotto);
        System.out.println("Prodotto aggiunto al carrello: " + prodotto);
    }
}
