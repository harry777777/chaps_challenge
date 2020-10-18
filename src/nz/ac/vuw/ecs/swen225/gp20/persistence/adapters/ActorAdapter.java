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
import nz.ac.vuw.ecs.swen225.gp20.maze.NPC;
import nz.ac.vuw.ecs.swen225.gp20.maze.Player;

/**
 * Adapter to deserialise Tile objects from JSON (since tile is an interface).
 */
public class ActorAdapter implements JsonSerializer<Actor>, JsonDeserializer<Actor> {

  @Override
  public JsonElement serialize(Actor actor, Type typeOfT, JsonSerializationContext context){
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("type", actor.getClass().getSimpleName());
    jsonObject.add("data", context.serialize(actor));
    return jsonObject;

  }

  @Override
  public Actor deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
    JsonObject jsonObject = json.getAsJsonObject();
    JsonElement type = jsonObject.get("type");
    if(type != null){
      switch(type.getAsString()){
        case "Player":
          return context.deserialize(jsonObject.get("data"), Player.class);
        case "NPC":
          return context.deserialize(jsonObject.get("data"), NPC.class);
      }
    }
    return null;
  }
}
