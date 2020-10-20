package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.Random;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

public class NPC implements Actor {

  private Random random;
  private Move move;
  private Location location;
  private Direction facing;
  private long seed;

  public NPC(long seed, Location location) {
    this.location = location;
    this.seed = seed;
    random = new Random();
    random.setSeed(seed);
    facing = getRandomDirection();
    startMove(facing);
  }

  private Direction getRandomDirection() {
    return Direction.values()[random.nextInt(Direction.values().length)];
  }


  @Override
  public Move getMove() {
    return move;
  }

  @Override
  public Direction getFacing() {
    return facing;
  }

  @Override
  public int getX() {
    return location.x;
  }

  @Override
  public int getY() {
    return location.y;
  }

  public long getSeed() {
    return seed;
  }

  @Override
  public void endMove() {
    move = null;
    startMove(getRandomDirection());
  }

  @Override
  public void setLocation(Location location) {
    this.location = location;
  }

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public void startMove(Direction direction) {
    move = new Move(direction);
  }

  @Override
  public String toString() {
    return "N";
  }

  public static void main(String[] args) {

    NPC npc = new NPC(1L, new Location(1,1));
  }
}
