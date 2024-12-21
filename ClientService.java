import java.io.*;
import java.net.Socket;

public class ClientService {
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    // Costruttore
    public ClientService(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    // Connessione al server
    public boolean connect() {
        try {
            // Crea il socket per connettersi al server
            socket = new Socket(serverAddress, serverPort);
            // Imposta un timeout sul socket per evitare blocchi
            socket.setSoTimeout(30000); // Timeout di 30 secondi
            // Flussi di input e output per comunicare
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            return true; // Connessione riuscita
        } catch (IOException e) {
            System.err.println("Errore durante la connessione al server: " + e.getMessage());
            return false; // Connessione fallita
        }
    }

    // Invio di un messaggio al server
    public void sendMessage(String message) {
        if (output != null) {
            output.println(message);
        } else {
            System.err.println("Errore: connessione non disponibile.");
        }
    }

    // Ricezione di un messaggio dal server
    public String receiveMessage() {
        try {
            if (input != null) {
                return input.readLine(); // Legge una riga dal server
            } else {
                System.err.println("Errore: connessione non disponibile.");
                return null;
            }
        } catch (java.net.SocketTimeoutException e) {
            System.err.println("Timeout: nessuna risposta ricevuta dal server.");
            return null;
        } catch (IOException e) {
            System.err.println("Errore durante la ricezione del messaggio: " + e.getMessage());
            return null;
        }
    }

    // Chiusura della connessione
    public void close() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (input != null) {
                input.close();
            }
            if (output != null) {
                output.close();
            }
        } catch (IOException e) {
            System.err.println("Errore durante la chiusura della connessione: " + e.getMessage());
        }
    }
}
