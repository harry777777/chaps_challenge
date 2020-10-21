package nz.ac.vuw.ecs.swen225.gp20.persistence.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import java.awt.Color;
import java.io.IOException;
import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.persistence.JSONHandler;
import nz.ac.vuw.ecs.swen225.gp20.persistence.Level;
import nz.ac.vuw.ecs.swen225.gp20.persistence.LevelManager;
import nz.ac.vuw.ecs.swen225.gp20.persistence.adapters.ActorAdapter;
import nz.ac.vuw.ecs.swen225.gp20.persistence.adapters.ColorAdapter;
import nz.ac.vuw.ecs.swen225.gp20.persistence.adapters.ItemAdapter;
import nz.ac.vuw.ecs.swen225.gp20.persistence.adapters.TileAdapter;
import org.junit.jupiter.api.Test;

/**
 * junit tests for the persistence module.
 * None have been included because it has been difficult to break down the persistence module into
 * unit tests.
 * The module has very few methods, and sometimes they call each other (so I'd be writing a test which
 * would call nearly everything in the module).
 */
public class PersistenceTests {


}
