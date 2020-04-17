package hu.latzkoo.raft;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Net extends Field {

    private boolean fixed;

    public Net(Dimension position) {
        this.setPosition(position);
        this.setFixed(true);
        try {
            this.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("net.png")));
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
