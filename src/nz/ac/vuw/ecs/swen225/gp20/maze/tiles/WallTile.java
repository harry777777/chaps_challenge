package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;


import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

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
  public char getSymbol() {
    return 'W';
  }

  @Override
  public Location getLocation() {
    return location;
  }
}

