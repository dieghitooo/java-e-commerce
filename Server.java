import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        int port = 12345; // Porta su cui il server sarà in ascolto

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Server in ascolto sulla porta " + port);

            while (true) {
                try {
                    // Accetta una connessione da un client
                    Socket clientSocket = serverSocket.accept();
                    System.out.println("Connessione accettata da " + clientSocket.getInetAddress());

                    // Gestisce la comunicazione con il client in un nuovo thread
                    new Thread(() -> handleClient(clientSocket)).start();
                } catch (IOException e) {
                    System.err.println("Errore nell'accettazione della connessione: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.err.println("Errore del server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void handleClient(Socket clientSocket) {
        try (
                // Flussi di input e output per comunicare con il client
                BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                PrintWriter output = new PrintWriter(clientSocket.getOutputStream(), true)
        ) {
            // Imposta un timeout sul socket per evitare connessioni pendenti
            clientSocket.setSoTimeout(30000); // Timeout di 30 secondi
            String message;
            System.out.println("In attesa di messaggi dal client...");

            // Riceve messaggi dal client
            while ((message = input.readLine()) != null) {
                System.out.println("Messaggio ricevuto: " + message);

                // Risponde al client
                output.println("Server ricevuto: " + message);

                // Se il client invia "exit", chiude la connessione
                if (message.equalsIgnoreCase("exit")) {
                    System.out.println("Chiusura della connessione con il client.");
                    break;
                }
            }
        } catch (java.net.SocketTimeoutException e) {
            System.err.println("Timeout: nessun messaggio ricevuto dal client entro il tempo limite.");
        } catch (IOException e) {
            System.err.println("Errore durante la comunicazione con il client: " + e.getMessage());
        } finally {
            try {
                clientSocket.close();
                System.out.println("Connessione con il client chiusa.");
            } catch (IOException e) {
                System.err.println("Errore durante la chiusura del socket: " + e.getMessage());
            }
        }
    }
}
