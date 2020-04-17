package hu.latzkoo.raft;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Leaf extends RawMaterial {

    public Leaf() {
        try {
            this.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("leaf.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
