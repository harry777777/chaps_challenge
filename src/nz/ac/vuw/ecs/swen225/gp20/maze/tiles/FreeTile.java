package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;


import java.util.ArrayList;
import java.util.Optional;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.items.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

/**
 * A FreeTile is a Tile with no conditions for access. A FreeTile may hold a collectable Item.
 * <p>
 * Actors can freely move onto these tiles. A Free tile may hold a item item.
 */
public class FreeTile implements Tile, Accessible, Custodial {

  private final Location location;
  private final Item item;

  /**


  /**
   * Basic FreeTile constructor.
   *
   * @param location Location of the tile.
   * @param item
   */
  public FreeTile(Location location, Item item) {
    this.location = location;
    this.item = item;
  }


  @Override
  public char getCharRepresentation() {
    return 'F';
  }

  @Override
  public Location getLocation() {
    return location;
  }


  @Override
  public boolean isAccessibleBy(Actor actor) {
    return true;
  }

  @Override
  public void admit(Actor actor) {
    if (isAccessibleBy(actor)) {

    }

  }

  @Override
  public Item get() throws GameStateError {
    if (item != null) {
      return item;
    } else {
      throw new GameStateError("Tile does not hold item");
    }
  }


  @Override
  public void act(Actor actor) {
  }
}
