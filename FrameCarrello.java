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
    public String getNome() {
        return nome;
    }
    public double getPrezzo() {
        return prezzo;
    }
    public String getTaglia() {
        return taglia;
    }
    public String getImmagine() {
        return immagine;
    }
    @Override
    public String toString() {
        return nome + " - " + taglia + " - " + String.format("€%.2f", prezzo);
    }
}
public class FrameCarrello extends JFrame {
    private static ArrayList<Prodotto> carrello = new ArrayList<>();
    private static JFrame carrelloFrame;

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
        JButton acqusitaButton = new JButton("acquaista");
        acqusitaButton.addActionListener(e -> carrelloFrame.dispose());

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
}
