package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * An Actor is a game component that may transition between locations on the maze.
 * Actors are not held in the same data structure as Tiles and collisions should be
 * tested for independently.
 *
 * @author Arie Bates-Hermans, 300288455.
 */
public interface Actor {

  /**
   * Get the Location object of the actor.
   *
   * @return the location of the actor
   */
  Location getLocation();

  /**
   * Set the location object of the actor.
   *
   * @param location location to be assigned to the player.
   */
  void setLocation(Location location);

  /**
   * Start the process of moving the actor.
   * This method should perform all the required actions that an actor
   * must take prior to moving.
   *
   * @param direction direction of travel
   */
  void startMove(Direction direction);

  /**
   * Perform the neccesary actions to complete a move.
   */
  void endMove();

  /**
   * Get the Move object of the actor.
   * If returns null the actor is not currently moving.
   *
   * @return the actor's move object, null if stationary.
   */
  Move getMove();

  /**
   * Get the direction the actor is currently facing.
   *
   * @return the direction the actor is facing.
   */
  Direction getFacing();

  /**
   * Convenience method for getting horizontal attribute of actor's location.
   *
   * @return x component of actor's location.
   */
  int getX();

  /**
   * Convenience method for getting vertical attribute of actor's location.
   *
   * @return y component of actor's location.
   */
  int getY();

  /**
   * Check if actor is not currently moving.
   *
   * @return true if actor is not moving.
   */
  boolean isStationary();
}

