package nz.ac.vuw.ecs.swen225.gp20.maze;

public class ExitTile implements Accessible, Tile {

  public ExitTile(Location location) {
    this.location = location;
  }

  private Location location;

  @Override
  public void entryOperations(Maze maze, Actor player) {
    maze.setLevelComplete(true);
  }

  @Override
  public boolean isAccessibleBy(Actor actor) {
    return false;
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
