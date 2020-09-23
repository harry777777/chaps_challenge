package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.Color;
import nz.ac.vuw.ecs.swen225.gp20.maze.Actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.Actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;


/**
 * A door tile will only grant access to the player if they have the correct key to enter. Once
 * opened, all actors may pass through freely.
 */
public class DoorTile extends FreeTile {

  Color color;

  /**
   * Constructor for a door tile.
   *
   * @param location The Door's location on the maze.
   * @param color    The color of the door.
   */
  public DoorTile(Location location, Color color) {
    super(location);
  }


  @Override
  public boolean canAccess(Actor actor) {
    if (actor instanceof Player) {
      Player player = (Player) actor;
      return true; // fixme player.hasKeyOfColor(color);

    }
    return false;
  }


}
