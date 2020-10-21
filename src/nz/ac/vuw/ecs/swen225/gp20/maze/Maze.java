package nz.ac.vuw.ecs.swen225.gp20.maze;


import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Accessible;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.DoorTile;
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

  private static final Logger LOGGER = Logger.getLogger(Maze.class.getName());

  public final int height;
  public final int width;
  private final Tile[][] tiles;
  private final Player player;
  private SoundNotifier sound;

  private boolean isAlive = true;
  private boolean isLevelComplete = false;

  public boolean isAlive() {
    return isAlive;
  }

  public boolean isLevelComplete() {
    return isLevelComplete;
  }

  public List<Actor> getActors() {
    return actors;
  }

  private List<Actor> actors = new ArrayList<>();

  /**
   * Most Constructs a new Maze with a Player Actor and Tiles.
   *
   * @param tiles  array of tile objects
   * @param player the player
   */
  public Maze(Tile[][] tiles, Player player) {
    this.height = tiles.length;
    this.width = tiles[0].length;
    this.tiles = Arrays.copyOf(tiles, tiles.length); // fixme: look at error on spotBugs
    this.player = player; // fixme: look at error on spotBugs
    actors.add(player);
  }

  /**
   * Most Constructs a new Maze with a Player Actor and Tiles.
   *
   * @param tiles  array of tile objects
   * @param player the player
   * @param actors the actors (i.e player and NPCS)
   */
  public Maze(Tile[][] tiles, Player player, List<Actor> actors) {
    this.height = tiles.length;
    this.width = tiles[0].length;
    this.tiles = Arrays.copyOf(tiles, tiles.length); // fixme: look at error on spotBugs
    this.player = player; // fixme: look at error on spotBugs
    this.actors = actors;
    this.actors.add(this.player);
  }

  /**
   * Construct from char array.
   * @deprecated
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
          case 'N':
            tiles[i][j] = new FreeTile(new Location(i, j));
            actors.add( new NPC(10L, new Location(i, j)));
          case 'K':
            tiles[i][j] = new FreeTile(new Location(i, j), new Key(new Color(0,255,255)));
            break;
          case 'T':
            tiles[i][j] = new FreeTile(new Location(i, j), new Treasure(1));
            break;
          case 'D':
            tiles[i][j] = new DoorTile(Color.cyan, new Location(i, j));
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + c);
        }
      }
    }
    this.player = player;
    this.tiles = tiles;
    this.height = input.length;
    this.width = input[0].length;
    actors.add(player);
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
   * Advance the maze simulation by one tick. Advance each move currently in process. If possible,
   * complete the move.
   */
  public void tick() {
    for (Actor actor : actors) {
      if (actor.getMove() != null) {
        Move move = actor.getMove();
        move.incrementDistance();
        if (move.isAtThreshold()) {
          executeMove(actor, move.getDirection());
          actor.endMove();
          if (actor instanceof NPC) {
            NPC npc = (NPC) actor;
            Direction nextDirection =  npc.getNextDirection();
            if (isValidMove(getTileAdjacentTo(actor.getLocation(), nextDirection), npc)) {
              npc.startMove(nextDirection);
            }
          }
        }
      }
    }
  }

  private void executeMove(Actor actor, Direction direction) {
    Tile destination = getTileAdjacentTo(actor.getLocation() , direction);

    if (destination != null) { // fixme this is gross
      if(destination instanceof  Accessible){
      Accessible accessible = (Accessible) destination;
      actor.setLocation(destination.getLocation());
      }
      if (isActorOnTile(destination)) {
        Actor actorOnTile = getActorOnTile(destination);
        if (actor.equals(player) || actorOnTile instanceof Player) {
          isAlive = false;
          System.out.println("endgame");
        }
      }
    }

  }

  /**
   * Move the player in a specified direction.
   *
   * @param direction Direction of movement
   */
  public void movePlayer(Direction direction) {
    LOGGER.info(String.format("Attempting to move %s", direction));
    if(player.isStationary()) {
      player.setFacing(direction);
    }
    Location currentLocation = player.getLocation();
    Tile destination = getTileAdjacentTo(currentLocation, direction);
    if (isValidMove(destination, player)) {
      Accessible accessible = (Accessible) destination;
      accessible.entryOperations(player);
      player.startMove(direction);
      sound = SoundNotifier.PLAYER_MOVE;
    }
  }


  boolean isValidMove(Tile destination, Actor actor) {
    if (actor.isStationary() && destination instanceof Accessible) {
      Accessible accessible = (Accessible) destination;
      if (accessible.isAccessibleBy(actor)) {
        return true;
      }
    }
    return false;
  }

  public Tile getTileAt(Location location) {
    return tiles[location.x][location.y];
  }

  private Tile getTileAdjacentTo(Location location, Direction direction) {
    Location adjacentLocation = location.getAdjacent(direction);
    if (isWithinBounds(adjacentLocation)) {
      return getTileAt(adjacentLocation);
    }
    return null; // todo throw error
  }

  private boolean isWithinBounds(Location location) {
    return location.x >= 0
        && location.x < width
        && location.y >= 0
        && location.y < height;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    int x = player.getLocation().x;
    int y = player.getLocation().y;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        Actor atLoc = null;
        for (Actor actor : actors) {
          if (actor.getLocation().y == i && actor.getLocation().x == j) {
            atLoc = actor;
          }
        }
        sb.append(atLoc == null ? tiles[j][i].toString() : atLoc.toString());
      }
      sb.append("\n");
    }
    sb.setLength(sb.length() - 1);
    return sb.toString();
  }

  public SoundNotifier getSound() {
    SoundNotifier sound = this.sound;
    this.sound = null;
    return sound;
  }

  public enum SoundNotifier {
    PLAYER_MOVE,
    WALL_COLLISION,
    PICKUP_ITEM,
    PLAYER_DEATH,
    DOOR_UNLOCK,
    END_LEVEL,
  }

  Actor getActorOnTile(Tile tile) {
    for (Actor actor : actors) {
      if (actor.getLocation().equals(tile.getLocation())) {
        return actor;
      }
    }
    return null;
  }

  boolean isActorOnTile(Tile tile) {
    for (Actor actor : actors) {
      if (actor.getLocation().equals(tile.getLocation())) {
        return true;
      }
    }
    return false;
  }
}
