package hu.latzkoo.raft;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

    public static JFrame app;
    public static final String VERSION = "1.0.1";

    public static void main(String[] args) {
        init();
    }

    public static void init() {
        Main.app = new JFrame("Raft " + VERSION);
        Main.app.setLayout(new BorderLayout());
        Main.app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setAppIcon();
        setMenus();
        setStatusBar();
        setKeyListener();

        Game game = create();
        game.start();
    }

    /**
     * Menü beállítása
     */
    private static void setMenus() {
        MenuListener menuListener = new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                if (Game.getStatus().equals("running")) {
                    Game.pauseGame();
                }
            }

            @Override
            public void menuDeselected(MenuEvent e) {
                if (Game.getStatus().equals("paused")) {
                    Game.continueGame();
                }
            }

            @Override
            public void menuCanceled(MenuEvent e) {
                if (Game.getStatus().equals("paused")) {
                    Game.continueGame();
                }
            }
        };

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("Fájl");
        fileMenu.addMenuListener(menuListener);

        JMenuItem newGame = new JMenuItem("Új játék");
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = create();

                if (Game.getStatus().equals("stopped")) {
                    game.start();
                }
            }
        });

        JMenuItem exit = new JMenuItem("Kilépés");
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F10, 0));
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        fileMenu.add(newGame);
        fileMenu.addSeparator();
        fileMenu.add(exit);

        JMenu helpMenu = new JMenu("Súgó");
        helpMenu.addMenuListener(menuListener);

        JMenuItem help = new JMenuItem("Súgó");
        help.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
        help.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Game.getStatus().equals("running")) {
                    Game.pauseGame();
                }
                new Help();
            }
        });

        JMenuItem about = new JMenuItem("Névjegy");
        about.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0));
        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (Game.getStatus().equals("running")) {
                    Game.pauseGame();
                }
                new About();
            }
        });

        helpMenu.add(help);
        helpMenu.addSeparator();
        helpMenu.add(about);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);
        Main.app.setJMenuBar(menuBar);
    }

    /**
     * Billentyű események kezelése
     */
    private static void setKeyListener() {
        app.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                String event = Game.getEvent(e.getKeyCode());

                if (Game.isContinueAfterWin() || Game.isOver() == null) {
                    try {
                        if (event != null) {
                            if (Game.isDirection(event)) {
                                Game.getUser().move(event);
                            }
                            else if (Game.isAction(event)) {
                                Game.getUser().doAction(event);
                            }
                        }
                    }
                    catch (Exception exception) {
                        System.err.println(exception.toString());
                    }
                }
            }
        });
    }

    /**
     * Status bar beállítása
     */
    private static void setStatusBar() {
        Statusbar.setEventCount(new StatusbarElement(
                new JLabel("Cselekvések:"),
                new JLabel(Integer.toString(LifeCycle.getEventCount())))
        );
        Statusbar.setPlankCount(new StatusbarElement(
                new JLabel("Deszka:"),
                new JLabel(Integer.toString(LifeCycle.getPlankCount())))
        );
        Statusbar.setLeafCount(new StatusbarElement(
                new JLabel("Levél:"),
                new JLabel(Integer.toString(LifeCycle.getLeafCount())))
        );
        Statusbar.setWasteCount(new StatusbarElement(
                new JLabel("Hulladék:"),
                new JLabel(Integer.toString(LifeCycle.getWasteCount())))
        );
        Statusbar.setFishCount(new StatusbarElement(
                new JLabel("Hal:"),
                new JLabel(Integer.toString(LifeCycle.getFishCount())))
        );
        Statusbar.setPotatoCount(new StatusbarElement(
                new JLabel("Burgonya:"),
                new JLabel(Integer.toString(LifeCycle.getPotatoCount())))
        );
        Statusbar.setHungryLevel(new StatusbarElement(
                new JLabel("Éhség:"),
                new JLabel(LifeCycle.getHungryLevel() + "%"))
        );
        Statusbar.setThirstyLevel(new StatusbarElement(
                new JLabel("Szomjúság:"),
                new JLabel(LifeCycle.getThirstyLevel() + "%"))
        );
        Statusbar.setSpearCount(new StatusbarElement(
                new JLabel("Lándzsa:"),
                new JLabel(Integer.toString(LifeCycle.getSpearCount())))
        );

        Statusbar.show();
    }

    /**
     * Alkalmazás ikonok beállítása
     */
    private static void setAppIcon() {
        try {
            BufferedImage image = ImageIO.read(Main.class.getClassLoader().getResourceAsStream("icon.png"));
            Main.app.setIconImage(image);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Build Frame
     * @return game
     */
    public static Game create() {
        LifeCycle.reset();
        Game game = new Game();

        Game.setCanvas(new Canvas(Game.dimension));
        app.add(Game.getCanvas(), BorderLayout.CENTER);

        Main.app.pack();
        Main.app.setLocationRelativeTo(null);
        Main.app.setResizable(false);
        Main.app.setVisible(true);

        return game;
    }

}
