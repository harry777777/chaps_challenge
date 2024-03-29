package nz.ac.vuw.ecs.swen225.gp20.maze;


/**
 * Part of a wall, actors cannot move onto those tiles.
 *
 * @author Arie Bates-Hermans
 */
public class WallTile implements Tile {

  Location location;

  /**
   * @param location Location of the WallTile
   */
  public WallTile(Location location) {
    this.location = location;
  }

  @Override
  public String toString() {
    return "W";
  }

  @Override
  public Location getLocation() {
    return location;
  }
}

