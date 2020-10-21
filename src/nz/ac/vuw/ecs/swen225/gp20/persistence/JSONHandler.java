package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.awt.Color;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.persistence.adapters.ActorAdapter;
import nz.ac.vuw.ecs.swen225.gp20.persistence.adapters.ColorAdapter;
import nz.ac.vuw.ecs.swen225.gp20.persistence.adapters.ItemAdapter;
import nz.ac.vuw.ecs.swen225.gp20.persistence.adapters.TileAdapter;

/**
 * @author Matt/CrunchyPancakes
 * Class for hanling JSON files.
 * Can handle a generic object, for which the type is serlialised/deserialised into JSON.
 * @param <T>
 */
public class JSONHandler<T> {

  private Gson gson; // access to most gson library methods
  private GsonBuilder builder; // additional gson methods

  public JSONHandler(){
    builder = new GsonBuilder();
    builder.registerTypeAdapter(Tile.class, new TileAdapter()); // allows handler to work with Tile interface
    builder.registerTypeAdapter(Actor.class, new ActorAdapter()); // allows handler to work with Actor interface
    builder.registerTypeAdapter(Item.class, new ItemAdapter()); // allows handler to work with Item interface
    builder.registerTypeAdapter(Color.class, new ColorAdapter()); // allows handler to work with Color, to avoid illegal reflection
    gson = builder.create();
  }

  /**
   * Writes an object of type T to a JSON file.
   * @param filepath
   * @param object
   * @throws IOException
   */
  public void write(String filepath, T object) throws IOException {
    FileWriter writer = new FileWriter(new File(filepath));
    gson.toJson(object, writer);
    writer.flush();
  }

  /**
   * Reads an object of type T from a JSON file, and returns the object.
   * @param filepath
   * @param objectClass
   * @return
   * @throws IOException
   */
  public T read(String filepath, Class<T> objectClass) throws IOException {
    Reader reader = Files.newBufferedReader(Paths.get(filepath));
    T readObject = gson.fromJson(reader, objectClass);
    reader.close();
    return readObject;
  }
}
