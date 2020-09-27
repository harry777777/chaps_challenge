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

public class Application {

    private static JFrame frame;
    private final Maze maze = new Maze(new Tile[1][1], new Player(1,1));   //Tile[][] tiles, Player player
    private final Renderer renderer = new Renderer(maze);
    private Graphics g;
    private boolean moving = false;
    private Recording recordedFootage = new Recording(" ");
    private int timer = 60;
    private long TSLM = 0; // Time Since Last Move
    private boolean running;

    /**
     * @author Owen
     *
     * Application class, this runs the game loop and creates the GUI
     *
     */

    public Application() {
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


        frame.add();

        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                if((e.getKeyCode() == 38) && !moving){
                    maze.movePlayer(Direction.UP);
                  //  recordedFootage.getTickEvents().add(new TickEvent((System.currentTimeMillis() - TSLM), Direction.UP));
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

    }

    /**
     * @author Owen
     *
     * Application class, this runs the game loop and creates the GUI
     *
     */

    private void run() {
        running = true;
        while(running){
            if(!(timer <= 0)) {
                update();
                try{Thread.sleep(1000);}catch (Exception e){
                    System.out.println("Error when sleeping");
                }
                timer--;
            }
        }
    }

    private void input() {
    }

    private void update() {
    }
}
