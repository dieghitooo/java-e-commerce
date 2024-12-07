import javax.swing.*;
import java.awt.*;

public class FrameLogin extends JFrame {
    public FrameLogin() {
        // Titolo della finestra
        super("Login");

        // Imposta la dimensione
        setSize(400, 300);

        // Imposta l'operazione di chiusura
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout per i componenti
        setLayout(new GridBagLayout());

        // Aggiungi componenti (esempio semplice)
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel labelUsername = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelUsername, gbc);

        JTextField textFieldUsername = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(textFieldUsername, gbc);

        JLabel labelPassword = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(labelPassword, gbc);

        JPasswordField passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        add(loginButton, gbc);
    }
}
