package hu.latzkoo.raft;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Barrel extends RawMaterial {

    public Barrel() {
        try {
            this.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("barrel.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
