package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * A treasure may be collected by the player.
 * All treasures of a level must be collected to end the level.
 */
public class Treasure implements Item {

  int value;

  /**
   * @param value value of treasure.
   */
  public Treasure(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "T";
  }
}
