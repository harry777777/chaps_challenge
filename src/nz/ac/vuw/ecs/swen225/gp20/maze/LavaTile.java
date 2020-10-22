package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze.SoundNotifier;

public class LavaTile implements Accessible, Tile {

  private Location location;

  public LavaTile(Location location) {
    this.location = location;
  }

  @Override
  public void entryOperations(Maze maze, Actor actor) {
    maze.setDead();
  }

  @Override
  public boolean isAccessibleBy(Actor actor, Maze maze) {
    return actor instanceof Player;
  }

  @Override
  public void exitOperations(Maze maze) {

  }


  @Override
  public Location getLocation() {
    return location;
  }
}
