package hu.latzkoo.raft;

public interface Movable {

    boolean isAcceptedField(Field field);

    boolean canMoveLeft();

    void moveLeft();

    boolean canMoveRight();

    void moveRight();

    boolean canMoveUp();

    void moveUp();

    boolean canMoveDown();

    void moveDown();

    boolean canMoveUpLeft();

    void moveUpLeft();

    boolean canMoveUpRight();

    void moveUpRight();

    boolean canMoveDownLeft();

    void moveDownLeft();

    boolean canMoveDownRight();

    void moveDownRight();

}
