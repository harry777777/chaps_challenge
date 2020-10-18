package nz.ac.vuw.ecs.swen225.gp20.persistence.adapters;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.Treasure;

/**
 * Adapter to deserialise Tile objects from JSON (since tile is an interface).
 */
public class ItemAdapter implements JsonSerializer<Item>, JsonDeserializer<Item> {

  @Override
  public JsonElement serialize(Item item, Type typeOfT, JsonSerializationContext context){
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("type", item.getClass().getSimpleName());
    jsonObject.add("data", context.serialize(item));
    return jsonObject;

  }

  @Override
  public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
    JsonObject jsonObject = json.getAsJsonObject();
    JsonElement type = jsonObject.get("type");
    if(type != null){
      switch(type.getAsString()){
        case "Key":
          return context.deserialize(jsonObject.get("data"), Key.class);
        case "Treasure":
          return context.deserialize(jsonObject.get("data"), Treasure.class);
      }
    }
    return null;
  }
}
