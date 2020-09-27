package nz.ac.vuw.ecs.swen225.gp20.application;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.render.Renderer;

import javax.swing.*;
import java.awt.*;

public class Application {
    /**
     * @author Owen
     *
     *
     *
     */

    private static JFrame frame;
    private final Maze maze = new Maze(new Tile[1][1], new Player(1,1));   //Tile[][] tiles, Player player
    private final Renderer renderer = new Renderer(maze);
    private Graphics2D g;

    public static void main(String[] args) {

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

        final double GAME_HERTZ = 60.0;      //Used this tutorial to setup a 60hz tick rate https://www.youtube.com/watch?v=LhUN3EKZiio&list=PLvJM9qNXoUYUDaDo_yfSKgn5dYnMmdN8B&index=2&t=335s
        final double TBU = 1000000000 / GAME_HERTZ; // Time before update

        final double MUBR = 5; //Most update before render

        double lastUpdateTime = System.nanoTime();
        double lastRenderTime;

        final double TARGET_FPS = 60;
        final double TTBR = 1000000000 / TARGET_FPS; // Total time before render

        int frameCount = 0;

        while(true){
            double now = System.nanoTime(); //
            int updateCount = 0;
            while(((now - lastUpdateTime) > TBU) && updateCount < MUBR){
                update();
                input();
                lastUpdateTime += TBU;
                updateCount++;
            }

            if(now - lastUpdateTime > TBU){
                lastUpdateTime = now - TBU;
            }

           renderer.draw(g);
           lastRenderTime = now;
           frameCount++;

            System.out.println(frameCount);

            while(now - lastRenderTime < TTBR && now - lastUpdateTime < TBU){
                Thread.yield();
                try{
                    Thread.sleep(1);
                }catch (Exception e){
                    System.out.println("yield error");
                }
                now = System.nanoTime();
            }
        }
    }

    private void input() {
    }

    private void update() {
    }
}
