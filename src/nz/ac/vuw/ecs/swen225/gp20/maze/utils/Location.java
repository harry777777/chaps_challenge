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
   * Construct a new location adjacent to the given location.
   *
   * @param direction direction of travel from given location.
   * @param location  the location adjacent to which the new location will be constructed.
   */
  public Location(Direction direction, Location location) {
    int tempHorizontal = location.horizontal;
    int tempVertical = location.vertical;

    switch (direction) {
      case UP:
        tempVertical = location.vertical - 1;
        break;
      case DOWN:
        tempVertical = location.vertical + 1;
        break;
      case LEFT:
        tempHorizontal = location.horizontal - 1;
        break;
      case RIGHT:
        tempHorizontal = location.horizontal + 1;
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + direction);
    }
    this.vertical = tempVertical;
    this.horizontal = tempHorizontal;
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
    return String.format("(%d,%d)", horizontal, vertical);
  }
}
