package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.ExitTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.HintTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.LavaTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.NPC;
import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.Treasure;
import nz.ac.vuw.ecs.swen225.gp20.maze.DoorTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.WallTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.Location;

/**
 * @author Matt/CrunchyPancakes
 * Class for handling the saving and loading of levels.
 * Works with the JSONHandler class to save and load levels in JSON format.
 */
public class LevelManager {
  private final Color[] COLORS = {Color.pink, Color.cyan};

  private Gson gson; // For testing purposes to use GSON in this class.
  private JSONHandler<Level> handler; // Handles our JSON level files. Saves/loads Level objects.

  public LevelManager(){
    gson = new Gson();
    handler = new JSONHandler<>();
  }

  /**
   * Saves a level as a JSON file.
   * Takes in a text array to build a level from.
   * @param filepath location to save level.
   * @param textLevel a textual representation of a level.
   * @throws IOException
   */
  public void saveLevel(String filepath, String[][] textLevel) throws IOException {
    int width = textLevel.length;
    int height = textLevel[0].length;
    Tile[][] tiles = new Tile[height][width];
    Player player = null;
    Color col = null;
    List<Actor> actors = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        String s = textLevel[j][i];
        switch (s.substring(0,1)) {
          case "F":
            tiles[i][j] = new FreeTile(new Location(i, j));
            break;
          case "W":
            tiles[i][j] = new WallTile(new Location(i, j));
            break;
          case "C":
            tiles[i][j] = new FreeTile(new Location(i, j));
            player = new Player(i,j);
//            actors.add(player);
            break;
          case "N":
            tiles[i][j] = new FreeTile(new Location(i, j));
            NPC npc = new NPC(10L, new Location(i, j));
            actors.add(npc);
            break;
          case "K":
            col = COLORS[Integer.parseInt(s.substring(1,2))];
//            System.out.println(col + " key");
            tiles[i][j] = new FreeTile(new Location(i, j), new Key(col));
            break;
          case "T":
            tiles[i][j] = new FreeTile(new Location(i, j), new Treasure(1));
            break;
          case "D":
            col = COLORS[Integer.parseInt(s.substring(1,2))];
//            System.out.println(col + " door");
            tiles[i][j] = new DoorTile(col, new Location(i,j));
            break;
          case "E":
            tiles[i][j] = new ExitTile(new Location(i,j));
            break;
          case "H":
            tiles[i][j] = new HintTile("You must collect all treasure before exiting the level!", new Location(i,j));
            break;
          case "L":
            tiles[i][j] = new LavaTile(new Location(i,j));
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + s);
        }
      }
    }

    // maze
    Maze mazeToSave = new Maze(tiles, player, actors);
    // Level
    int timer = 60;
    Level level = new Level(mazeToSave, timer);
    // Writing object to JSON file
    handler.write(filepath, level);
  }

  /**
   * Saves a level as a JSON file.
   * Takes in a Level to save to JSON.
   * @param filepath location to save level.
   * @param level Level object to be saved.
   * @throws IOException
   */
  public void saveLevel(String filepath, Level level) throws IOException {
    // Writing object to JSON file
    handler.write(filepath, level);
  }

  /**
   * Loads a level from a JSON file.
   * @param filepath location to load Level from.
   * @throws IOException
   */
  public Level loadLevel(String filepath) throws IOException {
    Level loadedLevel = handler.read(filepath, Level.class);
    Maze loadedMaze = loadedLevel.getMaze();
    Maze newMaze = new Maze(loadedMaze.getTiles(), loadedMaze.getPlayer(), loadedMaze.getActors());
//    System.out.println(loadedMaze.getActors());
    int timer = loadedLevel.getTimer();
    return new Level(newMaze, timer);
  }

  public static void main(String[] args) throws IOException {
    LevelManager lm = new LevelManager();
    Level l = null;

    lm.saveLevel("levels/testLevel.json", lm.testLevel);
    l = lm.loadLevel("levels/testLevel.json");
    lm.saveLevel("levels/level1.json", lm.level1);
    lm.loadLevel("levels/level1.json");
    lm.saveLevel("levels/level2.json", lm.level2);
    lm.loadLevel("levels/level2.json");
  }

  // Textual level representations
  private final String[][] testLevel = {
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"},
            {"W", "C", "D0", "F", "N", "F", "F", "F", "N", "W"},
            {"W", "K0", "W", "W", "W", "F", "W", "W", "W", "W"},
            {"W", "D0", "F", "T", "W", "F", "W", "F", "F", "W"},
            {"W", "E", "F", "F", "W", "F", "W", "F", "F", "W"},
            {"W", "F", "N", "F", "W", "F", "W", "F", "F", "W"},
            {"W", "F", "F", "F", "W", "F", "W", "F", "F", "W"},
            {"W", "F", "F", "F", "F", "F", "F", "F", "F", "W"},
            {"W", "F", "F", "F", "F", "F", "F", "F", "F", "W"},
            {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"}
        };

  public final String[][] level1 = {
      {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"},
      {"W", "T", "L", "K0", "F", "W", "F", "E", "F", "W"},
      {"W", "F", "F", "F", "F", "W", "F", "F", "F", "W"},
      {"W", "F", "F", "W", "W", "W", "W", "D1", "W", "W"},
      {"W", "F", "W", "W", "W", "W", "F", "T", "F", "W"},
      {"W", "F", "W", "W", "K1", "D0", "T", "F", "T", "W"},
      {"W", "F", "W", "W", "W", "W", "F", "T", "F", "W"},
      {"W", "T", "F", "F", "T", "H", "T", "F", "T", "W"},
      {"W", "W", "W", "W", "F", "C", "F", "T", "F", "W"},
      {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"},
  };

  public final String[][] level2 = {
      {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"},
      {"W", "F", "C", "T", "W", "T", "F", "T", "K0", "W"},
      {"W", "F", "L", "F", "W", "F", "W", "F", "W", "W"},
      {"W", "F", "W", "F", "W", "F", "W", "F", "T", "W"},
      {"W", "F", "W", "F", "W", "F", "W", "F", "W", "W"},
      {"W", "F", "W", "F", "W", "F", "W", "F", "T", "W"},
      {"W", "F", "W", "F", "W", "F", "W", "F", "W", "W"},
      {"W", "F", "W", "F", "H", "F", "W", "T", "W", "W"},
      {"W", "N", "W", "T", "F", "T", "W", "D0", "E", "W"},
      {"W", "W", "W", "W", "W", "W", "W", "W", "W", "W"},
  };
}
