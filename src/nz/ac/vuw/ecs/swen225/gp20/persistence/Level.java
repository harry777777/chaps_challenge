package nz.ac.vuw.ecs.swen225.gp20.persistence;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

/**
 * Class to hold information on a level, so that it can be saved and loaded.
 */
public class Level {

  private Maze maze; // maze object representing the level
  private int timer; // timer at time of load/save

  public Level(Maze maze, int timer){
    this.maze = maze;
    this.timer = timer;
  }

  public int getTimer() {
    return timer;
  }

  public Maze getMaze() {
    return maze;
  }
}
