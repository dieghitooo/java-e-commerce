
//SE FAIDELLE MODIFICHE AGGIUNGI I COMMENTI GRAZIE :)
//NON RIESCO A METTERE IL LOGO AL CENTRO IN ALTO E LA GIACCA NON SI RIMPICCIOLISCE (MAGARI è UN PROBLEMA DELL'IMMAGINE)
import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.GridLayout;

public class Prova {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Clicca sull'immagine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setLayout(new GridLayout(2, 2));// AGGIUNTO GRIGLIA PER METTERLI IN ORDINE
        ImageIcon imageIcon = new ImageIcon("magliaJava.jpg");
        JLabel imageLabel = new JLabel(imageIcon);
        ImageIcon imageIcon1 = new ImageIcon("pantaloneJava.jpg");
        JLabel imageLabel1 = new JLabel(imageIcon1);
        ImageIcon imageIcon2 = new ImageIcon("giaccaJava.jpg");
        JLabel imageLabel2 = new JLabel(imageIcon2);
        ImageIcon imageIcon3 = new ImageIcon("scarpaJava.jpg");
        JLabel imageLabel3 = new JLabel(imageIcon3);
        imageLabel.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame newFrame = new JFrame("Nuova finestra");
                newFrame.setSize(300, 200);
                newFrame.setVisible(true);
            }

            // Implement other MouseListener methods if needed (e.g., mousePressed,
            // mouseReleased, mouseEntered, mouseExited)
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        frame.add(imageLabel);
        frame.pack();
        frame.setVisible(true);

        imageLabel1.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame newFrame = new JFrame("Nuova finestra");
                newFrame.setSize(300, 200);
                newFrame.setVisible(true);
            }

            // Implement other MouseListener methods if needed (e.g., mousePressed,
            // mouseReleased, mouseEntered, mouseExited)
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        frame.add(imageLabel1);
        frame.pack();
        frame.setVisible(true);

        imageLabel2.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame newFrame = new JFrame("Nuova finestra");
                newFrame.setSize(300, 200);
                newFrame.setVisible(true);
            }

            // Implement other MouseListener methods if needed (e.g., mousePressed,
            // mouseReleased, mouseEntered, mouseExited)
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        frame.add(imageLabel2);
        frame.pack();
        frame.setVisible(true);

        imageLabel3.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JFrame newFrame = new JFrame("Nuova finestra");
                newFrame.setSize(300, 200);
                newFrame.setVisible(true);
            }

            // Implement other MouseListener methods if needed (e.g., mousePressed,
            // mouseReleased, mouseEntered, mouseExited)
            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        frame.add(imageLabel3);
        frame.pack();
        frame.setVisible(true);
    }
}