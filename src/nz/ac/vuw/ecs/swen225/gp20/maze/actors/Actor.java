package nz.ac.vuw.ecs.swen225.gp20.maze.actors;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

/**
 * An Actor is a type of game element that may move between tile locations within the Maze
 *
 * @author Arie Bates-Hermans
 */
public interface Actor {

  /**
   * Used for printing the Maze overview to the console.
   *
   * @return a character representation of the actor.
   */
  char getCharRep();

  /**
   * Get the actor's location.
   *
   * @return The current Location of the actor
   */
  Location getLocation();

  /**
   * Checks if it is possible to move the tile to a given destination tile.
   *
   * @param destination destination of the move.
   * @return if it is possible to move the actor to the destination tile
   */
  boolean canMoveTo(Tile destination);

  /**
   * Getter for the Actor's current motion.
   *
   * @return the current Motion of an actor. Can return null, indicating no motion.
   */
  Motion getMotion();

  /**
   * Begin the process of moving an actor between locations. After this method has been called, each
   * subsequent tick will result in the Motion offset being incremented.
   *
   * @param direction The direction of motion.
   */
  void setInMotion(Direction direction);

  /**
   * Finalise a Motion by moving the an Actor's location coordinate. Should only be called if Motion
   * not null.
   */
  void completeMove();
}

