package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.util.Random;

/**
 * An NPC is a form of actor that may walk around the maze at ease,
 * but will end the game if collides with the player.
 */
public class NPC implements Actor {

  private final Random random;
  private Move move;
  private Location location;
  private Direction facing;
  private final long seed;

  /**
   * Construct NPC at a given location with a seed for random movement.
   *
   * @param seed seed for random movement generation
   * @param location location of the NPC
   */
  public NPC(long seed, Location location) {
    this.location = location;
    this.seed = seed;
    random = new Random();
    random.setSeed(seed);
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

  /** Get the NPCs random seed.
   * @return seed
   */
  public long getSeed() {
    return seed;
  }

  @Override
  public void endMove() {
    move = null;
  }

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public void setLocation(Location location) {
    this.location = location;
  }

  @Override
  public void startMove(Direction direction) {
    facing = direction;
    move = new Move(direction);
  }

  /** Get the the next direction of movement.
   *
   * @return Next direction of movement.
   */
  Direction getNextDirection() {
    System.out.println("getting new directon for actor");
    return getRandomDirection();

  }

  @Override
  public String toString() {
    return "N";
  }

}
