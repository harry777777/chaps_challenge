package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.Record.Recorder;
import nz.ac.vuw.ecs.swen225.gp20.Record.Replay;
import nz.ac.vuw.ecs.swen225.gp20.Record.TickEvent;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Level;
import nz.ac.vuw.ecs.swen225.gp20.persistence.LevelManager;
import nz.ac.vuw.ecs.swen225.gp20.render.Renderer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;


/**
 * @author Owen
 * <p>
 * Application class, this runs the game loop,creates the GUI and manages the key listeners
 */

//todo add relevant buttons and menu items.
//todo add menu bar, add all keybinds

public class Application {

    private static JFrame frame;
    private Maze maze;
    private Renderer renderer;
    private final boolean moving = false;
    private Recorder r;
    private TickEvent tickEvent;
    private int timer;
    private int currentTick = 0;
    private boolean running = true;
    private boolean paused = false;
    private Replay replay;
    private Replay save;
    private boolean replaying = false;
    private boolean ctrlPressed = false;
    private JLabel timerLabel;
    private boolean gameOver = false;
    private boolean loadingSave = false;
    private boolean stepByStepReplay = false;
    private LevelManager manager;
    private Level level;

    private double GAME_HERTZ = 60;
    private double TBU = 1000000000 / GAME_HERTZ; // Time before update

    /**
     * @author Owen
     * <p>
     * Application class, this runs the game loop and creates the GUI
     */

    public static void main(String[] args) {
        Application A = new Application();
    }

    /**
     * initialise Application and run the loop
     */
    public Application() {
        loadGame(null);
        initialiseGui();
        run();

    }

    private void loadGame(String fileName) {
        Maze m = null;
        manager = new LevelManager();
        if (paused) {
            paused = false;
        }
        if (gameOver) {
            gameOver = false;
        }
        if (fileName != null) {
            try {
                level = manager.loadLevel("levels/" + fileName);
                m = level.getMaze();
                timer = level.getTimer();
                if (fileName.equals("level2.json")) {
                    r = new Recorder(2);
                }
            } catch (Exception E) {
                System.out.println("Error loading chosen level: " + E.getMessage());
            }
        } else {
            try {

                level = manager.loadLevel("levels/testLevel.json");
                System.out.println("asd");
                m = level.getMaze();
                r = new Recorder(1);
                //timer = level.getTimer();

            } catch (Exception E) {
                System.out.println("Error loading level 1: " + E.getMessage());
            }
        }
        this.maze = m;
        renderer = new Renderer(m);
        timer = 60;
    }

    /**
     * Initialises the Gui and creates the key listener
     */
    private void initialiseGui() {
        frame = new JFrame("Chaps Challenge");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JMenu File = new JMenu("File");
        JMenu Game = new JMenu("Game");

        JMenuItem exit = new JMenuItem("Exit");
        JMenuItem start = new JMenuItem("Start");

        File.add(exit);
        Game.add(start);
        menuBar.add(File);
        menuBar.add(Game);

        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(700, 700));
        frame.setLayout(new BorderLayout());

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 17) { //ctrl pressed
                    ctrlPressed = true;
                }
                if (e.getKeyCode() == 88 && ctrlPressed) { // X pressed - Exits the Game
                    System.exit(0);
                }
                if (e.getKeyCode() == 83 && ctrlPressed) { //S pressed - Saves and Exists the Game

                    try {
                        Level newSave = new Level(maze, timer);
                        manager.saveLevel("levels/savedGame.json", newSave);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    System.exit(0);
                }
                if (e.getKeyCode() == 82 && ctrlPressed) { //R pressed - Resume a Saved Game
                    JFileChooser fc = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Json File", "json");
                    fc.setFileFilter(filter);
                    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int returnVal = fc.showOpenDialog(frame);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        loadGame(file.getName());
                    }
                }
                if (e.getKeyCode() == 80
                        && ctrlPressed) { //P pressed - Start a New Game at the last unfinished Level
                    File file = new File("levels/l1c.txt");
                    boolean exists = file.exists();
                    if (exists) {
                        loadGame("level2.json");
                    } else {
                        loadGame(null);
                    }
                }
                if (e.getKeyCode() == 49 && ctrlPressed) { //1 pressed - Start Game at Level 1
                    loadGame(null);

                }
                if (e.getKeyCode() == 32) { //Space Pressed - Pause and open pause window
                    paused = true;
                    JOptionPane.showMessageDialog(null, "The Game is Paused", "", JOptionPane.ERROR_MESSAGE);
                }
                if (e.getKeyCode() == 27) { //Esc Pressed - Unpause and close pause window
                    paused = false;
                }
                if (!paused) {
                    if ((e.getKeyCode() == 38) && !replaying) {
                        tickEvent = new TickEvent(currentTick, Direction.UP);
                    }
                    if ((e.getKeyCode() == 40) && !replaying) {
                        tickEvent = new TickEvent(currentTick, Direction.DOWN);
                    }
                    if ((e.getKeyCode() == 37) && !replaying) {
                        tickEvent = new TickEvent(currentTick, Direction.LEFT);
                    }
                    if ((e.getKeyCode() == 39) && !replaying) {
                        tickEvent = new TickEvent(currentTick, Direction.RIGHT);
                    }
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == 17) {
                    ctrlPressed = false;
                }
            }
        });

        // Set up drawing panel
        JComponent drawing = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                redraw(g);
            }
        };
        drawing.setVisible(true);
        frame.add(drawing, BorderLayout.CENTER);

        JPanel gameInfo = new JPanel();
        JLabel newTimer = new JLabel("Time:" + timer);
        JLabel level = new JLabel("Level:");
        gameInfo.add(newTimer);
        timerLabel = newTimer;
        gameInfo.add(level);
        frame.add(gameInfo, BorderLayout.EAST);

        JPanel buttons = new JPanel();
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Object recordingName = JOptionPane.showInputDialog(frame,"Choose a recording name");
                    r.saveRecording(recordingName.toString());
                } catch (Exception E) {
                    System.out.println("Error saving recording:" + E.getMessage());
                }
                System.exit(0);
            }
        });
        exitButton.setFocusable(false);

        JButton pauseButton = new JButton("Pause");
        pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paused = true;
            }
        });
        pauseButton.setFocusable(false);
        JButton unPauseButton = new JButton("UnPause");
        unPauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                paused = false;
            }
        });
        unPauseButton.setFocusable(false);

        JButton replayButton = new JButton("Replay");
        replayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fc = new JFileChooser();
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("Json File", "json");
                    fc.setFileFilter(filter);
                    fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    int returnVal = fc.showOpenDialog(frame);

                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        File file = fc.getSelectedFile();
                        replay = new Replay(file.getName());
                        replaying = true;
                        paused = true;
                        Object[] possibleValues = {"Normal Speed", "Half Speed", "Double Speed",
                                "Step by Step"};
                        Object replayChoice = JOptionPane.showInputDialog(null,
                                "What type of replay", "Input",
                                JOptionPane.INFORMATION_MESSAGE, null,
                                possibleValues, possibleValues[0]);
                        if (replayChoice == "Step by Step") {
                            stepByStepReplay = true;
                        }
                        if (replayChoice == "Half Speed") {
                            GAME_HERTZ = 30;
                            TBU = 1000000000 / GAME_HERTZ;
                        }
                        if (replayChoice == "Double Speed") {
                            GAME_HERTZ = 60 * 60;
                            TBU = 1000000000 / GAME_HERTZ;
                        }

                        if (replay.recordingLevel() == 1) {
                            loadGame("level1.json");
                        } else if (replay.recordingLevel() == 2) {
                            loadGame("level2.json");
                        }
                        currentTick = 0;
                        timer = 60;
                        paused = false;
                        tickEvent = replay.getNextTick();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        replayButton.setFocusable(false);

        buttons.add(replayButton);
        buttons.add(pauseButton);
        buttons.add(unPauseButton);
        buttons.add(exitButton);
        buttons.setVisible(true);
        frame.add(buttons, BorderLayout.PAGE_END);

    }

    private void redraw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        renderer.draw(g2);
    }

    private void redraw() {
        frame.repaint();
    }

    /**
     * Runs the game loop, its runs at 60hz and is created from a tutorial I followed here
     * https://www.youtube.com/watch?v=LhUN3EKZiio&list=PLvJM9qNXoUYUDaDo_yfSKgn5dYnMmdN8B&index=2&t=335s
     */
    private void run() {
        int timerFrameCounter = 0;

        final double MUBR = 1; //Most update before render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60;
        final double TTBR = 1000000000 / TARGET_FPS; // Total time before render

        while (running) {
            while (!paused && !gameOver) {
                double now = System.nanoTime();
                int updateCount = 0;
                while (((now - lastUpdateTime) > TBU) && updateCount
                        < MUBR) {  //preform the update when its been long enough since last update
                    lastUpdateTime += TBU;
                    if (tickEvent != null) {
                        if (replaying) {
                            if (replay.isFinished() && currentTick > tickEvent.getTick() + 25) {
                                GAME_HERTZ = 60;
                                TBU = 1000000000 / GAME_HERTZ;
                                JOptionPane.showMessageDialog(frame, "The recording is finished");
                                paused = true;
                                replaying = false;
                            }
                            if (tickEvent.getTick() == currentTick) {
                                update();
                                tickEvent = replay.getNextTick();
                                if (stepByStepReplay && !replay.isFinished()) {
                                    paused = true;
                                }
                            }
                        } else if (loadingSave) {
                            if (tickEvent.getTick() == currentTick) {
                                update();
                                tickEvent = save.getNextTick();
                                if (tickEvent == null) {
                                    GAME_HERTZ = 60;
                                    TBU = 1000000000 / GAME_HERTZ;
                                    loadingSave = false;
                                }
                            }
                        } else {
                            update();
                        }
                    }
                    updateCount++;
                }

                if (now - lastUpdateTime > TBU) {
                    lastUpdateTime = now - TBU;
                }

                lastRenderTime = now;

                if (!paused) {
                    maze.tick();
                    redraw();
                    currentTick++;
                    timerFrameCounter++;

                }

                if (timerFrameCounter == 60) {
                    if (timer == 0) {
                        gameOver = true;
                        JOptionPane.showMessageDialog(frame, "Game Over, you ran out of time");
                        break;
                    }
                    timer -= 1;
                    timerLabel.setText("Time: " + timer);
                    timerFrameCounter = 0;
                }

                while (now - lastRenderTime < TTBR
                        && now - lastUpdateTime < TBU) {  // Sleep the thread to let the cpu rest
                    Thread.yield();
                    try {
                        Thread.sleep(1);
                    } catch (Exception e) {
                        System.out.println("yield error: " + e.getMessage());
                    }
                    now = System.nanoTime();
                }
            }
        }
    }

    /**
     * Updates every tick to move the player and at the tickEvent to the recording, but only if
     * something occurred that tick
     */
    private void update() {
        if (tickEvent != null) {
            maze.movePlayer(tickEvent.getMoveDir());
            r.updateRecording(tickEvent);
            tickEvent = null;
        }
    }
}
