package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

import java.io.IOException;

public class LevelManager {

  Gson gson;
  GsonBuilder builder;

  public LevelManager(){
    builder = new GsonBuilder();
    gson = builder.create();
  }

  /**
   * Saves a level as a JSON file.
   * Hardcoded for now to test saving a file.
   */
  public void saveLevel() throws IOException {

    // Maze tiles
    int verticalBound = 10;
    int horizontalBound = 10;
    Tile[][] tiles = new Tile[verticalBound][horizontalBound];
    for(int i = 0; i < verticalBound; i++){
      for(int j = 0; j < horizontalBound; j++){
        // Create a free tile at the space with nothing in it.
        tiles[i][j] = new FreeTile(new Location(i,j), null);
      }
    }
    // Maze player
    Player player = new Player(0,0);
    // Maze
    Maze maze = new Maze(tiles, player);

    // Writing object to JSON file
    JSONParser<Maze> parser = new JSONParser<>();
    parser.write("levels/level1.json", maze);
  }

  public void loadLevel() throws IOException {
    JSONParser<Maze> parser = new JSONParser<>();
    Maze JSONmaze = parser.read("levels/level1.json", Maze.class);
    System.out.println(JSONmaze.horizontalBound);
  }

  public static void main(String[] args) throws IOException {
    LevelManager lm = new LevelManager();
//    lm.saveLevel();
    lm.loadLevel();
  }
}
