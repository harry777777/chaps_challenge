package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.Record.Recorder;
import nz.ac.vuw.ecs.swen225.gp20.Record.Recording;
import nz.ac.vuw.ecs.swen225.gp20.Record.TickEvent;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.WallTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;
import nz.ac.vuw.ecs.swen225.gp20.persistence.LevelManager;
import nz.ac.vuw.ecs.swen225.gp20.render.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * @author Owen
 * <p>
 * Application class, this runs the game loop,creates the GUI and manages the key listeners
 */

public class Application {

    private static JFrame frame;
    private Maze maze;
    private final Renderer renderer;
    private final boolean moving = false;
    private final Recorder r;
    private TickEvent tickEvent;
    private final int timer = 60;
    private int currentTick = 0;
    private boolean running = true;
    private boolean paused = false;

    /**
     * @author Owen
     * <p>
     * Application class, this runs the game loop and creates the GUI
     */

    public static void main(String[] args) {
        Maze m = null;
        LevelManager l = new LevelManager();
        try {
            m = l.loadLevel("levels/level1.json");
        } catch (Exception E) {
            System.out.println("Error loading level: " + E.getMessage());
        }
        if (m != null) {
            Application A = new Application(m);
        }

    }

    /**
     * initialise Application and run the loop
     *
     * @param m the maze created by the LevelManagers loadLevel;
     */
    public Application(Maze m) {
        this.maze = m;
        renderer = new Renderer(m);
        r = new Recorder();
        initialiseGui();
        run();

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
            public void keyReleased(KeyEvent e) {
                if(!paused){
                    if ((e.getKeyCode() == 38) && !moving) {
                        maze.movePlayer(Direction.UP);
                        tickEvent = new TickEvent(currentTick, Direction.UP);
                    }
                    if ((e.getKeyCode() == 40) && !moving) {
                        maze.movePlayer(Direction.DOWN);
                        tickEvent = new TickEvent(currentTick, Direction.DOWN);
                    }
                    if ((e.getKeyCode() == 37) && !moving) {
                        maze.movePlayer(Direction.LEFT);
                        tickEvent = new TickEvent(currentTick, Direction.LEFT);
                    }
                    if ((e.getKeyCode() == 39) && !moving) {
                        maze.movePlayer(Direction.RIGHT);
                        tickEvent = new TickEvent(currentTick, Direction.RIGHT);
                    }
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

        JPanel buttons = new JPanel();
        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{r.saveRecording("Recording");}catch (Exception E){
                     System.out.println("Error saving recording:"+ E.getMessage());
                }
                System.exit(0);
            }
        });
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

        buttons.add(exitButton);
        buttons.add(pauseButton);
        buttons.add(unPauseButton);
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
     * Runs the game loop, its runs at 60hz and is created from a tutorial I followed here https://www.youtube.com/watch?v=LhUN3EKZiio&list=PLvJM9qNXoUYUDaDo_yfSKgn5dYnMmdN8B&index=2&t=335s
     */
    private void run() {
        final double GAME_HERTZ = 60;
        final double TBU = 1000000000 / GAME_HERTZ; // Time before update

        final double MUBR = 1; //Most update before render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60;
        final double TTBR = 1000000000 / TARGET_FPS; // Total time before render

        while (running) {
            while(!paused) {
                double now = System.nanoTime();
                int updateCount = 0;
                while (((now - lastUpdateTime) > TBU) && updateCount < MUBR) {  //preform the update when its been long enough since last update
                    lastUpdateTime += TBU;
                    update();
                    updateCount++;
                }

                if (now - lastUpdateTime > TBU) {
                    lastUpdateTime = now - TBU;
                }
                maze.tick();
                redraw();
                lastRenderTime = now;
                currentTick++;

                while (now - lastRenderTime < TTBR && now - lastUpdateTime < TBU) {  // Sleep the thread to let the cpu rest
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
     * Updates every tick to move the player and at the tickEvent to the recording, but only if something occurred that tick
     */
    private void update() {
        if (tickEvent != null) {
            maze.movePlayer(tickEvent.getMoveDir());
            r.updateRecording(tickEvent);
            tickEvent = null;
        }
    }
}
