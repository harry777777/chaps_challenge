package nz.ac.vuw.ecs.swen225.gp20.maze.actors;

import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;

/**
 * An object's Motion component. This is similar to a vector, with a direction and magnitude, but
 * limited to the enumerated directions.
 *
 * @author Arie Bates-Hermans
 */
public class Motion {

  private static final int MAX_OFFSET = 100;
  private final Direction direction;
  private int tileOffset;


  /**
   * @param direction Direction of movement.
   */
  public Motion(Direction direction) {
    this.direction = direction;
    this.tileOffset = 0;
  }


  /**
   * Increase the offset.
   */
  public void increaseTileOffset() {
    tileOffset++;
  }

  /**
   * Check if offset has been reached.
   *
   * @return If max offset has been reached.
   */
  public boolean isAtMaxOffset() {
    return tileOffset >= MAX_OFFSET;
  }

  /**
   * Getter for direction.
   *
   * @return Direction of movement.
   */
  public Direction getDirection() {
    return direction;
  }
}
