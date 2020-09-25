package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.render.Renderer;

import javax.swing.*;
import java.awt.*;

public class Application {
    /**
     * @author Owen
     *
     * main method, builds the gui
     *
     */

    private static JFrame frame;
    private final Maze maze = new Maze();   //Tile[][] tiles, Player player
    private final Renderer renderer = new Renderer(maze);
    private Graphics2D g;

    public static void main(String[] args) {
        new Application();
    }
    private static void initialiseGui(){
        frame = new JFrame("Chads Challenge");
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

    }
    public void run(){
        initialiseGui();

        final double GAME_HERTZ = 60.0;
        final double TBU = 1000000000 / GAME_HERTZ;
        while(true){
            renderer.draw(g);

        }
    }
}
