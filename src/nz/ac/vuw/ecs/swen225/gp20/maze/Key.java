package nz.ac.vuw.ecs.swen225.gp20.maze;

import java.awt.Color;

public class Key implements Item {

  private Color color;

  public Key(Color color) {
    this.color = color;
  }

  public Color getColor(){
    return this.color;
  }

  @Override
  public String toString() {
    return "K";
  }
}
