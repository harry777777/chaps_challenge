package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.Random;

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

  @Override
  public boolean isStationary() {
    return move == null;
  }

  public long getSeed() {
    return seed;
  }

  @Override
  public void endMove() {
    move = null;
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
    facing = direction;
    move = new Move(direction);
  }

  Direction getNextDirection() {
    return getRandomDirection();
  }

  @Override
  public String toString() {
    return "N";
  }

}
