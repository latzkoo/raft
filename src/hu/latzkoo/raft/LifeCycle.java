package hu.latzkoo.raft;

public class LifeCycle {

    private static int eventCount = 0;
    private static int leafCount = 0;
    private static int plankCount = 0;
    private static int wasteCount = 0;
    private static int fishCount = 0;
    private static int potatoCount = 0;
    private static int spearCount = 0;
    private static int spearUsageCount = 0;
    private static int thirstyLevel = 100;
    private static int hungryLevel = 100;

    public static int getEventCount() {
        return eventCount;
    }

    public static void setEventCount(int eventCount) {
        LifeCycle.eventCount = eventCount;
        LifeCycle.updateGUI(Statusbar.getEventCount(), Integer.toString(LifeCycle.getEventCount()));
    }

    public static int getLeafCount() {
        return leafCount;
    }

    public static void setLeafCount(int leafCount) {
        if (leafCount < 0)
            leafCount = 0;

        LifeCycle.leafCount = leafCount;
        LifeCycle.updateGUI(Statusbar.getLeafCount(), Integer.toString(LifeCycle.getLeafCount()));
    }

    public static int getPlankCount() {
        return plankCount;
    }

    public static void setPlankCount(int plankCount) {
        if (plankCount < 0)
            plankCount = 0;

        LifeCycle.plankCount = plankCount;
        LifeCycle.updateGUI(Statusbar.getPlankCount(), Integer.toString(LifeCycle.getPlankCount()));
    }

    public static int getWasteCount() {
        return wasteCount;
    }

    public static void setWasteCount(int wasteCount) {
        if (wasteCount < 0)
            wasteCount = 0;

        LifeCycle.wasteCount = wasteCount;
        LifeCycle.updateGUI(Statusbar.getWasteCount(), Integer.toString(LifeCycle.getWasteCount()));
    }

    public static int getFishCount() {
        return fishCount;
    }

    public static void setFishCount(int fishCount) {
        if (fishCount < 0)
            fishCount = 0;

        LifeCycle.fishCount = fishCount;
        LifeCycle.updateGUI(Statusbar.getFishCount(), Integer.toString(LifeCycle.getFishCount()));
    }

    public static int getPotatoCount() {
        return potatoCount;
    }

    public static void setPotatoCount(int potatoCount) {
        if (potatoCount < 0)
            potatoCount = 0;

        LifeCycle.potatoCount = potatoCount;
        LifeCycle.updateGUI(Statusbar.getPotatoCount(), Integer.toString(LifeCycle.getPotatoCount()));
    }

    public static int getSpearCount() {
        return spearCount;
    }

    public static void setSpearCount(int spearCount) {
        if (spearCount < 0)
            spearCount = 0;

        LifeCycle.spearCount = spearCount;
        LifeCycle.updateGUI(Statusbar.getSpearCount(), Integer.toString(LifeCycle.getSpearCount()));
    }

    public static int getThirstyLevel() {
        return thirstyLevel;
    }

    public static void setThirstyLevel(int thirstyLevel) {
        if (thirstyLevel < 0)
            thirstyLevel = 0;
        else if (thirstyLevel > 100)
            thirstyLevel = 100;

        LifeCycle.thirstyLevel = thirstyLevel;
        LifeCycle.updateGUI(Statusbar.getThirstyLevel(), LifeCycle.getThirstyLevel() + "%");
    }

    public static int getHungryLevel() {
        return hungryLevel;
    }

    public static void setHungryLevel(int hungryLevel) {
        if (hungryLevel < 0)
            hungryLevel = 0;
        else if (hungryLevel > 100)
            hungryLevel = 100;

        LifeCycle.hungryLevel = hungryLevel;
        LifeCycle.updateGUI(Statusbar.getHungryLevel(), LifeCycle.getHungryLevel() + "%");
    }

    public static int getSpearUsageCount() {
        return spearUsageCount;
    }

    public static void setSpearUsageCount(int spearUsageCount) {
        if (spearUsageCount < 0)
            spearUsageCount = 0;
        else if (spearUsageCount > 5)
            spearUsageCount = 5;

        LifeCycle.spearUsageCount = spearUsageCount;
    }

    public static void updateGUI(StatusbarElement element, String value) {
        element.getValue().setText(value);
    }

    public static void reset() {
        setEventCount(0);
        setLeafCount(0);
        setPlankCount(0);
        setWasteCount(0);
        setFishCount(0);
        setPotatoCount(0);
        setSpearCount(0);
        setSpearUsageCount(0);
        setThirstyLevel(100);
        setHungryLevel(100);
    }

}
