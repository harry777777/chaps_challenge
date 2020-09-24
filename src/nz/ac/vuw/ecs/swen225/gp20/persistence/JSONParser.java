package nz.ac.vuw.ecs.swen225.gp20.persistence;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

public class JSONParser<T> {

  public JSONParser(){
  }

  public void write(String filepath, T object) throws IOException {
    try{
      Gson gson = new Gson();
      FileWriter writer = new FileWriter(new File(filepath));
      gson.toJson(object, writer);
      writer.flush(); // good practice
    } catch (IOException | JsonIOException e) {
      e.printStackTrace();
    }
  }

  public T read(String filepath, Class<T> objectClass) throws IOException {
    try {
      Gson gson = new Gson();
      Reader reader = Files.newBufferedReader(Paths.get(filepath));
      T objectToReturn = gson.fromJson(reader, objectClass);
      reader.close();
      return objectToReturn;
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return null;
  }
}
