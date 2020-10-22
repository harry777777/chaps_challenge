package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * The playable character 'chap'. The player may shift locations through the maze, collecting items
 * and adding them to their inventory.
 *
 * @author Arie Bates-Hermans
 */
public class Player implements Actor {

  private static final Logger LOGGER = Logger.getLogger(Player.class.getName());
  private Location location;
  private Move move;
  private Direction facing;
  private final List<Item> inventory = new ArrayList<>();
  private int treasure = 0;

  /**
   * Constructs a new Player object at given horizontal and vertical components of location.
   *
   * @param verticalDimension   The Y location of the chap
   * @param horizontalDimension The X location of the chap
   */
  public Player(int horizontalDimension, int verticalDimension) {
    location = new Location(horizontalDimension, verticalDimension);
    facing = Direction.DOWN;
  }

  public int getTreasure() {
    return treasure;
  }

  @Override
  public String toString() {
    return "C";
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

  @Override
  public Move getMove() {
    return move;
  }

  @Override
  public Direction getFacing() {
      return facing;
  }

  @Override
  public int getX() {
    return location.x;
  }

  @Override
  public int getY() {
    return location.y;
  }

  @Override
  public boolean isStationary() {
    return move == null;
  }

  public void setFacing(Direction facing) {
    this.facing = facing;
  }

  @Override
  public void endMove() {
    LOGGER.info("Successfully moved to: " + location);
    move = null;
  }
  @Override
  public void startMove(Direction direction) {
    move = new Move(direction);
  }


  /** Add an item to inventory.
   *
   * @param item item to add to inventory
   */
  public void addToInventory(Item item) {
    if (item instanceof Key) {
      inventory.add(item);
    }
    if (item instanceof Treasure) {
      treasure += ((Treasure) item).value;
    }
  }

  /**
   * Get list of inventory items.
   *
   * @return inventory
   */
  public List<Item> getInventory() {
    return inventory;
  }

}