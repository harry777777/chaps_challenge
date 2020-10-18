package nz.ac.vuw.ecs.swen225.gp20.maze;

import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

public interface Actor {

  Move getMove();

  void endMove();

  boolean isStationary();

  void setLocation(Location location);

  Location getLocation();

}

