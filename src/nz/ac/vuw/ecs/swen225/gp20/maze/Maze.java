package nz.ac.vuw.ecs.swen225.gp20.maze;


import nz.ac.vuw.ecs.swen225.gp20.maze.Actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

/**
 * Maze holds the state of spatial objects in the game.
 */
public class Maze {

  public final int verticalBound;
  public final int horizontalBound;
  private final Tile[][] tiles;
  private final Player player;

  /**
   * Most Constructs a new Maze with a Player Actor and Tiles.
   *
   * @param tiles  array of tile objects
   * @param player the player
   */
  public Maze(Tile[][] tiles, Player player) {

    this.verticalBound = tiles.length;
    this.horizontalBound = tiles[0].length;
    this.tiles = tiles;
    this.player = player;
  }

  /**
   * Getter for tiles array.
   *
   * @return 2d array of tiles
   */
  public Tile[][] getTiles() {
    return tiles;
  }

  /**
   * Getter for the player.
   *
   * @return The player
   */
  public Player getPlayer() {
    return player;
  }

  /**
   * Get the vertical bound.
   *
   * @return Vertical bound
   */
  public int getVerticalBound() {
    return verticalBound;
  }

  /**
   * Get the horizontal bound.
   *
   * @return Horizontal bound
   */
  public int getHorizontalBound() {
    return horizontalBound;
  }

  /**
   * Advance the maze simulation by one tick.
   */
  public void tick() {
    player.tick();
  }


  /**
   * Move the player in a specified direction.
   *
   * @param direction Direction of movement
   */
  public void moveChap(Direction direction) {
    player.setInMotion(direction);
  }

  private Tile getTileAtLoc(Location location) {
    return tiles[location.getHorizontal()][location.getVertical()];
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int x = player.getPosition().getHorizontal();
    int y = player.getPosition().getVertical();

    for (int i = 0; i < horizontalBound; i++) {
      for (int j = 0; j < verticalBound; j++) {
        if (j == x && i == y) {
          sb.append(player.getCharRep());
        } else {
          sb.append(tiles[i][j].getSymbolicRepresentation());
        }
      }
      sb.append("\n");
    }
    return sb.toString();
  }

}
