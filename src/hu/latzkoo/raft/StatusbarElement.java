package hu.latzkoo.raft;

import javax.swing.*;

public class StatusbarElement {

    private JLabel text;
    private JLabel value;

    public StatusbarElement(JLabel text, JLabel value) {
        this.setText(text);
        this.setValue(value);
    }

    public JLabel getText() {
        return text;
    }

    public void setText(JLabel text) {
        this.text = text;
    }

    public JLabel getValue() {
        return value;
    }

    public void setValue(JLabel value) {
        this.value = value;
    }

}
