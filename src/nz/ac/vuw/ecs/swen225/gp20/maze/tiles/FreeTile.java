package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;


import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

/**
 * A FreeTile is a Tile with no conditions for access. A FreeTile may hold a collectable Item.
 * Actors can freely move onto these tiles. A Free tile may hold a item item.
 */
public class FreeTile implements Tile, Accessible {

  private final Location location;
  private Item item;

  public FreeTile(Location location, Item item) {
    this.location = location;
    this.item = item;
  }

  /**
   * Construct a FreeTile at a given location.
   *
   * @param location Location of the tile.
   */
  public FreeTile(Location location) {
    this.location = location;
  }

  @Override
  public String toString() {
    if (item != null) {
      return item.toString();
    }
    return "F";
  }

  @Override
  public Location getLocation() {
    return location;
  }

  @Override
  public void admit(Actor actor) {
    if (actor instanceof Player) {
      if (item != null) {
        Player player = (Player) actor;
        player.addToInventory(item);
        removeItem();
      }
    }
  }

  private void removeItem() {
    item = null;
  }

  public Item getItem() {
    return item;
  }

}
