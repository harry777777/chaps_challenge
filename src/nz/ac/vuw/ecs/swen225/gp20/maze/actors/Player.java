package nz.ac.vuw.ecs.swen225.gp20.maze.actors;

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
public class Player implements Actor {

  private final Location location;
  //  private final Item[] inventory = new Item[8];
  private Motion motion;


  /**
   * Constructs a new Player object at given a given location.
   *
   * @param verticalDimension   The Y location of the chap
   * @param horizontalDimension The X location of the chap
   */
  public Player(int horizontalDimension, int verticalDimension) {
    location = new Location(horizontalDimension, verticalDimension);
  }


  @Override
  public char getCharRep() {
    return 'C';
  }

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public boolean isValidMove(Tile destination) {
    if (destination instanceof Accessible) {
      Accessible accessibleTile = (Accessible) destination;
      return accessibleTile.isAccessibleBy(this);
    }
    return false;
  }


  public Motion getMotion() {
    return motion;
  }

//  public Item[] getInventory() {
  //todo implement
//    return inventory.clone();
//  }


  @Override
  public void setInMotion(Direction direction) {
    if (motion == null) {
      motion = new Motion(direction);
    }
  }

  @Override
  public void move() {
    switch (motion.getDirection()) {
      case UP:
        location.setVertical(location.getVertical() - 1);
        break;
      case DOWN:
        location.setVertical(location.getVertical() + 1);
        break;
      case LEFT:
        location.setHorizontal(location.getHorizontal() - 1);
        break;
      case RIGHT:
        location.setHorizontal(location.getHorizontal() + 1);
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + motion.getDirection());
    }
    motion = null;
  }


  /**
   * Advance the simulation by 1 tick.
   */
  public void tick() {
    if (motion != null) {
      motion.increaseTileOffset();
      if (motion.isAtMaxOffset()) {
        move();
      }
    }
  }

//  public void addToInventory(Item collectable) {
//    // TODO: 23/09/20 implement adding to inventory
//  }

//  public boolean hasKeyOfColor(Color color) {
//    // todo: return if holding correct key
//    return true;
//  }
}