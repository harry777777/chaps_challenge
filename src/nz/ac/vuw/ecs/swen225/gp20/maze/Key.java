package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Color;

public class Key implements Item {

  private Color color;

  public Key(Color color) {
    this.color = color;
  }

  @Override
  public char getSymbol() {
    return 'K';
  }
}
