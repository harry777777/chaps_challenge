package nz.ac.vuw.ecs.swen225.gp20.maze;


/**
 * Actors can freely move onto these tiles. A Free tile may hold a collectable item.
 */
public class FreeTile implements Tile, Access {

  Location location;
  Collectable collectable;

  /**
   * Construct a tile with a Collectable.
   *
   * @param location    Location of tile.
   * @param collectable Collectable held in the tile.
   */
  public FreeTile(Location location, Collectable collectable) {
    this.location = location;
    this.collectable = collectable;
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
