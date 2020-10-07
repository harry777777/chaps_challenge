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

  private Location location;
  //  private final Item[] inventory = new Item[8];
  private Motion motion;


  /**
   * Constructs a new Player object at given horizontal and vertical components of location.
   *
   * @param verticalDimension   The Y location of the chap
   * @param horizontalDimension The X location of the chap
   */
  public Player(int horizontalDimension, int verticalDimension) {
    location = new Location(horizontalDimension, verticalDimension);
  }


  public Player(Location location) {
    this.location = location;
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
  public boolean canMoveTo(Tile destination) {
    if (destination instanceof Accessible) {
      Accessible accessibleTile = (Accessible) destination;
      System.out.printf("Player can move from %s to %s%n", location, destination.getLocation());
      return accessibleTile.isAccessibleBy(this);
    }
    System.out.println("Player cannot move");
    return false;
  }


  public Motion getMotion() {
    return motion;
  }

  @Override
  public void setInMotion(Direction direction) {
    if (motion == null) {
      motion = new Motion(direction);
    } else {
      System.out.println("Player already in motion");
    }
  }

  @Override
  public void completeMove() {
    this.location = new Location(motion.getDirection(), this.location);
    System.out.println("Player moved " + motion.getDirection());
    motion = null;
  }


  /**
   * Advance the simulation by 1 tick.
   */
  public void tick() {
    if (motion != null) {
      motion.increaseTileOffset();
      if (motion.isAtMaxOffset()) {
        completeMove();
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