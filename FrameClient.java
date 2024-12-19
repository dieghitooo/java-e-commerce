import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FrameClient extends JFrame {
    private final Runnable onLoginSuccess;
    private ArrayList<JLabel> labels = new ArrayList<>();
    private JTextField textFieldUsername; // Campo per l'input dell'utente
    private JTextField textFieldEmail;   // Campo per l'input dell'email
    private JPasswordField passwordField; // Campo per l'input della password

    public FrameClient(Runnable onLoginSuccess) {
        super("Login");
        this.onLoginSuccess = onLoginSuccess;

        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Campo "Username"
        JLabel labelUsername = new JLabel("Username:");
        labels.add(labelUsername);
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelUsername, gbc);

        textFieldUsername = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(textFieldUsername, gbc);

        // Campo "Email"
        JLabel labelEmail = new JLabel("Email:");
        labels.add(labelEmail);
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(labelEmail, gbc);

        textFieldEmail = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(textFieldEmail, gbc);

        // Campo "Password"
        JLabel labelPassword = new JLabel("Password:");
        labels.add(labelPassword);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(labelPassword, gbc);

        passwordField = new JPasswordField(20);  // Utilizziamo JPasswordField per la password
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(passwordField, gbc);

        // Bottone "Login"
        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        // Azione del bottone "Login" con lambda
        loginButton.addActionListener(e -> {
            // Ottieni i dati dal form
            String username = textFieldUsername.getText().trim();
            String email = textFieldEmail.getText().trim();
            char[] password = passwordField.getPassword();

            // Validazione dei campi
            if (username.isEmpty()) {
                JOptionPane.showMessageDialog(
                        FrameClient.this,
                        "Il campo Username è obbligatorio.",
                        "Errore di validazione",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            if (email.isEmpty()) {
                JOptionPane.showMessageDialog(
                        FrameClient.this,
                        "Il campo Email è obbligatorio.",
                        "Errore di validazione",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            if (password.length == 0) {
                JOptionPane.showMessageDialog(
                        FrameClient.this,
                        "Il campo Password è obbligatorio.",
                        "Errore di validazione",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            // Prova a connettersi al server e inviare i dati
            try {
                // Crea un'istanza di ClientService
                ClientService clientService = new ClientService("localhost", 12345);

                if (clientService.connect()) {
                    // Prepara i dati da inviare
                    String dataToSend = "Username: " + username + "\nEmail: " + email + "\nPassword: " + new String(password);

                    // Invia i dati al server
                    clientService.sendMessage(dataToSend);

                    // Ricezione della risposta dal server
                    String response = clientService.receiveMessage();
                    System.out.println("Risposta dal server: " + response);

                    // Chiudi la connessione al server
                    clientService.close();

                    // Dopo il login riuscito, chiudi il frame e chiama il callback
                    dispose(); // Chiude il frame
                    if (onLoginSuccess != null) {
                        onLoginSuccess.run();
                    }
                } else {
                    JOptionPane.showMessageDialog(FrameClient.this, "Impossibile connettersi al server.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(FrameClient.this, "Errore durante la connessione: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    // Getter per ottenere il testo dei campi
    public String getUsername() {
        return textFieldUsername.getText().trim();
    }

    public String getEmail() {
        return textFieldEmail.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
