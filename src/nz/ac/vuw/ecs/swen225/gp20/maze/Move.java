package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Accessible;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

/**
 * An object's Move component. This is similar to a vector, with a direction and magnitude, but
 * limited to the enumerated directions.
 *
 * @author Arie Bates-Hermans
 */
public class Move {

  public static final int THRESHOLD = 25;

  private int distance;
  private final Direction direction;
  private int speed = 1;


  public Move(Direction direction) {
    this.direction = direction;
    distance = 0;
  }

  /**
   * Increase the distance.
   */
  public void incrementDistance() {
    distance += speed;
  }

  /**
   * Check if the Threshold has been reached. A move may be finalised if this is true.
   *
   * @return If max offset has been reached.
   */
  public boolean isAtThreshold() {
    return distance >= THRESHOLD;
  }

  /**
   * Getter for the current distance moved.
   *
   * @return Distance travelled in move.
   */
  public int getDistance() {
    return distance;
  }

  public Direction getDirection() {
    return direction;
  }

}

