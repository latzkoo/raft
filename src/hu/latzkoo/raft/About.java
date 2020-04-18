package hu.latzkoo.raft;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class About extends JDialog {

    public About() throws HeadlessException {
        this.setTitle("Raft névjegy");
        this.setModal(true);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(650,350);

        this.setTextField();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                super.windowClosed(e);
                if (Game.getStatus().equals("paused")) {
                    Game.continueGame();
                }
            }
        });
    }

    private void setTextField() {
        JLabel textField = new JLabel();
        textField.setBorder(new EmptyBorder(10, 10, 10, 10));

        textField.setText("<html><h2>Raft " + Main.VERSION + "</h2>" +
                "<strong>Készítette:</strong> Timári László<br />" +
                "<strong>Neptun azonosító:</strong> FA4ZPW<br />" +
                "<strong>A grafikai elemeket készítette:</strong> Buda Villő<br /><br />" +
                "<strong>Az alkalmazás célja:</strong> A programozás I. (IBL204L) tantárgy kötelező programjának megvalósítása<br />" +
                "<strong>Oktatók:</strong> Kicsi András, Dr. Jász Judit <br /><br />" +
                "<strong>Telefonszám:</strong> + 36 70 466 7132<br />" +
                "<strong>E-mail:</strong> Timari.Laszlo@stud.u-szeged.hu<br />" +
                "<strong>LinkedIn:</strong> https://www.linkedin.com/in/latzkoo<br />" +
                "<strong>GitHub:</strong> https://github.com/latzkoo/raft<br /><br />" +
                "Budapest, 2020. április 15.</html>");

        textField.setHorizontalAlignment(SwingConstants.LEFT);
        textField.setVerticalAlignment(SwingConstants.TOP);
        textField.setSize(new Dimension(650, 350));

        this.add(textField);
    }
}
