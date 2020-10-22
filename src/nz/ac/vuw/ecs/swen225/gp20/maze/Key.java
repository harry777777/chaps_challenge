package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Color;

/**
 * A Key can be collected and will grant entry through a door of the same colour.
 *
 * @author Arie Bates-Hermans, 300288455.
 */
public class Key implements Item {

  private final Color color;

  /**
   * Construct key of given colour.
   *
   * @param color colour of the key
   */
  public Key(Color color) {
    this.color = color;
  }

  /**
   * Get the key's colour
   * @return key colour
   */
  public Color getColor() {
    return this.color;
  }

  @Override
  public String toString() {
    return "K";
  }
}
