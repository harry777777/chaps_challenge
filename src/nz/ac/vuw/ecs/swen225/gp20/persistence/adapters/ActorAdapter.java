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
import nz.ac.vuw.ecs.swen225.gp20.maze.Location;

/**
 * @author Matt/CrunchyPancakes
 * Adapter to deserialise Tile objects from JSON (since tile is an interface).
 */
public class ActorAdapter implements JsonSerializer<Actor>, JsonDeserializer<Actor> {

  @Override
  public JsonElement serialize(Actor actor, Type typeOfT, JsonSerializationContext context){
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("type", actor.getClass().getSimpleName());
    if(actor instanceof NPC){
      NPC npc = (NPC) actor;
      jsonObject.add("seed", context.serialize(npc.getSeed()));
      int[] location = {npc.getX(), npc.getY()};
      jsonObject.add("location", context.serialize(location));
    }else{
      jsonObject.add("data", context.serialize(actor));
    }
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
          long seed = context.deserialize(jsonObject.get("seed"), long.class);
          int[] location = context.deserialize(jsonObject.get("location"), int[].class);
          return new NPC(seed, new Location(location[0], location[1]));
      }
    }
    return null;
  }
}
