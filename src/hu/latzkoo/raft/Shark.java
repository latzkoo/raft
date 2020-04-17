package hu.latzkoo.raft;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Shark extends Field implements Movable {

    private boolean fixed = true;
    private boolean attacking = false;
    private Field parentField;

    public Shark(Dimension position, Field parentField) {
        this.setPosition(position);
        this.setParentField(parentField);

        try {
            String image;
            image = "shark.png";

            this.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream(image)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isAttacking() {
        return attacking;
    }

    public void setAttacking(boolean attacking) {
        this.attacking = attacking;
    }

    public boolean isFixed() {
        return fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }

    public Field getParentField() {
        return parentField;
    }

    public void setParentField(Field parentField) {
        this.parentField = parentField;
    }

    public void doMove(int row, int col) {
        Field targetField = Game.getCanvas().getFields()[row][col];

        if (this.isAcceptedField(targetField)) {
            if (targetField instanceof User && Game.getUser().isCanBeAttacked()) {
                Game.getUser().kill();
            }
            else {
                String mode;
                if (targetField instanceof Raft || targetField instanceof User) {
                    mode = "hidden";
                }
                else {
                    mode = "normal";
                }

                if (targetField instanceof User) {
                    targetField = Game.getUser().getParentField();
                }

                Canvas.setField(mode, row, col, new Shark(new Dimension(row, col), targetField));
                Canvas.setField("normal", this.getPosition().getRows(), this.getPosition().getCols(), this.getParentField());

                this.setPosition(new Dimension(row, col));
                this.setParentField(targetField);
            }
        }
    }

    @Override
    public boolean isAcceptedField(Field field) {
        return true;
    }

    @Override
    public void moveLeft() {
        if (this.canMoveLeft()) {
            doMove(this.getPosition().getRows(), this.getPosition().getCols() - 1);
        }
    }

    public boolean canMoveLeft() {
        return Game.getUser().isCanBeAttacked() || this.getPosition().getCols() - 1 >= Game.getSharkDimension()[0].getCols();
    }

    @Override
    public void moveRight() {
        if (this.canMoveRight()) {
            doMove(this.getPosition().getRows(), this.getPosition().getCols() + 1);
        }
    }

    public boolean canMoveRight() {
        return Game.getUser().isCanBeAttacked() || this.getPosition().getCols() + 1 <= Game.getSharkDimension()[1].getCols();
    }

    @Override
    public void moveUp() {
        if (this.canMoveUp()) {
            doMove(this.getPosition().getRows() - 1, this.getPosition().getCols());
        }
    }

    public boolean canMoveUp() {
        return Game.getUser().isCanBeAttacked() || this.getPosition().getRows() - 1 >= Game.getSharkDimension()[0].getRows();
    }

    @Override
    public void moveDown() {
        if (this.canMoveDown()) {
            doMove(this.getPosition().getRows() + 1, this.getPosition().getCols());
        }
    }

    public boolean canMoveDown() {
        return Game.getUser().isCanBeAttacked() || this.getPosition().getRows() + 1 <= Game.getSharkDimension()[1].getRows();
    }

    @Override
    public void moveUpLeft() {
        if (this.canMoveUpLeft()) {
            doMove(this.getPosition().getRows() - 1, this.getPosition().getCols() - 1);
        }
    }

    public boolean canMoveUpLeft() {
        return (Game.getUser().isCanBeAttacked() || this.getPosition().getCols() - 1 >= Game.getSharkDimension()[0].getCols() &&
                this.getPosition().getRows() - 1 >= Game.getSharkDimension()[0].getRows());
    }

    @Override
    public void moveUpRight() {
        if (this.canMoveUpRight()) {
            doMove(this.getPosition().getRows() - 1, this.getPosition().getCols() + 1);
        }
    }

    public boolean canMoveUpRight() {
        return (Game.getUser().isCanBeAttacked() || (this.getPosition().getCols() + 1 <= Game.getSharkDimension()[1].getCols() &&
                this.getPosition().getRows() - 1 >= Game.getSharkDimension()[0].getRows()));
    }

    @Override
    public void moveDownLeft() {
        if (this.canMoveDownLeft()) {
            doMove(this.getPosition().getRows() + 1, this.getPosition().getCols() - 1);
        }
    }

    public boolean canMoveDownLeft() {
        return (Game.getUser().isCanBeAttacked() || (this.getPosition().getCols() - 1 >= Game.getSharkDimension()[0].getCols() &&
                this.getPosition().getRows() + 1 <= Game.getSharkDimension()[1].getRows()));
    }

    @Override
    public void moveDownRight() {
        if (this.canMoveDownRight()) {
            doMove(this.getPosition().getRows() + 1, this.getPosition().getCols() + 1);
        }
    }

    public boolean canMoveDownRight() {
        return (Game.getUser().isCanBeAttacked() || (this.getPosition().getCols() + 1 <= Game.getSharkDimension()[1].getCols() &&
                this.getPosition().getRows() + 1 <= Game.getSharkDimension()[1].getRows()));
    }

    public void move() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String direction = this.getDirection();

        if (direction != null) {
            Method method = this.getClass().getMethod(direction);
            method.invoke(this);
        }
    }

    public String getDirection() {
        if (Game.getUser().isCanBeAttacked()) {
            int userRow = Game.getUser().getPosition().getRows();
            int userCol = Game.getUser().getPosition().getCols();
            int sharkRow = this.getPosition().getRows();
            int sharkCol = this.getPosition().getCols();

            if (userRow < sharkRow && userCol < sharkCol) {
                return "moveUpLeft";
            }
            else if (userRow < sharkRow && userCol > sharkCol) {
                return "moveUpRight";
            }
            else if (userRow > sharkRow && userCol < sharkCol) {
                return "moveDownLeft";
            }
            else if (userRow > sharkRow && userCol > sharkCol) {
                return "moveDownRight";
            }
            else if (userRow < sharkRow) {
                return "moveUp";
            }
            else if (userRow > sharkRow) {
                return "moveDown";
            }
            else if (userCol < sharkCol) {
                return "moveLeft";
            }
            else {
                return "moveRight";
            }
        }
        else {
            String[] directions = new String[]{"moveLeft", "moveRight", "moveUp", "moveDown", "moveUpLeft",
                    "moveUpRight", "moveDownLeft", "moveDownRight"};

            int index = Util.getRandomNumber(8);

            return directions[index];
        }
    }

}
