package nz.ac.vuw.ecs.swen225.gp20.maze;

import com.google.gson.stream.JsonToken;

/**
 * A player must move onto the same location as an exit tile
 * in order to complete the current level.
 *
 * @author Arie Bates-Hermans, 300288455.
 */
public class ExitTile implements Accessible, Tile {

  private final Location location;

  /**
   * Construct the ExitTile at a specified location.
   *
   * @param location location of the exit tile
   */
  public ExitTile(Location location) {
    this.location = location;
  }

  @Override
  public void entryOperations(Maze maze, Actor player) {
    maze.setLevelComplete(true);
    System.out.println(maze);
  }

  @Override
  public boolean isAccessibleBy(Actor actor, Maze maze) {


    if (actor instanceof Player) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public void exitOperations(Maze maze) {

  }


  @Override
  public String toString() {
    return "E";
  }

  @Override
  public Location getLocation() {
    return location;
  }
}
