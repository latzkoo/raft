package hu.latzkoo.raft;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class User extends Field implements Movable {

    private boolean fixed;
    private boolean killed;
    private boolean canBeAttacked;
    private boolean attacked;
    private Field parentField;

    public User(Dimension position, Field parentField) {
        this.setFixed(true);
        this.setKilled(false);
        this.setCanBeAttacked(false);
        this.setAttacked(false);
        this.setPosition(position);
        this.setParentField(parentField);

        try {
            String image;
            if (this.getParentField() instanceof WaterPurifier) {
                image = "userWaterPurifier.png";
            }
            else if (this.getParentField() instanceof Stove) {
                image = "userStove.png";
            }
            else if (this.getParentField() instanceof Net) {
                image = "userNet.png";
            }
            else if (this.getParentField() instanceof Raft) {
                image = "userRaft.png";
            }
            else {
                image = "user.png";
            }

            this.setImage(ImageIO.read(getClass().getClassLoader().getResourceAsStream(image)));
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean isKilled() {
        return killed;
    }

    public void setKilled(boolean killed) {
        this.killed = killed;
    }

    public boolean isAttacked() {
        return attacked;
    }

    public void setAttacked(boolean attacked) {
        this.attacked = attacked;
    }

    public boolean isCanBeAttacked() {
        return canBeAttacked;
    }

    public void setCanBeAttacked(boolean canBeAttacked) {
        this.canBeAttacked = canBeAttacked;
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

    @Override
    public boolean isAcceptedField(Field field) {
        return !(field instanceof RawMaterial);
    }

    @Override
    public boolean canMoveLeft() {
        return this.getPosition().getCols() > 0;
    }

    @Override
    public void moveLeft() {
        if (this.canMoveLeft()) {
            doMove(this.getPosition().getRows(), this.getPosition().getCols() - 1);
        }
    }

    @Override
    public boolean canMoveRight() {
        return this.getPosition().getCols() < Game.dimension.getCols() - 1;
    }

    @Override
    public void moveRight() {
        if (this.canMoveRight()) {
            doMove(this.getPosition().getRows(), this.getPosition().getCols() + 1);
        }
    }

    @Override
    public boolean canMoveUp() {
        return this.getPosition().getRows() > 0;
    }

    @Override
    public void moveUp() {
        if (this.canMoveUp()) {
            doMove(this.getPosition().getRows() - 1, this.getPosition().getCols());
        }
    }

    @Override
    public boolean canMoveDown() {
        return this.getPosition().getRows() < Game.dimension.getRows() - 1;
    }

    @Override
    public void moveDown() {
        if (this.canMoveDown()) {
            doMove(this.getPosition().getRows() + 1, this.getPosition().getCols());
        }
    }

    @Override
    public boolean canMoveUpLeft() {
        return this.getPosition().getRows() > 0 && this.getPosition().getCols() > 0;
    }

    @Override
    public void moveUpLeft() {
        if (this.canMoveUpLeft()) {
            doMove(this.getPosition().getRows() - 1, this.getPosition().getCols() - 1);
        }
    }

    @Override
    public boolean canMoveUpRight() {
        return this.getPosition().getRows() > 0 && this.getPosition().getCols() < Game.dimension.getCols() - 1;
    }

    @Override
    public void moveUpRight() {
        if (this.canMoveUpRight()) {
            doMove(this.getPosition().getRows() - 1, this.getPosition().getCols() + 1);
        }
    }

    @Override
    public boolean canMoveDownLeft() {
        return this.getPosition().getRows() < Game.dimension.getRows() - 1 && this.getPosition().getCols() > 0;
    }

    @Override
    public void moveDownLeft() {
        if (this.canMoveDownLeft()) {
            doMove(this.getPosition().getRows() + 1, this.getPosition().getCols() - 1);
        }
    }

    @Override
    public boolean canMoveDownRight() {
        return this.getPosition().getRows() < Game.dimension.getRows() - 1 &&
                this.getPosition().getCols() < Game.dimension.getCols() - 1;
    }

    @Override
    public void moveDownRight() {
        if (this.canMoveDownRight()) {
            doMove(this.getPosition().getRows() + 1, this.getPosition().getCols() + 1);
        }
    }

    /**
     * Mozgás vezérlése
     * @param direction irány
     * @throws ArrayIndexOutOfBoundsException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    public void move(String direction) throws ArrayIndexOutOfBoundsException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method method = this.getClass().getMethod(direction);
        method.invoke(this);
    }

    /**
     * Mozgás megvalósítása
     * @param row sor
     * @param col oszlop
     */
    private void doMove(int row, int col) {
        Field targetField = Game.getCanvas().getFields()[row][col];

        if (this.isAcceptedField(targetField)) {
            Field resourceField;

            if (this.getParentField() instanceof Shark) {
                resourceField = ((Shark) this.getParentField()).getParentField();
            }
            else {
                resourceField = this.getParentField();
            }

            if (targetField instanceof Shark && !(((Shark) targetField).getParentField() instanceof Raft)) {
                Game.getUser().kill();
            }
            else if (!(targetField instanceof Raft) && !(targetField instanceof Net)) {
                Game.getUser().setCanBeAttacked(true);
            }
            else {
                Game.getUser().setCanBeAttacked(false);
            }

            Canvas.setField("normal", this.getPosition().getRows(), this.getPosition().getCols(), resourceField);
            Canvas.setField("normal", row, col, new User(new Dimension(row, col), targetField));
            this.setPosition(new Dimension(row, col));
            this.setParentField(targetField);
        }
    }

    /**
     * A felhasználói események kezelése
     * @param event
     */
    public void doAction(String event) {
        if (event.equals("addArea")) {
            this.addArea();
        }
        else if (event.equals("addNet")) {
            this.addNet();
        }
        else if (event.equals("addWaterPurifier")) {
            this.addWaterPurifier();
        }
        else if (event.equals("doFishing")) {
            this.doFishing();
        }
        else if (event.equals("addStove")) {
            this.addStove();
        }
        else if (event.equals("createSpear")) {
            this.createSpear();
        }
        else {
            // ha cápatámadás van
            if (this.isAttacked() && !(this.getParentField() instanceof Raft) && !this.defense()) {
                this.kill();
            }
            // ha víztisztítón állunk
            else if (this.getParentField() instanceof WaterPurifier && this.canDrink((WaterPurifier) this.getParentField())) {
                this.drink((WaterPurifier) this.getParentField());
            }
            // ha tűzhelyen állunk
            else if (this.getParentField() instanceof Stove) {
                // evés
                if (this.canEat((Stove) this.getParentField())) {
                    this.eat((Stove) this.getParentField());
                }
                // étel elhelyezése a tűzhelyre
                else if (this.canCook((Stove) this.getParentField())) {
                    this.cook((Stove) this.getParentField());
                }
            }
            else {
                this.pickUpRawMaterials();
            }
        }
    }

    /**
     * Lándzsa készítése
     */
    private void createSpear() {
        if (this.canCreateSpear()) {
            LifeCycle.setSpearCount(LifeCycle.getSpearCount() + 1);
            LifeCycle.setSpearUsageCount(5);

            LifeCycle.setPlankCount(LifeCycle.getPlankCount() - 4);
            LifeCycle.setLeafCount(LifeCycle.getLeafCount() - 4);
            LifeCycle.setWasteCount(LifeCycle.getWasteCount() - 4);
            LifeCycle.setEventCount(LifeCycle.getEventCount() + 1);
        }
    }

    /**
     * Horgászat
     */
    private void doFishing() {
        if (!(this.getParentField() instanceof Raft)) {
            int chance = Util.getRandomNumber(100);

            if (chance < 25) {
                LifeCycle.setFishCount(LifeCycle.getFishCount() + 1);
            }
        }

        LifeCycle.setEventCount(LifeCycle.getEventCount() + 1);
    }

    /**
     * Ivás lehetőségének vizsgálata
     * @param waterPurifier víztisztító
     * @return boolean
     */
    private boolean canDrink(WaterPurifier waterPurifier) {
        return waterPurifier.isDone();
    }

    /**
     * Ivás
     * @param waterPurifier víztisztító
     */
    private void drink(WaterPurifier waterPurifier) {
        LifeCycle.setThirstyLevel(LifeCycle.getThirstyLevel() + 40);
        LifeCycle.setEventCount(LifeCycle.getEventCount() + 1);
        waterPurifier.reset();
    }

    /**
     * Főzés lehetőségének vizsgálata
     * @param stove tűzhely
     * @return boolean
     */
    private boolean canCook(Stove stove) {
        return (!stove.isHasFood() && (LifeCycle.getFishCount() > 0 || LifeCycle.getPotatoCount() > 0));
    }

    /**
     * Főzés
     * @param stove tűzhely
     */
    private void cook(Stove stove) {
        if (LifeCycle.getFishCount() > 0) {
            LifeCycle.setFishCount(LifeCycle.getFishCount() - 1);
        }
        else {
            LifeCycle.setPotatoCount(LifeCycle.getPotatoCount() - 1);
        }

        LifeCycle.setEventCount(LifeCycle.getEventCount() + 1);
        stove.setHasFood(true);
    }

    /**
     * Evés lehetőségének vizsgálata
     * @param stove tűzhely
     * @return boolean
     */
    private boolean canEat(Stove stove) {
        return stove.isDone();
    }

    /**
     * Evés
     * @param stove tűzhely
     */
    private void eat(Stove stove) {
        LifeCycle.setHungryLevel(LifeCycle.getHungryLevel() + 60);
        LifeCycle.setEventCount(LifeCycle.getEventCount() + 1);
        stove.reset();
    }

    /**
     * Nyersanyagok felvétele
     */
    private void pickUpRawMaterials() {
        for (Field field : Game.getNearbyFields(this, "full")) {
            if (field instanceof RawMaterial) {
                this.pickUp(field);
            }
        }
    }

    /**
     * Nyersanyag felvétele egy mezőről
     * @param field mező
     */
    public void pickUp(Field field) {
        int row = field.getPosition().getRows();
        int col = field.getPosition().getCols();

        if (field instanceof Plank)
            LifeCycle.setPlankCount(LifeCycle.getPlankCount() + 1);

        else if (field instanceof Leaf)
            LifeCycle.setLeafCount(LifeCycle.getLeafCount() + 1);

        else if (field instanceof Waste)
            LifeCycle.setWasteCount(LifeCycle.getWasteCount() + 1);

        else if (field instanceof Barrel) {
            String[] fieldTypes = new String[]{"plank", "leaf", "waste", "potato"};

            for (int i = 0; i < 5; i++) {
                int index = Util.getRandomNumber(4);

                switch (fieldTypes[index]) {
                    case "plank":
                        LifeCycle.setPlankCount(LifeCycle.getPlankCount() + 1);
                        break;
                    case "leaf":
                        LifeCycle.setLeafCount(LifeCycle.getLeafCount() + 1);
                        break;
                    case "waste":
                        LifeCycle.setWasteCount(LifeCycle.getWasteCount() + 1);
                        break;
                    case "potato":
                        LifeCycle.setPotatoCount(LifeCycle.getPotatoCount() + 1);
                        break;
                }
            }
        }

        Canvas.setField("normal", row + 1, col, new Field(new Dimension(row, col)));

        LifeCycle.setEventCount(LifeCycle.getEventCount() + 1);
    }

    /**
     * Tutaj bővítése
     */
    public void addArea() {
        if (this.canAddArea()) {
            int row = this.getPosition().getRows();
            int col = this.getPosition().getCols();

            Field area = new Raft(new Dimension(row, col));
            this.setParentField(area);
            Canvas.setField("normal", row, col, new User(new Dimension(row, col), area));

            LifeCycle.setPlankCount(LifeCycle.getPlankCount() - 2);
            LifeCycle.setLeafCount(LifeCycle.getLeafCount() - 2);
            LifeCycle.setEventCount(LifeCycle.getEventCount() + 1);

            Game.getCanvas().updateSharkDimensions();
        }
    }

    /**
     * Háló elhelyezése
     */
    public void addNet() {
        if (this.canAddNet()) {
            int row = this.getPosition().getRows();
            int col = this.getPosition().getCols();

            Field net = new Net(new Dimension(row, col));
            this.setParentField(net);
            Canvas.setField("normal", row, col, new User(new Dimension(row, col), net));

            LifeCycle.setPlankCount(LifeCycle.getPlankCount() - 2);
            LifeCycle.setLeafCount(LifeCycle.getLeafCount() - 6);
            LifeCycle.setEventCount(LifeCycle.getEventCount() + 1);

            Game.getCanvas().updateSharkDimensions();
        }
    }

    /**
     * Víztisztító elhelyezése
     */
    public void addWaterPurifier() {
        if (this.canAddWaterPurifier()) {
            int row = this.getPosition().getRows();
            int col = this.getPosition().getCols();

            WaterPurifier waterPurifier = new WaterPurifier(new Dimension(row, col));
            this.setParentField(waterPurifier);
            Canvas.setField("normal", row, col, new User(new Dimension(row, col), waterPurifier));

            LifeCycle.setLeafCount(LifeCycle.getLeafCount() - 2);
            LifeCycle.setWasteCount(LifeCycle.getWasteCount() - 4);
            LifeCycle.setEventCount(LifeCycle.getEventCount() + 1);

            Game.addWaterPurifier(waterPurifier);
        }
    }

    /**
     * Tűzhely elhelyezése
     */
    public void addStove() {
        if (this.canAddStove()) {
            int row = this.getPosition().getRows();
            int col = this.getPosition().getCols();

            Stove stove = new Stove(new Dimension(row, col));
            this.setParentField(stove);
            Canvas.setField("normal", row, col, new User(new Dimension(row, col), stove));

            LifeCycle.setPlankCount(LifeCycle.getPlankCount() - 2);
            LifeCycle.setLeafCount(LifeCycle.getLeafCount() - 4);
            LifeCycle.setWasteCount(LifeCycle.getWasteCount() - 3);
            LifeCycle.setEventCount(LifeCycle.getEventCount() + 1);

            Game.addStove(stove);
        }
    }

    /**
     * Azt vizsgálja, hogy a szomszédos mezőkre lehetséges-e a tutajbővítés vagy a háló elhelyezése
     * @return
     */
    private boolean isAreaAvailableForAdd() {
        for (Field field : Game.getNearbyFields(this, "normal")) {
            if (field instanceof Raft) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tutajbővítés lehetőségének vizsgálata
     * @return
     */
    private boolean canAddArea() {
        return LifeCycle.getPlankCount() >= 2 && LifeCycle.getLeafCount() >= 2 && isAreaAvailableForAdd();
    }

    /**
     * Háló elhelyezés lehetőségének vizsgálata
     * @return
     */
    private boolean canAddNet() {
        return LifeCycle.getPlankCount() >= 2 && LifeCycle.getLeafCount() >= 6 && isAreaAvailableForAdd();
    }

    /**
     * Víztisztító elhelyezés lehetőségének vizsgálata
     * @return
     */
    private boolean canAddWaterPurifier() {
        return LifeCycle.getPlankCount() >= 2 && LifeCycle.getLeafCount() >= 4 && isAreaAvailableForStuff();
    }

    /**
     * Tűzhely elhelyezés lehetőségének vizsgálata
     * @return
     */
    private boolean canAddStove() {
        return LifeCycle.getPlankCount() >= 2 && LifeCycle.getLeafCount() >= 4 && LifeCycle.getWasteCount() >= 3
                && isAreaAvailableForStuff();
    }

    /**
     * Lándzsakészítés lehetőségének vizsgálata
     * @return
     */
    private boolean canCreateSpear() {
        return LifeCycle.getPlankCount() >= 4 && LifeCycle.getLeafCount() >= 4 && LifeCycle.getWasteCount() >= 4;
    }

    /**
     * Tutaj mező vizsgálata
     * Elhelyezhető-e rajta tűzhely vagy víztisztító
     * @return
     */
    private boolean isAreaAvailableForStuff() {
        return this.getParentField() instanceof Raft;
    }

    /**
     * Cápatámadás
     */
    public void kill() {
        if (this.isAttacked()) {
            this.setKilled(true);
            Game.stopGame();
        }
        else {
            this.setAttacked(true);
        }
    }

    /**
     * Védekezés
     * @return boolean
     */
    private boolean defense() {
        if (LifeCycle.getSpearUsageCount() > 0) {
            this.setAttacked(false);
            LifeCycle.setSpearUsageCount(LifeCycle.getSpearUsageCount() - 1);

            if (LifeCycle.getSpearUsageCount() == 1) {
                LifeCycle.setSpearCount(LifeCycle.getSpearCount() - 1);
                LifeCycle.setEventCount(LifeCycle.getEventCount() + 1);
            }

            return true;
        }

        return false;
    }

}
