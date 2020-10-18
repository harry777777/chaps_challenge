package nz.ac.vuw.ecs.swen225.gp20.maze;

public class Treasure implements Item{
  int value;

  public Treasure(int value) {
    this.value = value;
  }

  @Override
  public char getSymbol() {
    return 'T';
  }
}
