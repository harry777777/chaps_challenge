package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

/**
 * Class for hanling JSON files.
 * Can handle a generic object, for which the type is serlialised/deserialised into JSON.
 * @param <T>
 */
public class JSONHandler<T> {

  Gson gson; // access to most gson library methods
  GsonBuilder builder; // additional gson methods

  public JSONHandler(){
    builder = new GsonBuilder();
    builder.registerTypeAdapter(Tile.class, new TileAdapter()); // allows handler to work with Tile interface
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
