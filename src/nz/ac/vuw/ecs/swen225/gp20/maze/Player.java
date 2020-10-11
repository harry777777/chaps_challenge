package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Accessible;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

/**
 * The playable character 'chap'. The player may shift locations through the maze, collecting items
 * and adding them to their inventory.
 *
 * @author Arie Bates-Hermans
 */
public class Player {

  private Location location;
  private boolean isMoving = false;
  private Direction facing;

  /**
   * Constructs a new Player object at given horizontal and vertical components of location.
   *
   * @param verticalDimension   The Y location of the chap
   * @param horizontalDimension The X location of the chap
   */
  public Player(int horizontalDimension, int verticalDimension) {
    location = new Location(horizontalDimension, verticalDimension);
  }

  /**
   * Get the player's symbolic representation.
   *
   * @return a char 'C' representing the location of the player on a console output.
   */
  public char getSymbol() {
    return 'C';
  }


  /**
   * Get player's x.
   *
   * @return the x coordinate of the player's location
   */
  public int getX() {
    return location.x;
  }

  /**
   * Get player's y.
   *
   * @return the y coordinate of the player's location
   */
  public int getY() {
    return location.y;
  }


  /**
   * Check the player may access a given tile.
   *
   * @param destination the tile checked for access by player.
   * @return if the player may access a given tile.
   */
  public boolean canAccess(Tile destination) {
    return destination instanceof Accessible;
  }


  /**
   * Set the flags for movement and direction of travel.
   *
   * @param direction direction of travel
   */
  public void startMove(Direction direction) {
    assert (isStationary());
    facing = direction;
    isMoving = true;
  }


  /**
   * Set facing direction to DOWN, and switch moving flag.
   */
  public void endMove() {
    facing = Direction.DOWN;
    isMoving = false; // fixme. side affect?
  }

  /**
   * @return if the player is not currently moving
   */
  public boolean isStationary() {
    return !isMoving;
  }


  /**
   * Get the player's location.
   *
   * @return The location object of the player.
   */
  public Location getLocation() {
    return location;
  }


  /**
   * Set the location of the player.
   *
   * @param location the new location of the player.
   */
  public void setLocation(Location location) {
    this.location = location;
  }
}