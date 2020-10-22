package nz.ac.vuw.ecs.swen225.gp20.maze;


/**
 * A player must move onto the same location as an exit tile
 * in order to complete the current level.
 *
 * @author Arie Bates-Hermans, 300288455.
 */
public class ExitDoor implements Accessible, Tile {

  private final Location location;

  public boolean isLocked() {
    return isLocked;
  }

  private boolean isLocked = true;


  /**
   * Construct the ExitTile at a specified location.
   *
   * @param location location of the exit tile
   */
  public ExitDoor(Location location) {
    this.location = location;
  }

  @Override
  public void entryOperations(Maze maze, Actor player) {

  }

  @Override
  public boolean isAccessibleBy(Actor actor, Maze maze) {
    if (actor instanceof Player) {
      Player player = (Player) actor;
      if (player.getTreasure() == maze.computeItemCount()) {
        isLocked = false;
        return true;
      }
      return false;
    } else {
      return false;
    }
  }

  @Override
  public void exitOperations(Maze maze) {

  }


  @Override
  public String toString() {
    return "X";
  }

  @Override
  public Location getLocation() {
    return location;
  }
}
