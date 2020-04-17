package hu.latzkoo.raft;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Plank extends RawMaterial {

    public Plank() {
        try {
            this.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("plank.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
