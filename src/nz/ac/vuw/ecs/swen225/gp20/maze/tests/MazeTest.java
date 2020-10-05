package nz.ac.vuw.ecs.swen225.gp20.maze.tests;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
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

    };
    String actual = new Maze(initialState).toString();

    String expected =
        "WWWWW\n"
            + "WFFFW\n"
            + "WFCFW";

    assertEquals(expected, actual);
  }

  /**
   * Player should be moved up one tile from the center location.
   */
  @Test
  public void movePlayerUp() {
    Maze maze = createStandardMaze();
    maze.movePlayer(Direction.UP);
    simulate100Ticks(maze);
    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFCFW\n"
            + "WFFFW\n"
            + "WFFFW\n"
            + "WWWWW";

    assertEquals(expected, actual);
  }

  /**
   * Player should be moved down one tile from the center location.
   */
  @Test
  public void movePlayerDown() {
    Maze maze = createStandardMaze();
    maze.movePlayer(Direction.DOWN);
    simulate100Ticks(maze);
    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFFFW\n"
            + "WFFFW\n"
            + "WFCFW\n"
            + "WWWWW";

    assertEquals(expected, actual);
  }

  /**
   * Player should be moved left one tile from the center location.
   */

  @Test
  public void movePlayerLeft() {
    Maze maze = createStandardMaze();
    maze.movePlayer(Direction.LEFT);
    simulate100Ticks(maze);
    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFFFW\n"
            + "WCFFW\n"
            + "WFFFW\n"
            + "WWWWW";

    assertEquals(expected, actual);
  }

  /**
   * Player should be moved right one tile from the center location.
   */
  @Test
  public void movePlayerRight() {
    Maze maze = createStandardMaze();
    maze.movePlayer(Direction.RIGHT);
    simulate100Ticks(maze);
    String actual = maze.toString();
    String expected =
        "WWWWW\n"
            + "WFFFW\n"
            + "WFFCW\n"
            + "WFFFW\n"
            + "WWWWW";

    assertEquals(expected, actual);
  }

  /**
   * Assert that a double call to set player in motion will not result in multiple moves being
   * performed.
   */
  @Test
  public void noDoubleMovement() {
    Maze maze = createStandardMaze();
    maze.movePlayer(Direction.UP);
    maze.movePlayer(Direction.UP);
    simulate100Ticks(maze);
    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFCFW\n"
            + "WFFFW\n"
            + "WFFFW\n"
            + "WWWWW";

    assertEquals(expected, actual);
  }


  /**
   * Assert that multiple moves can be made given enough ticks have been performed between.
   */
  @Test
  public void multipleMovement() {
    Maze maze = createStandardMaze();

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
            + "WWWWW";

    assertEquals(expected, actual);
  }

  /**
   * Assert that the player may not move into a WallTile.
   */
  @Test
  public void movementIntoWall() {
    Maze maze = createStandardMaze();

    maze.movePlayer(Direction.UP);
    simulate100Ticks(maze);


    String actual = maze.toString();

    String expected =
        "WWWWW\n"
            + "WFCFW\n"
            + "WFFFW\n"
            + "WFFFW\n"
            + "WWWWW";

    assertEquals(expected, actual);
  }

  @Test
  public void cannotMoveOutBoundsRight() {

    char[][] initState = {{'C'}};
    Maze maze = new Maze(initState);
    maze.movePlayer(Direction.RIGHT);
    simulate100Ticks(maze);
    String actual = maze.toString();

    String expected = "C";

    assertEquals(expected, actual);
  }

  @Test
  public void cannotMoveOutBoundsUp() {

    char[][] initState = {{'C'}};
    Maze maze = new Maze(initState);
    maze.movePlayer(Direction.UP);
    simulate100Ticks(maze);
    String actual = maze.toString();

    String expected = "C";

    assertEquals(expected, actual);
  }

  @Test
  public void cannotMoveOutBoundsDown() {

    char[][] initState = {{'C'}};
    Maze maze = new Maze(initState);
    maze.movePlayer(Direction.DOWN);
    simulate100Ticks(maze);
    String actual = maze.toString();

    String expected = "C";

    assertEquals(expected, actual);
  }

  @Test
  public void cannotMoveOutBoundsLeft() {

    char[][] initState = {{'C'}};
    Maze maze = new Maze(initState);
    maze.movePlayer(Direction.LEFT);
    simulate100Ticks(maze);
    String actual = maze.toString();

    String expected = "C";

    assertEquals(expected, actual);
  }

  private Maze createStandardMaze() {
    char[][] initialState = {
        {'W', 'W', 'W', 'W', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'F', 'C', 'F', 'W'},
        {'W', 'F', 'F', 'F', 'W'},
        {'W', 'W', 'W', 'W', 'W'}
    };
    return new Maze(initialState);
  }

  private void simulate100Ticks(Maze maze) {
    for (int i = 0; i < 100; i++) {
      maze.tick();
    }
  }





}