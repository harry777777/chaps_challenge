package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

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
  void entryOperations(Maze maze, Actor actor);

  boolean isAccessibleBy(Actor actor);

}
