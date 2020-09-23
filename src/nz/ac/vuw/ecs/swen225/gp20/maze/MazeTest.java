package nz.ac.vuw.ecs.swen225.gp20.maze;

import org.junit.Test;

import java.util.Scanner;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;


class MazeTest {
    //this is just me learning how to use the logger instead of System.out for debugging. It may be removed.
    private static final Logger LOGGER = Logger.getLogger(MazeTest.class.getName());

    static {
        LOGGER.setLevel(Level.ALL);
        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new SimpleFormatter());
        LOGGER.addHandler(ch);
        ch.setLevel(Level.SEVERE);
    }

    @Test
    public void test1() {
        String initialState =
                "WWWWWWWWWWWWWWW\n" +
                        "WFFFFFFFFFFFFFW\n" +
                        "WFFFFFFFFFFFFFW\n" +
                        "WFFFFFFFFFFFFFW\n" +
                        "WFFFFFFCFFFFFFW\n" +
                        "WFFFFFFFFFFFFFW\n" +
                        "WWWWWWWWWWWWWWW\n";
        Maze maze = constructMaze(initialState, 15, 7);
        String actual = maze.toString();
        String expected =
                "WWWWWWWWWWWWWWW\n" +
                        "WFFFFFFFFFFFFFW\n" +
                        "WFFFFFFFFFFFFFW\n" +
                        "WFFFFFFFFFFFFFW\n" +
                        "WFFFFFFCFFFFFFW\n" +
                        "WFFFFFFFFFFFFFW\n" +
                        "WWWWWWWWWWWWWWW\n";

        assertEquals(expected, actual);
    }

    private Maze constructMaze(String maze, int width, int height) {
        Tile[][] tiles = new Tile[height][width];
        Scanner scanner = new Scanner(maze);
        Chap chap = null;
        for (int i = 0; i < height; i++) {
            String line = scanner.nextLine();
            for (int j = 0; j < width; j++) {
                char c = line.charAt(j);
                LOGGER.fine(String.format("(i: %d, j: %d) c = %c\n", i, j, c));
                switch (c) {
                    case 'F':
                        tiles[i][j] = new FreeTile();
                        break;
                    case 'W':
                        tiles[i][j] = new WallTile();
                        break;
                    case 'C':
                        tiles[i][j] = new FreeTile();
                        chap = new Chap(i, j);
                        break;
                }
            }
        }
        return new Maze(width, height, tiles, chap);
    }

}