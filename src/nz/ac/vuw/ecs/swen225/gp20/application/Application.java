package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.Record.Recording;
import nz.ac.vuw.ecs.swen225.gp20.Record.TickEvent;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.WallTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;
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
    private Maze maze;
    private Renderer renderer;
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

   // public Application() {
    //   // Gui gui = new Gui
    //    initialiseGui();
     //   runRender();
   // }

    public static void main(String[] args) {
        Application A = new Application();
        char[][] initialState = {
                {'W', 'W', 'W', 'W', 'W'},
                {'W', 'F', 'F', 'F', 'W'},
                {'W', 'F', 'C', 'F', 'W'},
                {'W', 'F', 'F', 'F', 'W'},
                {'W', 'W', 'W', 'W', 'W'}
        };
        A.maze = A.constructMaze(initialState);
        A.renderer = new Renderer(A.maze);
        A.initialiseGui();
        A.runRender();

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
                   // recordedFootage.addEvent(new TickEvent(System.currentTimeMillis(), Direction.UP),System.currentTimeMillis());
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
                renderer.draw(frame.getGraphics());
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
    private void runRender() {
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
                lastUpdateTime += TBU;
                updateCount++;
            }

            if (now - lastUpdateTime > TBU) {
                lastUpdateTime = now - TBU;
            }

            renderer.draw(frame.getGraphics());
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
                wait(1);
                renderer.draw(g);
            }
        }
    }*/

    private void input() {
    }

    private void update() {
       // recordedFootage.getTickEvents().add(new TickEvent(System.currentTimeMillis(), null), System.currentTimeMillis());
    }

    /**
     *
     *
     * Temporary methods to create a maze for the renderer
     *
     */

    private Maze constructMaze(char[][] input) {
        int width = input.length;
        int height = input[0].length;
        Tile[][] tiles = new Tile[height][width];
        Player player = null;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                char c = input[i][j];
                switch (c) {
                    case 'F':
                        tiles[i][j] = new FreeTile(new Location(i, j));
                        break;
                    case 'W':
                        tiles[i][j] = new WallTile(new Location(i, j));
                        break;
                    case 'C':
                        tiles[i][j] = new FreeTile(new Location(i, j));
                        player = new Player(i, j);
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + c);
                }
            }
        }
        return new Maze(tiles, player);
    }
}
