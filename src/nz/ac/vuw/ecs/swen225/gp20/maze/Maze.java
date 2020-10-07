package nz.ac.vuw.ecs.swen225.gp20.maze;


import java.util.Arrays;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.WallTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

/**
 * Maze holds the state of spatial objects in the game.
 *
 * @author Arie Bates-Hermans
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
    this.tiles = tiles; // fixme: look at error on spotBugs
    this.player = player; // fixme: look at error on spotBugs
  }


  /**
   * Construct from char array.
   *
   * @param input char[][] of symbolic representations of the maze.
   */
  public Maze(char[][] input) {
    int width = input.length;
    int height = input[0].length;
    Tile[][] tiles = new Tile[height][width];
    Player player = null;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        char c = input[j][i];
        switch (c) {
          case 'F':
            tiles[i][j] = new FreeTile(new Location(i, j));
            break;
          case 'W':
            tiles[i][j] = new WallTile(new Location(i, j));
            break;
          case 'C':
            tiles[i][j] = new FreeTile(new Location(i, j));
            player = new Player(i, j);
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + c);
        }
      }
    }
    this.player = player;
    this.tiles = tiles;
    this.verticalBound = input.length;
    this.horizontalBound = input[0].length;
  }

  /**
   * Getter for tiles array.
   *
   * @return 2d array of tiles
   */
  public Tile[][] getTiles() {
    return Arrays.copyOf(tiles, tiles.length);
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
  public void movePlayer(Direction direction) {
    System.out.printf("Attempting to move player %s%n", direction);
    Location currentLocation = player.getLocation();
    Tile destination = getTileAdjacentTo(currentLocation, direction);

    if (player.canMoveTo(destination)) {
      player.setInMotion(direction);
    }
    System.out.println(this.toString());
  }

  private Tile getTileAt(Location location) {
    return tiles[location.getHorizontal()][location.getVertical()];
  }

  private Tile getTileAdjacentTo(Location from, Direction direction) {
    Location adjacentLocation = from.getAdjacent(direction);
    if (isWithinBounds(adjacentLocation)) {
      return getTileAt(adjacentLocation);
    }
    return null;// todo throw error
  }

  private boolean isWithinBounds(Location location) {
    return location.getHorizontal() >= 0 &&
        location.getHorizontal() < horizontalBound &&
        location.getVertical() >= 0 &&
        location.getVertical() < verticalBound;
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int x = player.getLocation().getHorizontal();
    int y = player.getLocation().getVertical();

    for (int i = 0; i < verticalBound; i++) {
      for (int j = 0; j < horizontalBound; j++) {
        if (j == x && i == y) {
          sb.append(player.getCharRep());
        } else {
          sb.append(tiles[j][i].getCharRepresentation());
        }
      }
      sb.append("\n");
    }
    sb.setLength(sb.length() - 1);
    return sb.toString();
  }

}
