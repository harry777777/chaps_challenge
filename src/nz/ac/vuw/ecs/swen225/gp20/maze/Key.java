package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Color;

/**
 * A Key may be collected by the player and used to open doors. A Key's color must match it's door's
 * color.
 */
public class Key extends Collectable {

  Color color;

  /**
   * Key constructor.
   *
   * @param color The color of the key and it's door
   */
  public Key(Color color) {
    this.color = color;
  }

  @Override
  public void pickUp(Player player, Tile location) {
    super.pickUp(player, location);
  }
}
