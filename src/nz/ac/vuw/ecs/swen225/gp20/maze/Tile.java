package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * A tile represents one unit in a 2D orthogonal grid.
 */
public interface Tile {

  /**
   * @return a character representation of the tile. Used for .toString() output.
   */
  char getSymbolicRepresentation();

  /**
   * @return The location of the tile.
   */
  Location getLocation();

}

