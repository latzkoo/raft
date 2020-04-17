package hu.latzkoo.raft;

import javax.imageio.ImageIO;
import java.io.IOException;

public class WaterPurifier extends Raft {

    private boolean fixed;
    private int status;
    private boolean isDone;

    public WaterPurifier(Dimension position) {
        this.setFixed(true);
        this.setStatus(0);
        this.setPosition(position);

        try {
            this.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("waterPurifier.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        if (status < 0) {
            status = 0;
        }
        else if (status > 25) {
            status = 25;
        }

        this.status = status;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public boolean isDone() {
        return isDone;
    }

    public void process() {
        this.setStatus(this.getStatus() + 1);

        if (this.getStatus() == 25) {
            this.setDone(true);
            this.setStatus(0);

            try {
                this.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("waterPurifierDone.png")));
            }
            catch (IOException e) {
                e.printStackTrace();
            }

            int row = this.getPosition().getRows();
            int col = this.getPosition().getCols();

            Game.getCanvas().getFields()[row][col] = this;
            Canvas.renderField(Game.getCanvas().getGraphics(), row, col);
        }
    }

    protected void reset() {
        this.setStatus(0);
        this.setDone(false);

        try {
            this.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream("waterPurifier.png")));
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        int row = this.getPosition().getRows();
        int col = this.getPosition().getCols();

        Canvas.renderField(Game.getCanvas().getGraphics(), row, col);
    }

}
