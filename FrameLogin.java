import javax.swing.*;
import java.awt.*;
import javax.swing.text.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FrameLogin extends JFrame {
    private final Runnable onLoginSuccess;
    private static String username;
    private static String numero;
    private static String email;
    public FrameLogin(Runnable onLoginSuccess) {
        super("Login");
        this.onLoginSuccess = onLoginSuccess;

        // Imposta la dimensione
        setSize(400, 400);

        // Centra la finestra
        setLocationRelativeTo(null);

        // Imposta l'operazione di chiusura
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Layout per i componenti
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        // Campo "Numero di carta"
        JLabel labelNumbercard = new JLabel("Numero di carta:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(labelNumbercard, gbc);

        JTextField textFieldNumberCard = new JTextField(20);
        ((AbstractDocument) textFieldNumberCard.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                if (newText.matches("\\d{0,12}")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                replace(fb, offset, 0, text, attr);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(textFieldNumberCard, gbc);

        // Campo "Scadenza carta" (mese e anno)
        JLabel labelScadenza = new JLabel("Seleziona il mese e l'anno:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        add(labelScadenza, gbc);

        // Creazione JComboBox per mese
        String[] mesi = { "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottobre", "Novembre", "Dicembre" };
        JComboBox<String> monthComboBox = new JComboBox<>(mesi);
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(monthComboBox, gbc);

        // Creazione JComboBox per anno (ad esempio, 2024-2030)
        Integer[] anni = { 2024, 2025, 2026, 2027, 2028, 2029, 2030, 2031, 2032, 2033, 2034, 2035, 2036, 2037, 2038, 2039, 2040, 2041, 2042, 2043, 2044, 2045, 2046, 2047, 2048, 2049, 2050};
        JComboBox<Integer> yearComboBox = new JComboBox<>(anni);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(yearComboBox, gbc);
        // Campo "CVC"
        JLabel labelCVC = new JLabel("CVC:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        add(labelCVC, gbc);

        JPasswordField textFieldCVC = new JPasswordField(20);
        ((AbstractDocument) textFieldCVC.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                if (newText.matches("\\d{0,3}")) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                replace(fb, offset, 0, text, attr);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(textFieldCVC, gbc);

        // Campo "Indirizzo pacco"
        JLabel labelVia = new JLabel("Indirizzo pacco:");
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(labelVia, gbc);

        JTextField textFieldVia = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 4;
        add(textFieldVia, gbc);

        // Campo "Numero telefonico"
        JLabel labelNumero = new JLabel("Numero telefonico:");
        gbc.gridx = 0;
        gbc.gridy = 5;
        add(labelNumero, gbc);

        JTextField textFieldNumero = new JTextField(20);
        ((AbstractDocument) textFieldNumero.getDocument()).setDocumentFilter(new DocumentFilter() {
            @Override
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                String currentText = fb.getDocument().getText(0, fb.getDocument().getLength());
                String newText = currentText.substring(0, offset) + text + currentText.substring(offset + length);
                if (newText.length() <= 10) {
                    super.replace(fb, offset, length, text, attrs);
                }
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                replace(fb, offset, 0, text, attr);
            }
        });
        gbc.gridx = 1;
        gbc.gridy = 5;
        add(textFieldNumero, gbc);

        // Bottone "Login"
        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        add(loginButton, gbc);

        // Listener per il bottone "Login"
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validazione dei campi
                String numberCard = textFieldNumberCard.getText().trim();
                String CVC = new String(textFieldCVC.getPassword()).trim();
                String indirizzo = textFieldVia.getText().trim();
                String numero = textFieldNumero.getText().trim();
                String mese = (String) monthComboBox.getSelectedItem();
                int anno = (Integer) yearComboBox.getSelectedItem();

                if (numberCard.isEmpty()) {
                    JOptionPane.showMessageDialog(FrameLogin.this, "Il campo Numero di carta è obbligatorio.", "Errore di validazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (CVC.isEmpty()) {
                    JOptionPane.showMessageDialog(FrameLogin.this, "Il campo CVC è obbligatorio.", "Errore di validazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (indirizzo.isEmpty()) {
                    JOptionPane.showMessageDialog(FrameLogin.this, "Il campo Indirizzo pacco è obbligatorio.", "Errore di validazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (numero.isEmpty()) {
                    JOptionPane.showMessageDialog(FrameLogin.this, "Il campo Numero telefonico è obbligatorio.", "Errore di validazione", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    FrameCarrello.scontrino(username,email,indirizzo,numero);
                    System.out.println("Scontrino generaro");
                }catch (Exception ex){
                    JOptionPane.showMessageDialog(FrameLogin.this, "errore" + ex.getMessage(), "errore", JOptionPane.ERROR_MESSAGE);

                }
                dispose();
                if (onLoginSuccess != null) {
                    onLoginSuccess.run();
                }
            }
        });
    }
}