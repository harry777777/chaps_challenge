package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;


import nz.ac.vuw.ecs.swen225.gp20.maze.Actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

/**
 * Actors can freely move onto these tiles. A Free tile may hold a item item.
 */
public class FreeTile implements Tile, Access {

  Location location;
  Item item;

  /**
   * Construct a tile with a Item.
   *
   * @param location Location of tile.
   * @param item     Item held in the tile.
   */
  public FreeTile(Location location, Item item) {
    this.location = location;
    this.item = item;
  }

  /**
   * Basic FreeTile constructor.
   *
   * @param location Location of the tile.
   */
  public FreeTile(Location location) {
    this.location = location;
  }


  @Override
  public char getSymbolicRepresentation() {
    return 'F';
  }

  @Override
  public Location getLocation() {
    return location;
  }


  @Override
  public boolean canAccess(Actor actor) {
    return true;
  }

  @Override
  public void grantAccess(Actor actor) {

  }

}
