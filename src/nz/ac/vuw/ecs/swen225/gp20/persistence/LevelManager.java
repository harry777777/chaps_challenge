package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.NPC;
import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.Treasure;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.WallTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

/**
 * Class for handling the saving and loading of levels.
 * Works with the JSONHandler class to save and load levels in JSON format.
 */
public class LevelManager {

  Gson gson; // For testing purposes to use GSON in this class.
  JSONHandler<Maze> handler; // Handles our JSON level files. Saves/loads Maze objects.

  public LevelManager(){
    gson = new Gson();
    handler = new JSONHandler<>();
  }

  /**
   * Saves a level as a JSON file.
   * Hardcoded for now to test saving a file.
   * @param filepath
   * @throws IOException
   */
  public void saveLevel(String filepath) throws IOException {
    // Maze tiles
    int verticalBound = 10;
    int horizontalBound = 10;
    Tile[][] tiles = new Tile[verticalBound][horizontalBound];
    for(int i = 0; i < verticalBound; i++){
      for(int j = 0; j < horizontalBound; j++){
        // Create a free tile at the space with nothing in it.
        tiles[i][j] = new FreeTile(new Location(i, j));
      }
    }

    // Add some variation to the level
    tiles[0][6] = new WallTile(new Location(0,6));
    tiles[1][6] = new WallTile(new Location(1,6));
    tiles[2][6] = new WallTile(new Location(2,6));
    tiles[3][6] = new WallTile(new Location(3,6));
    tiles[4][6] = new WallTile(new Location(4,6));
    tiles[5][6] = new WallTile(new Location(5,6));
    tiles[6][6] = new WallTile(new Location(6,6));
    tiles[7][6] = new WallTile(new Location(7,6));
    tiles[8][6] = new WallTile(new Location(8, 6));
    tiles[5][5] = new FreeTile(new Location(5, 5));

    // Maze player
    Player player = new Player(0,0);
    // Maze
    Maze maze = new Maze(tiles, player);
    // Writing object to JSON file
    handler.write(filepath, maze);
  }

  /**
   * Saves a level as a JSON file.
   * Takes in a char array to build a level from.
   * @param filepath
   * @param textLevel
   * @throws IOException
   */
  public void saveLevel(String filepath, char[][] textLevel) throws IOException {
    int width = textLevel.length;
    int height = textLevel[0].length;
    Tile[][] tiles = new Tile[height][width];
    Player player = null;
    List<Actor> actors = new ArrayList<>();
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        char c = textLevel[j][i];
        switch (c) {
          case 'F':
            tiles[i][j] = new FreeTile(new Location(i, j));
            break;
          case 'W':
            tiles[i][j] = new WallTile(new Location(i, j));
            break;
          case 'C':
            tiles[i][j] = new FreeTile(new Location(i, j));
            player = new Player(i,j);
            break;
          case 'N':
            tiles[i][j] = new FreeTile(new Location(i, j));
            NPC npc = new NPC(10L, new Location(i, j));
            actors.add(npc);
            break;
          case 'K':
            tiles[i][j] = new FreeTile(new Location(i, j), new Key(new Color(0,255,255)));
            break;
          case 'T':
            tiles[i][j] = new FreeTile(new Location(i, j), new Treasure(1));
            break;
          default:
            throw new IllegalStateException("Unexpected value: " + c);
        }
      }
    }

    Maze mazeToSave = new Maze(tiles, player, actors);

    // Writing object to JSON file
    handler.write(filepath, mazeToSave);
  }

  /**
   * Saves a level as a JSON file.
   * Takes in a maze to save to JSON.
   * @param filepath
   * @param level
   * @throws IOException
   */
  public void saveLevel(String filepath, Maze level) throws IOException {
    // Writing object to JSON file
    handler.write(filepath, level);
  }

  /**
   * Loads a level from a JSON file.
   * @param filepath
   * @throws IOException
   */
  public Maze loadLevel(String filepath) throws IOException {
    Maze loadedMaze = handler.read(filepath, Maze.class);
    return new Maze(loadedMaze.getTiles(), loadedMaze.getPlayer(), loadedMaze.getActors());
  }

  public static void main(String[] args) throws IOException {
    LevelManager lm = new LevelManager();
    lm.saveLevel("levels/level1.json", lm.testLevel1);
    lm.loadLevel("levels/level1.json");
  }

  // Textual level representations
  private final char[][] testLevel1 = {
            {'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W'},
            {'W', 'C', 'F', 'F', 'F', 'F', 'F', 'F', 'N', 'W'},
            {'W', 'F', 'W', 'W', 'W', 'F', 'W', 'W', 'W', 'W'},
            {'W', 'F', 'F', 'T', 'W', 'F', 'W', 'F', 'F', 'W'},
            {'W', 'F', 'F', 'F', 'W', 'F', 'W', 'F', 'F', 'W'},
            {'W', 'F', 'N', 'F', 'W', 'F', 'W', 'F', 'F', 'W'},
            {'W', 'F', 'F', 'F', 'W', 'F', 'W', 'F', 'F', 'W'},
            {'W', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'W'},
            {'W', 'K', 'F', 'F', 'F', 'F', 'F', 'F', 'F', 'W'},
            {'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W', 'W'}
        };
}
