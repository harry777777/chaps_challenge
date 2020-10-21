package nz.ac.vuw.ecs.swen225.gp20.persistence.adapters;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.awt.Color;
import java.lang.reflect.Type;
import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.NPC;
import nz.ac.vuw.ecs.swen225.gp20.maze.Player;

/**
 * @author Matt/CrunchyPancakes
 * Adapter to deserialise Color objects.
 */
public class ColorAdapter implements JsonSerializer<Color>, JsonDeserializer<Color> {

  @Override
  public JsonElement serialize(Color color, Type typeOfT, JsonSerializationContext context){
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("type", color.getClass().getSimpleName());
    int[] colorRGB = {color.getRed(), color.getGreen(), color.getBlue()};
    jsonObject.add("data", context.serialize(colorRGB));
    return jsonObject;

  }

  @Override
  public Color deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException{
    JsonObject jsonObject = json.getAsJsonObject();
    JsonElement type = jsonObject.get("type");
    if(type != null){
      // get the array of ints, turn into array, and access each value to make the color.
      JsonElement colorRGB = jsonObject.get("data");
      JsonArray array = colorRGB.getAsJsonArray();
      return new Color(array.get(0).getAsInt(), array.get(1).getAsInt(), array.get(2).getAsInt());
    }
    return null;
  }
}
