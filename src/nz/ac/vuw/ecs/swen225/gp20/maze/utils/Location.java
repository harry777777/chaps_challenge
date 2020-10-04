package nz.ac.vuw.ecs.swen225.gp20.maze.utils;

import java.util.Objects;

/**
 * Location used for spatial orientation on a 2D plane.
 *
 * @author Arie Bates-Hermans
 */
public class Location {

  private final int horizontal;
  private final int vertical;

  /**
   * Location from x-y integers.
   *
   * @param horizontal x dimension
   * @param vertical   y dimension
   */
  public Location(int horizontal, int vertical) {
    this.horizontal = horizontal;
    this.vertical = vertical;
  }

  /**
   * Getter for x dimension.
   *
   * @return x
   */
  public int getHorizontal() {
    return horizontal;
  }


  /**
   * Get the vertical component of the location.
   *
   * @return Vertical location
   */
  public int getVertical() {
    return vertical;
  }


  /**
   * Get the Location adjacent to the current location in a given direction.
   *
   * @param direction Direction from current Location
   * @return The adjacent location
   */
  public Location getAdjacent(Direction direction) {
    switch (direction) {
      case UP:
        return new Location(horizontal, vertical - 1);
      case DOWN:
        return new Location(horizontal, vertical + 1);
      case LEFT:
        return new Location(horizontal - 1, vertical);
      case RIGHT:
        return new Location(horizontal + 1, vertical);
    }
    return null; // todo throw error?
  }


  @Override
  public int hashCode() {
    return Objects.hash(horizontal, vertical);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof Location) {
      Location other = (Location) obj;
      return (other.horizontal == horizontal && other.vertical == vertical);
    }
    return false;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
