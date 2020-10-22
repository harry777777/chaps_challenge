package nz.ac.vuw.ecs.swen225.gp20.maze;

public class ExitTile implements Accessible {


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
}
