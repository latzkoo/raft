package hu.latzkoo.raft;

public class Dimension {

    private int rows;
    private int cols;

    public Dimension(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

}
