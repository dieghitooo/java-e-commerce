/*public class client2 {
    JButton confermaButton = new JButton("vai al carrello");
        confermaButton.addActionListener(e -> {
        String tagliaSelezionata = (String) comboBoxTaglie.getSelectedItem();
        String coloreSelezionato = null;

        if (rossoButton.isSelected())
            coloreSelezionato = "Rosso";
        else if (bluButton.isSelected())
            coloreSelezionato = "Blu";
        else if (verdeButton.isSelected())
            coloreSelezionato = "Verde";
        else if (neroButton.isSelected())
            coloreSelezionato = "Nero";

        // Connessione al server
        if (coloreSelezionato != null) {
            try (Socket socket = new Socket("localhost", 12345); // Connessione al server sulla porta 12345
                 PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                 BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

// Inviare la richiesta al server
                String richiesta = title + "_" + coloreSelezionato + "_" + tagliaSelezionata;
                out.println(richiesta);

// Leggere la risposta del server
                String risposta = in.readLine();
                JOptionPane.showMessageDialog(newFrame, risposta); // Mostra la risposta del server

            } catch (IOException ex) {
                JOptionPane.showMessageDialog(newFrame, "Errore di connessione al server.");
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(newFrame, "Per favore, seleziona un colore.");
        }
    });
}

 */
