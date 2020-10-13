package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Accessible;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

/**
 * An object's Move component. This is similar to a vector, with a direction and magnitude, but
 * limited to the enumerated directions.
 *
 * @author Arie Bates-Hermans
 */
public class Move {

  public static final int THRESHOLD = 100;

  private final Actor actor;
  private final Tile destination;
  private int distance;

  /**
   * Construct a new move with a player and destination.
   *
   * @param player      the player
   * @param destination the destination tile of the move
   */
  public Move(Player player, Tile destination) {
    this.actor = player;
    this.destination = destination;
    distance = 0;
  }

  /**
   * Increase the distance.
   */
  public void incrementDistance() {
    distance++;
  }

  /**
   * Check if the Threshold has been reached. A move may be finalised if this is true.
   *
   * @return If max offset has been reached.
   */
  public boolean isAtThreshold() {
    return distance >= THRESHOLD;
  }

  /**
   * Getter for the current distance moved.
   *
   * @return Distance travelled in move.
   */
  public int getDistance() {
    return distance;
  }

  /**
   * Finalise the Move. Admit a player to the destination tile. Set the player back to face down
   * direction.
   */
  public void executeMove() {
    assert (distance >= THRESHOLD);
    assert (destination instanceof Accessible);
    Accessible destination = (Accessible) this.destination;
    destination.admit(actor);
    actor.endMove();
  }
}

