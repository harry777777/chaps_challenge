package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;

/**
 * Marks an area of the game that may be accessed by an Actor.
 *
 * @author Arie Bates-Hermans
 */
public interface Accessible {

  /**
   * Perform the required operations to move the player into the accessible area.
   *
   * @param player The Actor whom shall be granted access
   */
  void admit(Actor player);

  boolean isAccessible();

}
