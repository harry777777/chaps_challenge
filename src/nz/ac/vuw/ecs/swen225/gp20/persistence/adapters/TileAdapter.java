package nz.ac.vuw.ecs.swen225.gp20.persistence.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import nz.ac.vuw.ecs.swen225.gp20.maze.DoorTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.WallTile;

/**
 * @author Matt/CrunchyPancakes
 * Adapter to deserialise Tile objects from JSON (since tile is an interface).
 */
public class TileAdapter implements JsonSerializer<Tile>, JsonDeserializer<Tile> {

  @Override
  public JsonElement serialize(Tile tile, Type typeOfT, JsonSerializationContext context){
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("type", tile.getClass().getSimpleName());
    jsonObject.add("data", context.serialize(tile));
    return jsonObject;

  }

  @Override
  public Tile deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
    JsonObject jsonObject = json.getAsJsonObject();
    JsonElement type = jsonObject.get("type");
    if(type != null){
      switch(type.getAsString()){
        case "FreeTile":
          return context.deserialize(jsonObject.get("data"), FreeTile.class);
        case "WallTile":
          return context.deserialize(jsonObject.get("data"), WallTile.class);
        case "DoorTile":
          return context.deserialize(jsonObject.get("data"), DoorTile.class);
      }
    }
    return null;
  }
}
