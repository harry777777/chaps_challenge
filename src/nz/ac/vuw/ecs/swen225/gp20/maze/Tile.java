package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.maze.Location;

/**
 * A tile represents one unit in a 2D orthogonal grid that forms the maze.
 *
 * @author Arie Bates-Hermans
 */
public interface Tile {

  /**
   * @return The location of the tile.
   */
  Location getLocation();

}

