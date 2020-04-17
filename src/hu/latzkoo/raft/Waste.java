package hu.latzkoo.raft;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Waste extends RawMaterial {

    public Waste() {
        try {
            this.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("waste.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
