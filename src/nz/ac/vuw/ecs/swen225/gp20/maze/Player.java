package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.List;
import java.util.logging.Logger;
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
public class Player implements Actor{

  private static final Logger LOGGER = Logger.getLogger(Player.class.getName());
  private Location location;
  private Move move;
  private Direction facing;
  private List<Item> inventory;


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
   * Check the player may access a given tile.
   *
   * @param destination the tile checked for access by player.
   * @return if the player may access a given tile.
   */
  public boolean canAccess(Tile destination) {
    return destination instanceof Accessible;
  }


//  /**
//   * Set the flags for movement and direction of travel.
//   *
//   * @param direction direction of travel
//   */
//  public void startMove(Direction direction) {
//    assert (isStationary());
//    facing = direction;
//    isMoving = true;
//  }


//  /**
//   * Set facing direction to DOWN, and switch moving flag.
//   */
//  public void endMove() {
//    facing = Direction.DOWN;
//    isMoving = false; // fixme. side affect?
//  }

//  /**
//   * @return if the player is not currently moving
//   */
//  public boolean isStationary() {
//    return !isMoving;
//  }


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

  @Override
  public Move getMove() {
    return move;
  }

  /**
   * Getter for direction.
   *
   * @return direction the player is currently facing
   */
  public Direction getFacing() {
    return facing;
  }

  @Override
  public void endMove() {
    LOGGER.info("Successfully moved to: " + location);
    move = null;
  }

  @Override
  public boolean isStationary() {
    return move == null;
  }

  public void startMove(Direction direction, Tile destination) {
    facing = direction;
    move = new Move(this, destination);
  }

  public void addToInventory(Item item) {
    if (item instanceof Key) {
      inventory.add(item);
    }
  }
}