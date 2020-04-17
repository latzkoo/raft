package hu.latzkoo.raft;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Raft extends Field {

    private boolean fixed;

    public Raft() {
        this.setFixed(true);
        try {
            this.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("raft.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Raft(Dimension position) {
        this.setPosition(position);
        this.setFixed(true);
        try {
            this.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("raft.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

}
