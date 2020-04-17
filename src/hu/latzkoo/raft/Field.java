package hu.latzkoo.raft;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Field extends JPanel {

    private boolean fixed = false;
    private BufferedImage image;
    private Dimension position;

    public Field() {

    }

    public Field(Dimension position) {
        this.setPosition(position);
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public boolean isFixed() {
        return this.fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public Dimension getPosition() {
        return position;
    }

    public void setPosition(Dimension position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Field{" +
                "fixed=" + fixed +
                ", image=" + image +
                ", position=" + position +
                '}';
    }

}
