package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.Record.Recording;
import nz.ac.vuw.ecs.swen225.gp20.Record.TickEvent;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.render.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


/**
 * @author Owen
 *
 * Application class, this runs the game loop and creates the GUI
 *
 */

public class Application{

    private static JFrame frame;
    private final Maze maze = new Maze(new Tile[1][1], new Player(1,1));   //Tile[][] tiles, Player player
    private final Renderer renderer = new Renderer(maze);
    private Graphics g;
    private boolean moving = false;
    private Recording recordedFootage = new Recording(" ");
    private int timer = 60;
    private long TSLM = 0; // Time Since Last Move
    private boolean running;
    private JComponent drawing;

    /**
     * @author Owen
     *
     * Application class, this runs the game loop and creates the GUI
     *
     */

    public Application() {
       // Gui gui = new Gui
        initialiseGui();
        run();
    }

    /**
     * @author Owen
     *
     * Application class, this runs the game loop and creates the GUI
     *
     */

    private void initialiseGui(){
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

        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(700, 700));

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if((e.getKeyCode() == 38) && !moving){
                    maze.movePlayer(Direction.UP);
                    recordedFootage.addEvent(new TickEvent(System.currentTimeMillis(), Direction.UP),System.currentTimeMillis());
                    TSLM = System.currentTimeMillis();
                }
                if((e.getKeyCode() == 40) && !moving){
                    maze.movePlayer(Direction.DOWN);
                   // recordedFootage.update(Direction.DOWN,(System.currentTimeMillis() - TSLM));
                    TSLM = System.currentTimeMillis();
                }
                if((e.getKeyCode() == 37) && !moving){
                    maze.movePlayer(Direction.LEFT);
                  //  recordedFootage.update(Direction.LEFT,(System.currentTimeMillis() - TSLM));
                    TSLM = System.currentTimeMillis();
                }
                if((e.getKeyCode() == 39) && !moving){
                    maze.movePlayer(Direction.RIGHT);
                   // recordedFootage.update(Direction.RIGHT,(System.currentTimeMillis() - TSLM));
                    TSLM = System.currentTimeMillis();
                }
            }
        });

        // Set up drawing panel
        drawing = new JComponent() {
            @Override
            protected void paintComponent(Graphics g) {
                renderer.draw(g);
            }
        };
        drawing.setVisible(true);

    }

    /**
     * @author Owen
     *
     * Application class, this runs the game loop and creates the GUI
     *
     */
    private void run() {
        final double GAME_HERTZ = 60.0;      //Used this tutorial to setup a 60hz tick rate https://www.youtube.com/watch?v=LhUN3EKZiio&list=PLvJM9qNXoUYUDaDo_yfSKgn5dYnMmdN8B&index=2&t=335s
        final double TBU = 1000000000 / GAME_HERTZ; // Time before update

        final double MUBR = 1; //Most update before render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60;
        final double TTBR = 1000000000 / TARGET_FPS; // Total time before render

        int frameCount = 0;
        int i = 0;

        while (true) {
            double now = System.nanoTime();
            int updateCount = 0;
            while (((now - lastUpdateTime) > TBU) && updateCount < MUBR) {
                update();
                input();
                lastUpdateTime += TBU;
                updateCount++;
            }

            if (now - lastUpdateTime > TBU) {
                lastUpdateTime = now - TBU;
            }

            redraw();
            lastRenderTime = now;
            frameCount++;

            System.out.println(frameCount);

            while (now - lastRenderTime < TTBR && now - lastUpdateTime < TBU) {
                Thread.yield();
                try {
                    Thread.sleep(1);
                } catch (Exception e) {
                    System.out.println("yield error");
                }
                now = System.nanoTime();
            }
        }
    }

    private void redraw() {
    }

    /** private void run() {
        running = true;
        while(running){
            if(!(timer <= 0)) {
                update();
               try{Thread.sleep(1000);}catch (Exception e){
                    System.out.println("Error when sleeping");
               }
                renderer.draw(g);
                Graphics2D
                timer--;
            }
        }
    }*/

    private void input() {
    }

    private void update() {
        recordedFootage.addEvent(new TickEvent(System.currentTimeMillis(), null), System.currentTimeMillis());
    }
}
