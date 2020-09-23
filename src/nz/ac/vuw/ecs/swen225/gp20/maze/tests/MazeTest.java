package nz.ac.vuw.ecs.swen225.gp20.maze.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import nz.ac.vuw.ecs.swen225.gp20.maze.Actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.WallTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;
import org.junit.jupiter.api.Test;


class MazeTest {

  //this is just me learning how to use the logger instead of System.out for debugging.
  // It may be removed.
  private static final Logger LOGGER = Logger.getLogger(MazeTest.class.getName());

  static {
    LOGGER.setLevel(Level.ALL);
    ConsoleHandler ch = new ConsoleHandler();
    ch.setFormatter(new SimpleFormatter());
    LOGGER.addHandler(ch);
    ch.setLevel(Level.SEVERE);
  }

  @Test
  public void buildMaze() {
    char[][] initialState = {
        {'W', 'W', 'W', 'W', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'F', 'C', 'F', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'W', 'W', 'W', 'W'}
    };
    String actual = constructMaze(initialState).toString();

    String expected =
        "WWWWW\n"
            + "WFFFW\n"
            + "WFCFW\n"
            + "WFFFW\n"
            + "WWWWW\n";

    assertEquals(expected, actual);
  }

  @Test
  public void moveChapDown() {
    char[][] initialState = {
        {'W', 'W', 'W', 'W', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'F', 'C', 'F', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'W', 'W', 'W', 'W'}
    };
    Maze maze = constructMaze(initialState);
    maze.moveChap(Direction.DOWN);
    for (int i = 0; i < 120; i++) {
      maze.tick();
    }
    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFFFW\n"
            + "WFFFW\n"
            + "WFCFW\n"
            + "WWWWW\n";

    assertEquals(expected, actual);
  }

  @Test
  public void movePlayerLeft() {
    char[][] initialState = {
        {'W', 'W', 'W', 'W', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'F', 'C', 'F', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'W', 'W', 'W', 'W'}
    };
    Maze maze = constructMaze(initialState);
    maze.moveChap(Direction.LEFT);
    for (int i = 0; i < 120; i++) {
      maze.tick();
    }
    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFFFW\n"
            + "WCFFW\n"
            + "WFFFW\n"
            + "WWWWW\n";

    assertEquals(expected, actual);
  }

  @Test
  public void movePlayerRight() {
    char[][] initialState = {
        {'W', 'W', 'W', 'W', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'F', 'C', 'F', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'W', 'W', 'W', 'W'}
    };
    Maze maze = constructMaze(initialState);
    maze.moveChap(Direction.RIGHT);
    for (int i = 0; i < 120; i++) {
      maze.tick();
    }
    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFFFW\n"
            + "WFFCW\n"
            + "WFFFW\n"
            + "WWWWW\n";

    assertEquals(expected, actual);
  }

  @Test
  public void movePlayerUp() {
    char[][] initialState = {
        {'W', 'W', 'W', 'W', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'F', 'C', 'F', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'W', 'W', 'W', 'W'}
    };
    Maze maze = constructMaze(initialState);
    maze.moveChap(Direction.UP);
    for (int i = 0; i < 120; i++) {
      maze.tick();
    }
    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFCFW\n"
            + "WFFFW\n"
            + "WFFFW\n"
            + "WWWWW\n";

    assertEquals(expected, actual);
  }


  private Maze constructMaze(char[][] input) {
    int width = input.length;
    int height = input[0].length;
    Tile[][] tiles = new Tile[height][width];
    Player player = null;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        char c = input[i][j];
        LOGGER.fine(String.format("(i: %d, j: %d) c = %c\n", i, j, c));
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