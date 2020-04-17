package hu.latzkoo.raft;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.font.TextAttribute;
import java.util.HashMap;
import java.util.Map;

public class Statusbar {

    private static StatusbarElement eventCount;
    private static StatusbarElement leafCount;
    private static StatusbarElement plankCount;
    private static StatusbarElement wasteCount;
    private static StatusbarElement fishCount;
    private static StatusbarElement potatoCount;
    private static StatusbarElement spearCount;
    private static StatusbarElement thirstyLevel;
    private static StatusbarElement hungryLevel;

    public Statusbar() {

    }

    public static void show() {
        EmptyBorder border = new EmptyBorder(3, 8, 3, 8);
        EmptyBorder borderRight = new EmptyBorder(0, 0, 0, 10);

        // Header
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(50, 50, 50));
        header.setBorder(border);
        Main.app.add(header, BorderLayout.NORTH);

        JPanel headerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        headerLeft.setBackground(new Color(50, 50, 50));
        header.add(headerLeft, BorderLayout.WEST);

        JPanel headerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        headerRight.setBackground(new Color(50, 50, 50));
        header.add(headerRight, BorderLayout.EAST);

        JLabel eventLabel = Statusbar.getEventCount().getText();
        headerLeft.add(eventLabel);
        setFont(eventLabel);
        JLabel eventCount = Statusbar.getEventCount().getValue();
        setFont(eventCount);
        headerLeft.add(eventCount);

        JLabel hungryLabel = Statusbar.getHungryLevel().getText();
        setFont(hungryLabel);
        headerRight.add(hungryLabel);
        JLabel hungryLevel = Statusbar.getHungryLevel().getValue();
        setFont(hungryLevel);
        headerRight.add(hungryLevel);
        hungryLevel.setBorder(borderRight);

        JLabel thirstyLabel = Statusbar.getThirstyLevel().getText();
        setFont(thirstyLabel);
        headerRight.add(thirstyLabel);
        JLabel thirstyLevel = Statusbar.getThirstyLevel().getValue();
        setFont(thirstyLevel);
        headerRight.add(thirstyLevel);


        // Footer
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBackground(new Color(50, 50, 50));
        footer.setBorder(border);
        Main.app.add(footer, BorderLayout.SOUTH);

        JPanel footerLeft = new JPanel(new FlowLayout(FlowLayout.LEFT));
        footerLeft.setBackground(new Color(50, 50, 50));
        footer.add(footerLeft, BorderLayout.WEST);

        JPanel footerRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerRight.setBackground(new Color(50, 50, 50));
        footer.add(footerRight, BorderLayout.EAST);


        JLabel plankLabel = Statusbar.getPlankCount().getText();
        setFont(plankLabel);
        footerLeft.add(plankLabel);
        JLabel plankCount = Statusbar.getPlankCount().getValue();
        setFont(plankCount);
        footerLeft.add(plankCount);
        plankCount.setBorder(borderRight);

        JLabel leafLabel = Statusbar.getLeafCount().getText();
        setFont(leafLabel);
        footerLeft.add(leafLabel);
        JLabel leafCount = Statusbar.getLeafCount().getValue();
        footerLeft.add(leafCount);
        setFont(leafCount);
        leafCount.setBorder(borderRight);

        JLabel wasteLabel = Statusbar.getWasteCount().getText();
        setFont(wasteLabel);
        footerLeft.add(wasteLabel);
        JLabel wasteCount = Statusbar.getWasteCount().getValue();
        setFont(wasteCount);
        footerLeft.add(wasteCount);
        wasteCount.setBorder(borderRight);

        JLabel fishLabel = Statusbar.getFishCount().getText();
        setFont(fishLabel);
        footerLeft.add(fishLabel);
        JLabel fishCount = Statusbar.getFishCount().getValue();
        setFont(fishCount);
        footerLeft.add(fishCount);
        fishCount.setBorder(borderRight);

        JLabel potatoLabel = Statusbar.getPotatoCount().getText();
        setFont(potatoLabel);
        footerLeft.add(potatoLabel);
        JLabel potatoCount = Statusbar.getPotatoCount().getValue();
        setFont(potatoCount);
        footerLeft.add(potatoCount);
        potatoCount.setBorder(borderRight);

        JLabel spearLabel = Statusbar.getSpearCount().getText();
        setFont(spearLabel);
        footerRight.add(spearLabel);
        JLabel spearCount = Statusbar.getSpearCount().getValue();
        setFont(spearCount);
        footerRight.add(spearCount);
    }

    private static void setFont(JLabel field) {
        field.setForeground(Colors.FOREGROUND);
        Map<TextAttribute, Object> attributes = new HashMap<>();
        attributes.put(TextAttribute.WEIGHT, TextAttribute.WEIGHT_BOLD);
        field.setFont(Font.getFont(attributes));
    }

    public static StatusbarElement getEventCount() {
        return eventCount;
    }

    public static void setEventCount(StatusbarElement eventCount) {
        Statusbar.eventCount = eventCount;
    }

    public static StatusbarElement getLeafCount() {
        return leafCount;
    }

    public static void setLeafCount(StatusbarElement leafCount) {
        Statusbar.leafCount = leafCount;
    }

    public static StatusbarElement getPlankCount() {
        return plankCount;
    }

    public static void setPlankCount(StatusbarElement plankCount) {
        Statusbar.plankCount = plankCount;
    }

    public static StatusbarElement getWasteCount() {
        return wasteCount;
    }

    public static void setWasteCount(StatusbarElement wasteCount) {
        Statusbar.wasteCount = wasteCount;
    }

    public static StatusbarElement getPotatoCount() {
        return potatoCount;
    }

    public static void setPotatoCount(StatusbarElement potatoCount) {
        Statusbar.potatoCount = potatoCount;
    }

    public static StatusbarElement getFishCount() {
        return fishCount;
    }

    public static void setFishCount(StatusbarElement fishCount) {
        Statusbar.fishCount = fishCount;
    }

    public static StatusbarElement getSpearCount() {
        return spearCount;
    }

    public static void setSpearCount(StatusbarElement spearCount) {
        Statusbar.spearCount = spearCount;
    }

    public static StatusbarElement getThirstyLevel() {
        return thirstyLevel;
    }

    public static void setThirstyLevel(StatusbarElement thirstyLevel) {
        Statusbar.thirstyLevel = thirstyLevel;
    }

    public static StatusbarElement getHungryLevel() {
        return hungryLevel;
    }

    public static void setHungryLevel(StatusbarElement hungryLevel) {
        Statusbar.hungryLevel = hungryLevel;
    }

}
