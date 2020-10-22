package nz.ac.vuw.ecs.swen225.gp20.maze;

public class HintTile implements Tile, Accessible {
  private final String hint;
  private final Location location;

  public HintTile(String hint, Location location) {
    this.hint = hint;
    this.location = location;
  }

  @Override
  public void entryOperations(Maze maze, Actor actor) {
    if (actor instanceof Player) {
      maze.setMessage(hint);
    }
  }

  @Override
  public boolean isAccessibleBy(Actor actor, Maze maze) {
    return true;
  }

  @Override
  public Location getLocation() {
    return null;
  }
}
