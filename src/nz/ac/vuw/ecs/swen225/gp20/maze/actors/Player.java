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
      return accessibleTile.isAccessibleBy(this);
    }
    return false;
  }


  public Motion getMotion() {
    return motion;
  }

  @Override
  public void setInMotion(Direction direction) {
    if (motion == null) {
      motion = new Motion(direction);
    }
  }

  @Override
  public void completeMove() {
    this.location = new Location(motion.getDirection(), this.location);
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
}