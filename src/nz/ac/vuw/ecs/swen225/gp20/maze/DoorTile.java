package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Color;

/**
 * A DoorTile represents a location on the maze with conditional entry.
 * An actor may enter a door if they hold a key, or the door is already unlocked.
 *
 * @author Arie Bates-Hermans, 300288455
 */
public class DoorTile implements Tile, Accessible {

  private final Location location;
  boolean isLocked = true;
  Color color;
  public DoorTile(Color color, Location location) {
    this.color = color;
    this.location = location;
  }


  /** Returns true if door is currently in locked state.
   *
   * @return if the door is locked.
   */
  public boolean isLocked() {
    return isLocked;
  }

  /**
   * Get the door colour.
   *
   * @return the color of the door.
   */
  public Color getColor() {
    return color;
  }

  @Override
  public void entryOperations(Maze maze, Actor actor) {
    unlock();
  }

  @Override
  public boolean isAccessibleBy(Actor actor, Maze maze) {
    return !isLocked || hasKey(actor);
  }

  @Override
  public void exitOperations(Maze maze) {

  }


  /**
   * Change the door state to unlocked.
   */
  private void unlock() {
    isLocked = false;
  }

  /**
   * Checks if the actor is hold the key of the door's color.
   *
   * @param actor actor to check.
   * @return if actor has the key.
   */
  private boolean hasKey(Actor actor) {
    if (actor instanceof Player) {
      Player player = (Player) actor;
      for (Item item : player.getInventory()) {
        if (item instanceof Key) {
          Key key = (Key) item;
          System.out.println(key.getColor().equals(color));
          if (key.getColor().equals(color)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public String toString() {
    return isLocked ? "D" : "O";
  }
}
