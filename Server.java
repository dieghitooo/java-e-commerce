import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Server {
    // Magazzino prodotti
    private static final HashMap<String, Integer> magazzino = new HashMap<>();
    private static final AtomicInteger accessCount = new AtomicInteger(0); // Contatore accessi client
    private static final String logFilePath = "acquisti_log.txt"; // Percorso del file log

    public static void main(String[] args) {
        // Inizializza il magazzino
        inizializzaMagazzino();

        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server in ascolto sulla porta 12345");

            while (true) {
                try (Socket clientSocket = serverSocket.accept();
                     BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                     PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                    int clientAccessNumber = accessCount.incrementAndGet();
                    System.out.println("Client #" + clientAccessNumber + " connesso.");

                    // Leggi la richiesta del client
                    String richiesta = in.readLine();

                    if (richiesta.startsWith("ORDINE_")) {
                        gestisciOrdine(richiesta, clientAccessNumber, out);
                    } else {
                        out.println("Richiesta non valida.");
                    }

                } catch (IOException e) {
                    System.err.println("Errore con il client: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Errore di avvio del server: " + e.getMessage());
        }
    }

    private static void inizializzaMagazzino() {
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
    }

    private static void gestisciOrdine(String richiesta, int clientAccessNumber, PrintWriter out) {
        String[] ordine = richiesta.substring(7).split(";"); // Rimuovi "ORDINE_" e dividi i prodotti
        StringBuilder risposta = new StringBuilder("Risultato ordine:\n");
        boolean ordineCompletato = true;

        for (String prodottoDettagli : ordine) {
            String[] dettagli = prodottoDettagli.split(":");
            if (dettagli.length < 2) continue; // Ignora formati non validi

            String prodotto = dettagli[0];
            int quantitaRichiesta = Integer.parseInt(dettagli[1]);
            int disponibilita = magazzino.getOrDefault(prodotto, 0);

            if (disponibilita >= quantitaRichiesta) {
                magazzino.put(prodotto, disponibilita - quantitaRichiesta);
                risposta.append(String.format("- %s: Acquistati %d. Rimangono %d.\n", prodotto, quantitaRichiesta, disponibilita - quantitaRichiesta));
                logAcquisto(clientAccessNumber, prodotto, quantitaRichiesta, disponibilita - quantitaRichiesta);
            } else {
                risposta.append(String.format("- %s: Disponibili %d, impossibile completare l'acquisto.\n", prodotto, disponibilita));
                ordineCompletato = false;
            }
        }

        // Risposta finale al client
        if (ordineCompletato) {
            risposta.append("Ordine completato con successo!");
        } else {
            risposta.append("Ordine parzialmente completato: alcuni prodotti non erano disponibili.");
        }

        out.println(risposta.toString());
    }

    private static void logAcquisto(int clientAccessNumber, String prodotto, int quantita, int disponibilitaRimanente) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true))) {
            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            String logEntry = String.format("%s | Client #%d | Prodotto: %s | Quantità: %d | Disponibilità rimanente: %d%n",
                    timestamp, clientAccessNumber, prodotto, quantita, disponibilitaRimanente);
            writer.write(logEntry);
        } catch (IOException e) {
            System.err.println("Errore durante la scrittura del log: " + e.getMessage());
        }
    }
}
