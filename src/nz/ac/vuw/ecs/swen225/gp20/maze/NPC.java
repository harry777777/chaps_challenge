package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.Random;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

public class NPC implements Actor {

  private Random random;
  private Move move;
  private Location location;
  private Direction facing;

  public NPC(long seed, Location location) {
    this.location = location;
    random = new Random();
    random.setSeed(seed);
    facing = Direction.values()[random.nextInt(Direction.values().length)];
  }


  @Override
  public Move getMove() {
    return move;
  }

  @Override
  public void endMove() {
    move = null;
  }

  @Override
  public boolean isStationary() {
    return move == null;
  }

  @Override
  public void setLocation(Location location) {

  }


  public static void main(String[] args) {
    NPC npc = new NPC(1L, new Location(1,1));
  }
}
