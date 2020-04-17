package hu.latzkoo.raft;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Objects;

public class Game extends JFrame {

    public static final int SIZE = 25;
    public static final Dimension dimension = new Dimension(25, 35);
    private static User user;
    private static Shark shark;
    private static Canvas canvas;
    private static Timer timer;
    private static Dimension[] sharkDimension = new Dimension[2];
    private static ArrayList<WaterPurifier> waterPurifiers = new ArrayList<>();
    private static ArrayList<Stove> stoves = new ArrayList<>();
    private static String status;
    private static boolean continueAfterWin;

    public Game() {
        sharkDimension = new Dimension[2];
        waterPurifiers = new ArrayList<>();
        stoves = new ArrayList<>();
        continueAfterWin = false;
    }

    /**
     * Cselekvés validálása esemény alapján
     * @param event esemény
     * @return boolean
     */
    public static boolean isAction(String event) {
        return event.equals("doAction") || event.equals("doFishing") || event.equals("addArea") ||
                event.equals("addNet") || event.equals("addWaterPurifier") || event.equals("addStove") ||
                event.equals("createSpear");
    }

    /**
     * Irány validálása esemény alapján
     * @param event esemény
     * @return boolean
     */
    public static boolean isDirection(String event) {
        switch (event) {
            case "moveLeft":
            case "moveRight":
            case "moveUp":
            case "moveDown":
            case "moveUpLeft":
            case "moveUpRight":
            case "moveDownLeft":
            case "moveDownRight":
                return true;
            default:
                return false;
        }
    }

    public static boolean isContinueAfterWin() {
        return continueAfterWin;
    }

    public static void setContinueAfterWin(boolean continueAfterWin) {
        Game.continueAfterWin = continueAfterWin;
    }

    public static String getStatus() {
        return status;
    }

    public static void setStatus(String status) {
        Game.status = status;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        Game.user = user;
    }

    public static Shark getShark() {
        return shark;
    }

    public static void setShark(Shark shark) {
        Game.shark = shark;
    }

    public static Canvas getCanvas() {
        return canvas;
    }

    public static void setCanvas(Canvas canvas) {
        Game.canvas = canvas;
    }

    public static Dimension[] getSharkDimension() {
        return sharkDimension;
    }

    public static void setSharkDimension(Dimension[] sharkDimension) {
        Game.sharkDimension = sharkDimension;
    }


    /**
     * Játék indítása
     */
    public void start() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!Game.isContinueAfterWin() && isOver() != null) {
                    if (Objects.equals(isOver(), "lost")) {
                        stopGame();
                    } else if (!isContinueAfterWin()) {
                        winGame();
                    }
                } else {
                    LifeCycle.setEventCount(LifeCycle.getEventCount() + 1);
                    LifeCycle.setHungryLevel(LifeCycle.getHungryLevel() - 1);
                    LifeCycle.setThirstyLevel(LifeCycle.getThirstyLevel() - 1);

                    doPurifyingWater();
                    doCooking();
                    fireAction();
                }
            }
        });

        timer.stop();
        timer.setInitialDelay(1000);
        timer.start();

        setStatus("running");
    }

    /**
     * Játék folytatása
     */
    private void winGame() {
        setStatus("paused");
        timer.stop();

        int value = JOptionPane.showConfirmDialog(null,
                "Nyertél, elérted az 1 500 cselekvést!\nFolytatod a játékot?", "Nyertél!",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (value == 0) {
            continueGame();
        }
        else {
            setStatus("stopped");
        }
    }

    /**
     * Játék befejezése
     */
    public static void stopGame() {
        setStatus("stopped");
        setContinueAfterWin(false);
        timer.stop();
        JOptionPane.showMessageDialog(canvas, "A játék véget ért!\nSajnos vesztettél.");
    }

    /**
     * Játék szüneteltetése
     */
    public static void pauseGame() {
        timer.stop();
        setStatus("paused");
    }

    /**
     * Játék folytatása
     */
    public static void continueGame() {
        setContinueAfterWin(true);
        setStatus("running");
        timer.restart();
    }

    /**
     * A játék befejezésének vizsgálata
     * @return status
     */
    public static String isOver() {
        if (user.isAttacked() || user.isKilled() ||
                LifeCycle.getHungryLevel() == 0 || LifeCycle.getThirstyLevel() == 0) {
            return "lost";
        }
        else if (LifeCycle.getEventCount() >= 1500) {
            return "win";
        }

        return null;
    }

    /**
     * Billentyűzet események validálása
     * @param keyCode billentyűkód
     * @return event
     */
    public static String getEvent(int keyCode) {
        switch (keyCode) {
            case 37:
            case 100:
                return "moveLeft";
            case 38:
            case 104:
                return "moveUp";
            case 39:
            case 102:
                return "moveRight";
            case 40:
            case 98:
                return "moveDown";
            case 103:
                return "moveUpLeft";
            case 105:
                return "moveUpRight";
            case 97:
                return "moveDownLeft";
            case 99:
                return "moveDownRight";
            case 32:
                return "doAction";
            case 70:
                return "doFishing";
            case 65:
                return "addArea";
            case 87:
                return "addWaterPurifier";
            case 83:
                return "addStove";
            case 78:
                return "addNet";
            case 81:
                return "createSpear";
            default:
                return null;
        }
    }

    /**
     * Nyersanyagot generál.
     * @return Field nyersanyag
     */
    public static Field generateField() {
        Field field;

        int types = Util.getRandomNumber(100);

        if (types < 32)
            field = new Plank();

        else if (types < 64)
            field = new Leaf();

        else if (types < 96)
            field = new Waste();

        else
            field = new Barrel();

        return field;
    }

    /**
     * Összegyűjti az aktuális mezőhöz közeli mezőket.
     * @param field aktuális mező
     * @param mode normal: balra, jobbra, le, fel, full: átlós irányban is
     * @return
     */
    public static ArrayList<Field> getNearbyFields(Field field, String mode) {
        int row = field.getPosition().getRows();
        int col = field.getPosition().getCols();

        ArrayList<Field> nearbyFields = new ArrayList<>();

        if (col > 0) {
            nearbyFields.add(getCanvas().getFields()[row][col - 1]);
        }
        if (col < dimension.getCols() - 1) {
            nearbyFields.add(getCanvas().getFields()[row][col + 1]);
        }
        if (row > 0) {
            nearbyFields.add(getCanvas().getFields()[row - 1][col]);
        }
        if (row < dimension.getRows() - 1) {
            nearbyFields.add(getCanvas().getFields()[row + 1][col]);
        }

        if (mode.equals("full")) {
            if (row > 0 && col < dimension.getCols() - 1) {
                nearbyFields.add(getCanvas().getFields()[row - 1][col + 1]);
            }
            if (row > 0 && col > 0) {
                nearbyFields.add(getCanvas().getFields()[row - 1][col - 1]);
            }
            if (row < dimension.getRows() - 1 && col > 0) {
                nearbyFields.add(getCanvas().getFields()[row + 1][col - 1]);
            }
            if (row < dimension.getRows() - 1 && col < dimension.getCols() - 1) {
                nearbyFields.add(getCanvas().getFields()[row + 1][col + 1]);
            }
        }

        return nearbyFields;
    }

    /**
     * Víztisztítók lekérdezése
     * @return waterPurifiers
     */
    public static ArrayList<WaterPurifier> getWaterPurifiers() {
        return waterPurifiers;
    }

    /**
     * Víztisztító hozzáadása
     * @param waterPurifier víztisztító
     */
    public static void addWaterPurifier(WaterPurifier waterPurifier) {
        waterPurifiers.add(waterPurifier);
    }

    /**
     * Tűzhelyek lekérdezáse
      * @return Stoves
     */
    public static ArrayList<Stove> getStoves() {
        return stoves;
    }

    /**
     * Tűzhely hozzáadása
     * @param stove tűzhely
     */
    public static void addStove(Stove stove) {
        stoves.add(stove);
    }

    /**
     * Víztisztító működtetése
     */
    private static void doPurifyingWater() {
        for (WaterPurifier waterPurifier : getWaterPurifiers()) {
            waterPurifier.process();
        }
    }

    /**
     * Tűzhely működtetése
     */
    private static void doCooking() {
        for (Stove stove : getStoves()) {
            stove.process();
        }
    }

    /**
     * Folyam működtetése, cápa mozgatása
     */
    public void fireAction() {
        try {
            shark.move();
            getCanvas().doFlow();
        }
        catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

}
