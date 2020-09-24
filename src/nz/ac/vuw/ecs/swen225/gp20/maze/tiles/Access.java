package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;


import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;

/**
 * Marks an area of the game that may be accessed by an Actor.
 *
 * @author Arie Bates-Hermans
 */
public interface Access {


  /**
   * Check if a given Actor can access.
   *
   * @param actor The Actor attempting to gain access
   * @return If the Actor is able to access this location.
   */
  boolean canAccess(Actor actor);


  /**
   * Perform the required operations to move an actor into the accessible area.
   *
   * @param actor The Actor whom shall be granted access to the Location
   */
  void grantAccess(Actor actor);


}
