package nz.ac.vuw.ecs.swen225.gp20.maze.tests;




import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.WallTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;
import org.junit.jupiter.api.Test;


/**
 * Tests for basic movement in the maze.
 *
 * @author Arie Bates-Hermans
 */
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

  /**
   * Build a simple maze consisting of 9 FreeTiles surrounded by WallTiles. Assert that the correct
   * Maze.toString is produced.
   */
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

  /**
   * Player should be moved up one tile from the center location.
   */
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
    maze.movePlayer(Direction.UP);
    simulate100Ticks(maze);
    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFCFW\n"
            + "WFFFW\n"
            + "WFFFW\n"
            + "WWWWW\n";

    assertEquals(expected, actual);
  }

  /**
   * Player should be moved down one tile from the center location.
   */
  @Test
  public void movePlayerDown() {
    char[][] initialState = {
        {'W', 'W', 'W', 'W', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'F', 'C', 'F', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'W', 'W', 'W', 'W'}
    };
    Maze maze = constructMaze(initialState);
    maze.movePlayer(Direction.DOWN);
    simulate100Ticks(maze);
    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFFFW\n"
            + "WFFFW\n"
            + "WFCFW\n"
            + "WWWWW\n";

    assertEquals(expected, actual);
  }

  /**
   * Player should be moved left one tile from the center location.
   */
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
    maze.movePlayer(Direction.LEFT);
    simulate100Ticks(maze);
    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFFFW\n"
            + "WCFFW\n"
            + "WFFFW\n"
            + "WWWWW\n";

    assertEquals(expected, actual);
  }

  /**
   * Player should be moved right one tile from the center location.
   */
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
    maze.movePlayer(Direction.RIGHT);
    simulate100Ticks(maze);
    String actual = maze.toString();
    String expected =
        "WWWWW\n"
            + "WFFFW\n"
            + "WFFCW\n"
            + "WFFFW\n"
            + "WWWWW\n";

    assertEquals(expected, actual);
  }

  /**
   * Assert that a double call to set player in motion will not result in multiple moves being
   * performed.
   */
  @Test
  public void noDoubleMovement() {
    char[][] initialState = {
        {'W', 'W', 'W', 'W', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'F', 'C', 'F', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'W', 'W', 'W', 'W'}
    };
    Maze maze = constructMaze(initialState);
    maze.movePlayer(Direction.UP);
    maze.movePlayer(Direction.UP);
    simulate100Ticks(maze);
    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFCFW\n"
            + "WFFFW\n"
            + "WFFFW\n"
            + "WWWWW\n";

    assertEquals(expected, actual);
  }


  /**
   * Assert that multiple moves can be made given enough ticks have been performed between.
   */
  @Test
  public void multipleMovement() {
    char[][] initialState = {
        {'W', 'W', 'W', 'W', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'F', 'C', 'F', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'W', 'W', 'W', 'W'}
    };
    Maze maze = constructMaze(initialState);

    maze.movePlayer(Direction.UP);
    simulate100Ticks(maze);
    maze.movePlayer(Direction.LEFT);
    simulate100Ticks(maze);

    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WCFFW\n"
            + "WFFFW\n"
            + "WFFFW\n"
            + "WWWWW\n";

    assertEquals(expected, actual);
  }

  /**
   * Assert that the player may not move into a WallTile.
   */
  @Test
  public void movementIntoWall() {
    char[][] initialState = {
        {'W', 'W', 'W', 'W', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'F', 'C', 'F', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'W', 'W', 'W', 'W'}
    };
    Maze maze = constructMaze(initialState);

    maze.movePlayer(Direction.UP);
    simulate100Ticks(maze);
    maze.movePlayer(Direction.UP);
    simulate100Ticks(maze);

    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFCFW\n"
            + "WFFFW\n"
            + "WFFFW\n"
            + "WWWWW\n";

    assertEquals(expected, actual);
  }

  private void simulate100Ticks(Maze maze) {
    for (int i = 0; i < 100; i++) {
      maze.tick();
    }
  }


  private Maze constructMaze(char[][] input) {
    int width = input.length;
    int height = input[0].length;
    Tile[][] tiles = new Tile[height][width];
    Player player = null;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        char c = input[i][j];
        LOGGER.fine(String.format("(i: %d, j: %d) c = %c%n", i, j, c));
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