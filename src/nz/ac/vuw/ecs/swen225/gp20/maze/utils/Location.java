package nz.ac.vuw.ecs.swen225.gp20.maze.utils;

import java.util.Objects;

/**
 * Location used for spatial orientation on a 2D plane.
 *
 * @author Arie Bates-Hermans
 */
public class Location {

  public final int x;
  public final int y;

  /**
   * Location from x-y integers.
   *
   * @param x x dimension
   * @param y y dimension
   */
  public Location(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Construct a new location adjacent to the given location.
   *
   * @param direction direction of travel from given location.
   * @param location  the location adjacent to which the new location will be constructed.
   */
  public Location(Direction direction, Location location) {
    int tempX = location.x;
    int tempY = location.y;

    switch (direction) {
      case UP:
        tempY = location.y - 1;
        break;
      case DOWN:
        tempY = location.y + 1;
        break;
      case LEFT:
        tempX = location.x - 1;
        break;
      case RIGHT:
        tempX = location.x + 1;
        break;
      default:
        throw new IllegalStateException("Unexpected value: " + direction);
    }
    this.y = tempY;
    this.x = tempX;
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
        return new Location(x, y - 1);
      case DOWN:
        return new Location(x, y + 1);
      case LEFT:
        return new Location(x - 1, y);
      case RIGHT:
        return new Location(x + 1, y);
      default:
        throw new IllegalStateException("Unexpected value: " + direction);
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(x, y);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj instanceof Location) {
      Location other = (Location) obj;
      return (other.x == x && other.y == y);
    }
    return false;
  }

  @Override
  public String toString() {
    return String.format("(%d,%d)", x, y);
  }
}
