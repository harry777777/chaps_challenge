package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;

public interface Custodial {

  Item get() throws GameStateError;

  void act(Actor actor);

}
