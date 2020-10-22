package nz.ac.vuw.ecs.swen225.gp20.maze;

public interface Actor {

  void setLocation(Location location);

  Location getLocation();

  void startMove(Direction direction);

  void endMove();

  Move getMove();

  Direction getFacing();

  int getX();

  int getY();

  boolean isStationary();
}

