package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Marks an area of the game that may be accessed by an Actor.
 *
 * @author Arie Bates-Hermans, 300288455.
 */
public interface Accessible {

  /**
   * Perform the required operations to move the player into the accessible area.
   *
   * @param player The Actor whom shall be granted access
   */
  void entryOperations(Maze maze, Actor actor);


  /**
   * Check to see if this Tile is accessible by a given player.
   * @param actor
   * @return
   */
  boolean isAccessibleBy(Actor actor, Maze maze);

}
