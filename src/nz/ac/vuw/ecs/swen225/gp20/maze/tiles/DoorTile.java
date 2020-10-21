package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;

import java.awt.Color;
import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

public class DoorTile implements Tile, Accessible {

  public boolean isLocked() {
    return isLocked;
  }

  boolean isLocked = true;
  Color color;
  private final Location location;

  public Color getColor() {
    return color;
  }

  public DoorTile(Color color, Location location) {
    this.color = color;
    this.location = location;
  }

  @Override
  public void admit(Actor actor) {
    // FIXME: 21/10/20 remove key from inventory
    unlock();
  }


@Override
  public boolean isAccessibleBy(Actor actor) {
    return !isLocked || hasKey(actor);
  }

  private void unlock() {
    isLocked = false;
  }

  private boolean hasKey(Actor actor) {
    if (actor instanceof Player) {
      Player player = (Player) actor;
      for (Item item : player.getInventory()) {
        if (item instanceof Key) {
          Key key = (Key) item;
          System.out.println(key.getColor().equals(color));
          if (key.getColor().equals(color)) {
            return true;
          }
        }
      }
    }
    return false;
  }

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public String toString() {
    return isLocked ? "D" : "O";
  }
}
