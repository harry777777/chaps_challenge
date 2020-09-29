package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

/**
 * A tile represents one unit in a 2D orthogonal grid that forms the maze.
 *
 * @author Arie Bates-Hermans
 */
public interface Tile {

  /**
   * @return a character representation of the tile. Used for .toString() output.
   */
  char getCharRepresentation();

  /**
   * @return The location of the tile.
   */
  Location getLocation();

}

