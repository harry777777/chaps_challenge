package nz.ac.vuw.ecs.swen225.gp20.application;

import javax.swing.*;

public class Application {
    /**
     * @author Owen
     *
     * main method, builds the gui
     *
     */

    private static JFrame frame;

    public static void main(String[] args) {
        initialiseGui();

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

    }
}
