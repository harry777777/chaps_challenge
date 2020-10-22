package nz.ac.vuw.ecs.swen225.gp20.maze;


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
  public void entryOperations(Maze maze, Actor actor) {
    if (actor instanceof Player) {
      if (item != null) {
        Player player = (Player) actor;
        player.addToInventory(item);
        removeItem();
      }
    }
  }

  @Override
  public boolean isAccessibleBy(Actor actor, Maze maze) {
    return true;
  }

  /**
   * Remove the Item held on the tile.
   */
  private void removeItem() {
    item = null;
  }

  /**
   * Get the item held on the tile.
   * Returns null if no item on tile.
   *
   * @return the item held on the tile, null.
   */
  public Item getItem() {
    return item;
  }

}
