package hu.latzkoo.raft;

import javax.swing.*;
import java.awt.*;

public class Canvas extends JPanel {

    private Dimension dimension;
    private Field[][] fields;

    public Canvas(Dimension dimension) {
        super(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setPreferredSize(new java.awt.Dimension(Game.dimension.getRows() * Game.dimension.getCols(),
                Game.SIZE * Game.SIZE));
        setBackground(Colors.CANVAS);

        this.setDimension(dimension);
        this.fields = new Field[this.getDimension().getRows()][this.getDimension().getCols()];

        this.init();
    }

    public Field[][] getFields() {
        return fields;
    }

    public void setFields(Field[][] fields) {
        this.fields = fields;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    private void init() {
        for (int row = 0; row < this.getDimension().getRows(); row++) {
            for (int col = 0; col < this.getDimension().getCols(); col++) {
                this.fields[row] = this.createEmptyRow();
            }
        }

        this.initRaft();
        this.initShark();
    }

    /**
     * Tutaj inicializálása
     */
    private void initRaft() {
        int centerRow = this.getDimension().getRows() / 2;
        int centerCol = this.getDimension().getCols() / 2;

        Game.setUser(new User(new Dimension(centerRow, centerCol), new Raft(new Dimension(centerRow, centerCol))));

        this.fields[centerRow][centerCol] = Game.getUser();
        this.fields[centerRow + 1][centerCol] = new Raft(new Dimension(centerRow + 1, centerCol));
        this.fields[centerRow][centerCol + 1] = new Raft(new Dimension(centerRow, centerCol + 1));
        this.fields[centerRow + 1][centerCol + 1] = new Raft(new Dimension(centerRow + 1, centerCol + 1));
    }

    /**
     * Beállítja a cápa (támadáson kívüli) mozgási területét (közel tartja a tutajhoz)
     */
    public void updateSharkDimensions() {
        int lowestRow = 24;
        int highestRow = 0;
        int lowestCol = 34;
        int highestCol = 0;

        for (int row = 0; row < Game.dimension.getRows(); row++) {
            for (int col = 0; col < Game.dimension.getCols(); col++) {
                if (this.fields[row][col] instanceof Raft) {
                    if (row < lowestRow) {
                        lowestRow = row;
                    }
                    if (row > highestRow) {
                        highestRow = row;
                    }
                    if (col < lowestCol) {
                        lowestCol = col;
                    }
                    if (col > highestCol) {
                        highestCol = col;
                    }
                }
            }
        }

        Game.setSharkDimension(new Dimension[]{
            new Dimension(lowestRow - 3, lowestCol - 3),
            new Dimension(lowestRow + 3, lowestCol + 3)
        });
    }

    /**
     * Cápa inicializálása
     */
    private void initShark() {
        this.updateSharkDimensions();

        int row = this.getDimension().getRows() / 2 + 3;
        int col = this.getDimension().getCols() / 2 + Util.getRandomNumber(4) - 2;

        Game.setShark(new Shark(new Dimension(row, col), new Field(new Dimension(row, col))));

        this.fields[row][col] = Game.getShark();
    }

    /**
     * A sorokat egyel lejjebb tolja
     */
    private void shiftCols() {
        for (int row = 24; row >= 0; row--) {
            for (int col = 0; col < 34; col++) {
                if (row - 1 >= 0) {
                    Field current = fields[row][col];
                    Field prev = fields[row - 1][col];

                    if (current instanceof Net) {
                        if (!(prev instanceof User) && !(prev instanceof Shark)) {
                            Game.getUser().pickUp(this.fields[row - 1][col]);
                        }
                    }
                    else if (!current.isFixed() && !(current instanceof User) && !(current instanceof Shark)) {
                        if (prev instanceof User || prev instanceof Shark) {
                            if (!Game.getUser().getParentField().isFixed()) {
                                this.fields[row][col] = Game.getUser().getParentField();
                                this.fields[row][col].setPosition(new Dimension(row - 1, col));
                            }
                        }
                        else if (!prev.isFixed()) {
                            this.fields[row][col] = this.fields[row - 1][col];
                            this.fields[row][col].setPosition(new Dimension(row - 1, col));
                        }
                    }
                }
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.render(g, true);
    }

    /**
     * Nyersanyag folyam renderelése
     * @param g graphics object
     * @param gameStart játék indítás-e
     */
    public void render(Graphics g, Boolean gameStart) {
        for (int row = 0; row < this.getDimension().getRows(); row++) {
            for (int col = 0; col < this.getDimension().getCols(); col++) {
                if (gameStart || (!(this.fields[row][col] instanceof User) && !(this.fields[row][col] instanceof Raft) &&
                        !(this.fields[row][col] instanceof Shark))) {
                    renderField(g, row, col);
                }
            }
        }
    }

    /**
     * Egy új sort ad hozzá random nyersanyagokkal
     * @return row
     */
    private Field[] createNewRow() {
        int newStuffItems = Util.getRandomNumber(3);
        Field[] row = this.createEmptyRow();

        for (int i = 0; i < newStuffItems; i++) {
            int col = Util.getRandomNumber(34);
            row[col] = Game.generateField();
            row[col].setPosition(new Dimension(0, col));
        }

        return row;
    }

    /**
     * Egy új üres sort készít
     * @return row
     */
    private Field[] createEmptyRow() {
        int cols = this.getDimension().getCols();
        Field[] row = new Field[cols];

        for (int col = 0; col < cols; col++) {
            row[col] = new Field(new Dimension(0, col));
        }

        return row;
    }

    /**
     * Mező beállítása
     * @param mode normál vagy rejtett
     * @param row sor
     * @param col oszlop
     * @param targetField célmező
     */
    public static void setField(String mode, int row, int col, Field targetField) {
        Game.getCanvas().getFields()[row][col] = targetField;

        if (!mode.equals("hidden")) {
            Graphics g = Game.getCanvas().getGraphics();
            Canvas.renderField(g, row, col);
        }
    }


    /**
     * Újrarajzol egy mezőt.
     *
     * @param g   Graphics object
     * @param row sor
     * @param col oszlop
     */
    public static void renderField(Graphics g, int row, int col) {
        g.setColor(Colors.CANVAS);
        g.fillRect(col * Game.SIZE, row * Game.SIZE, Game.SIZE, Game.SIZE);

        g.drawImage(Game.getCanvas().getFields()[row][col].getImage(), col * Game.SIZE, row * Game.SIZE, Game.SIZE, Game.SIZE, null);
    }

    /**
     * A nyersanyagok folyamát vezérli
     */
    public void doFlow() {
        this.shiftCols();
        this.getFields()[0] = this.createNewRow();
        this.render(getGraphics(), false);
    }

}
