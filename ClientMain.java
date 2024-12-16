public class ClientMain {
    public static void main(String[] args) {
        // Crea il servizio client con IP del server e porta
        ClientService clientService = new ClientService("localhost", 12345);

        if (clientService.connect()) {
            System.out.println("Connesso al server!");

            // Invia messaggi al server
            clientService.sendMessage("Ciao Server!");
            System.out.println("Risposta dal server: " + clientService.receiveMessage());

            clientService.sendMessage("Come stai?");
            System.out.println("Risposta dal server: " + clientService.receiveMessage());

            // Termina la connessione
            clientService.sendMessage("exit");
            System.out.println("Risposta dal server: " + clientService.receiveMessage());

            clientService.close();
        } else {
            System.out.println("Impossibile connettersi al server.");
        }
    }
}
