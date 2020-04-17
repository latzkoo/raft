package hu.latzkoo.raft;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Dimension;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Help extends JDialog {

    public Help() throws HeadlessException {
        this.setTitle("Raft súgó");
        this.setModal(true);
        this.setLayout(new BorderLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(650,430);

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

        textField.setText("<html><h2>Billentyűparancsok</h2>" +
                "<strong>← → ↑ ↓ ⤡ ⤢ :</strong> mozgás (numerikus billentyűzeten átlósan is)<br /><br />" +
                "<strong>SPACE:</strong> cselekvés (nyersanyagok felvétele, elemek elhelyezése, főzés, evés, ivás, védekezés)<br /><br />" +
                "<strong>A:</strong> tutaj bővítése<br />" +
                "<strong>N:</strong> háló elhelyezése<br />" +
                "<strong>W:</strong> víztisztító elhelyezése<br />" +
                "<strong>S:</strong> tűzhely elhelyezése<br />" +
                "<strong>F:</strong> horgászat<br />" +
                "<strong>Q:</strong> lándzsa készítése<br /><br />" +
                "<h2>Hasznos infók</h2>" +
                "Minden víztisztító automatikusan termel vizet, melyet arra a mezőre lépve ihatjuk meg, ahol a víztisztító található. " +
                "A víztisztítási művelet végét a tisztító színének megváltozása jelzi.<br /><br />" +
                "Ahhoz, hogy enni tudjunk, a halat és a burgonyát meg kell főzni, rá kell helyezni azokat a tűzhelyre.<br />" +
                "A főzés folyamata alatt a tűzhelyen egy üst látható. Ha ennek a színe megváltozik, az ételünk elkészült, melyet arra " +
                "a mezőre lépve fogyaszthatunk el, ahol a tűzhely található.</html>");

        textField.setHorizontalAlignment(SwingConstants.LEFT);
        textField.setVerticalAlignment(SwingConstants.TOP);
        textField.setSize(new Dimension(650, 430));

        this.add(textField);
    }
}