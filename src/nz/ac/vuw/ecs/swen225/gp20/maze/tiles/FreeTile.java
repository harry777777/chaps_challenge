package nz.ac.vuw.ecs.swen225.gp20.maze.tiles;


import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

/**
 * A FreeTile is a Tile with no conditions for access. A FreeTile may hold a collectable Item.
 * <p>
 * Actors can freely move onto these tiles. A Free tile may hold a item item.
 */
public class FreeTile implements Tile, Accessible {

  private final Location location;

  /**
   * /** Basic FreeTile constructor.
   *
   * @param location Location of the tile.
   */
  public FreeTile(Location location) {
    this.location = location;
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

}
