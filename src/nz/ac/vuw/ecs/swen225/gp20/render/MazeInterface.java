package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.Treasure;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.DoorTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.ExitDoor;
import nz.ac.vuw.ecs.swen225.gp20.maze.ExitTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.WallTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.Direction;

/**
 * @author Marco McGowan-Arnold - 300390611
 * 
 * The maze interface is in charge of getting any information from the maze module that is required by the renderer
 * This design means there is only one place that needs updating if significant changes are made to the maze module
 *
 */
public class MazeInterface {
	private Maze maze;
	private List<Actor> actors = new ArrayList<Actor>();
	
	
	/**
	 * 
	 * Constructs the maze interface passing it the maze
	 * 
	 * @param maze
	 */
	public MazeInterface(Maze maze) {
		this.maze = maze;
		List<Actor> tempActors = maze.getActors();
		for(Actor actor: tempActors) {
			if (!(actor instanceof Player)) {
				actors.add(actor);
			}
		}
	}
	
	//------------Maze-----------------
	
	/**
	 * Returns the width of the current level as an int
	 * 
	 * @return the width of the current level
	 */
	public int getMazeWidth() {
		return maze.getTiles()[0].length;
	}
	
	/**
	 * Returns the height of the current level as an int
	 * 
	 * @return the height of the current level
	 */
	public int getMazeHeight() {
		return maze.getTiles().length;
	}
	
	/**
	 * Returns an enum for the type of tile at x and y
	 * 
	 * @param x
	 * @param y
	 * @return the type of tile
	 */
	public TileType getTileType(int x, int y) {
		if(maze.getTiles().length <= x) {
			return null;
		}
		if(maze.getTiles()[0].length <= y) {
			return null;
		}
		Tile current = maze.getTiles()[x][y];
		if(current instanceof FreeTile) {
			return TileType.FREE;
		}else if(current instanceof WallTile){
			return TileType.WALL;
		}else if(current instanceof DoorTile){
			return TileType.DOOR;
		}else if(current instanceof ExitTile){
			return TileType.EXIT;
		}else if(current instanceof ExitDoor){
			return TileType.EXITDOOR;
		}
		return null;
	}
	
	/**
	 * Returns the color of a door at x and y
	 * 
	 * @param x
	 * @param y
	 * @return the door color
	 */
	public Color getDoorColor(int x, int y) {
		if(maze.getTiles().length <= x) {
			return null;
		}
		if(maze.getTiles()[0].length <= y) {
			return null;
		}
		Tile current = maze.getTiles()[x][y];
		if(current instanceof DoorTile){
			DoorTile currentDoor = (DoorTile) maze.getTiles()[x][y];
			return currentDoor.getColor();
		}
		return null;
	}
	
	/**
	 * Returns the locked status of a door at x and y
	 * 
	 * @param x
	 * @param y
	 * @return door locked status
	 */
	public boolean getDoorLocked(int x, int y) {
		if(maze.getTiles().length <= x) {
			return false;
		}
		if(maze.getTiles()[0].length <= y) {
			return false;
		}
		Tile current = maze.getTiles()[x][y];
		if(current instanceof DoorTile){
			DoorTile currentDoor = (DoorTile) maze.getTiles()[x][y];
			return currentDoor.isLocked();
		}
		return false;
		
	}
	
	 
	/**
	 * Returns the locked status of an exit door at x and y
	 * 
	 * @param x
	 * @param y
	 * @return exit door locked status
	 */
	public boolean getExitDoorLocked(int x, int y) {
		if(maze.getTiles().length <= x) {
			return true;
		}
		if(maze.getTiles()[0].length <= y) {
			return true;
		}
		Tile current = maze.getTiles()[x][y];
		if(current instanceof ExitDoor){
			ExitDoor currentExitDoor = (ExitDoor) maze.getTiles()[x][y];
			return currentExitDoor.isLocked();
		}
		return true;
	}
	
	/**
	 * Returns an enum for type of item on tile at x and y
	 * 
	 * @param x
	 * @param y
	 * @return the type of item on a tile
	 */
	public ItemType getItemType(int x, int y) {
		if(maze.getTiles().length <= x) {
			return null;
		}
		if(maze.getTiles()[0].length <= y) {
			return null;
		}
		Tile current = maze.getTiles()[x][y];
		if(current instanceof FreeTile) {
			FreeTile currentFree = (FreeTile) current;
			if(currentFree.getItem() instanceof Treasure) {
				return ItemType.TREASURE;
			}
			if(currentFree.getItem() instanceof Key) {
				return ItemType.KEY;
			}
		}
		return null;
	}
	
	/**
	 * Returns the color of a key at a given position
	 * 
	 * @param x
	 * @param y
	 * @return Color of key at specified position
	 */
	public Color getKeyColor(int x, int y) {
		if(maze.getTiles().length <= x) {
			return null;
		}
		if(maze.getTiles()[0].length <= y) {
			return null;
		}
		Tile current = maze.getTiles()[x][y];
		if(current instanceof FreeTile) {
			FreeTile currentFree = (FreeTile) current;
			if(currentFree.getItem() instanceof Key) {
				return ((Key)currentFree.getItem()).getColor();
			}
		}
		return null;
	}
	
	//------------Player----------------
	
	/**
	 * Returns the current player direction
	 * 
	 * @return player direction
	 */
	public InterfaceDirection getPlayerDirection() {
		Direction direction = maze.getPlayer().getFacing();
		switch (direction) {
			case LEFT: 
				return InterfaceDirection.LEFT;
			case RIGHT: 
				return InterfaceDirection.RIGHT;
			case UP: 
				return InterfaceDirection.UP;
			case DOWN: 
				return InterfaceDirection.DOWN;
			default:
				return InterfaceDirection.DOWN;
		}
	}
	
	/**
	 * Returns the current x position of the player
	 * 
	 * @return player x position
	 */
	public int getPlayerX() {
		return maze.getPlayer().getX();
	}
	
	/**
	 * Returns the current y position of the player
	 * 
	 * @return player y position
	 */
	public int getPlayerY() {
		return maze.getPlayer().getY();
	}
	
	/**
	 * Returns the current position offset of the player
	 * 
	 * @return player offset
	 */
	public int getPlayerOffset() {
		if(maze.getPlayer().getMove() == null) {
			return 0;
		}
		return maze.getPlayer().getMove().getDistance();
	}
	
	/**
	 * Returns the current position offset of the player
	 * 
	 * @return player threshold
	 */
	public int getPlayerThreshold() {
		if(maze.getPlayer().getMove() == null) {
			return 0;
		}
		return maze.getPlayer().getMove().THRESHOLD;
	}
	
	
	//-------------Actor----------------
	
	/**
	 * Returns the number of relevant actors
	 * 
	 * @return number of actors that aren't the player
	 */
	public int getNumberActors() {
		return actors.size();
	}
	
	/**
	 * Returns the chosen actor direction
	 * 
	 * @param i position of actor in the actors ArrayList
	 * 
	 * @return actor direction
	 */
	public InterfaceDirection getActorDirection(int i) {
		Direction direction = actors.get(i).getFacing();
		if(direction == null) {
			return InterfaceDirection.DOWN;
		}
		
		switch (direction) {
			case LEFT: 
				return InterfaceDirection.LEFT;
			case RIGHT: 
				return InterfaceDirection.RIGHT;
			case UP: 
				return InterfaceDirection.UP;
			case DOWN: 
				return InterfaceDirection.DOWN;
			default:
				return InterfaceDirection.DOWN;
		}
	}
	
	/**
	 * Returns the current x position of the chosen actor
	 * 
	 * @param i position of actor in the actors ArrayList
	 * 
	 * @return actor x position
	 */
	public int getActorX(int i) {
		return actors.get(i).getX();
	}
	
	/**
	 * Returns the current y position of the chosen actor
	 * 
	 * @param i position of actor in the actors ArrayList
	 * 
	 * @return actor y position
	 */
	public int getActorY(int i) {
		return actors.get(i).getY();
	}
	
	/**
	 * Returns the current position offset of the chosen actor
	 * 
	 * @param i position of actor in the actors ArrayList
	 * 
	 * @return actor offset
	 */
	public int getActorOffset(int i) {
		if(actors.get(i).getMove() == null) {
			return 0;
		}
		return actors.get(i).getMove().getDistance();
	}
	
	/**
	 * Returns the current position offset of the chosen actor
	 * 
	 * @param i position of actor in the actors ArrayList
	 * 
	 * @return actor threshold
	 */
	public int getActorThreshold(int i) {
		if(actors.get(i).getMove() == null) {
			return 0;
		}
		return actors.get(i).getMove().THRESHOLD;
	}
	
	
	//-------------Inventory----------------
	
	/**
	 * Returns the length of the player inventory
	 * 
	 * @return inventory length
	 */
	public int getInventorySize() {
		return maze.getPlayer().getInventory().size();
	}
	
	/**
	 * Returns an enum for the type of item at a given position in the inventory
	 * 
	 * @param i position of item in the items List
	 * 
	 * @return An enum for item type
	 */
	public ItemType getFromInventory(int i) {
		List<Item> inventory = maze.getPlayer().getInventory();
		if(inventory.size() < i) {
			return null;
		}
		Item item = inventory.get(i);
		if(item instanceof Key) {
			return ItemType.KEY;
		}
		return null;
	}
	
	/**
	 * Returns a color for a key at a given position in the inventory
	 * 
	 * @param i position of item in the items List
	 * 
	 * @return The color for the specified key
	 */
	public Color getKeyColorInventory(int i) {
		List<Item> inventory = maze.getPlayer().getInventory();
		if(inventory.size() < i) {
			return null;
		}
		Item item = inventory.get(i);
		if(item instanceof Key) {
			return ((Key) item).getColor();
		}
		return null;
	}
	
	/**
	 * Returns the current score (chips collected)
	 * 
	 * @return player treasure
	 */
	public int getTreasure() {
		return maze.getPlayer().getTreasure();
	}
	
	
	//--------------Sound------------------
	
	/**
	 * Returns an enum for a sound effect that should be played, or null if no sound effect should be played
	 * 
	 * @return An enum for sound to be played 
	 */
	public SoundType getSound() {
		Maze.SoundNotifier sound = maze.getSound();
	    if(sound == null) {
	    	return null;
	    }
		switch (sound) {
    		case PLAYER_MOVE: 
    			return SoundType.PLAYER_MOVE;
    		case WALL_COLLISION: 
    			return SoundType.WALL_COLLISION;
    		case PICKUP_ITEM: 
    			return SoundType.PICKUP_ITEM;
    		case PLAYER_DEATH: 
    			return SoundType.PLAYER_DEATH;
    		case DOOR_UNLOCK: 
    			return SoundType.DOOR_UNLOCK;
    		case END_LEVEL: 
    			return SoundType.END_LEVEL;
		}
		return null;
	}
	
	
	//--------------Enums------------------
	
	/**
	 * @author Marco
	 * 
	 * An interface enum for direction in the maze module
	 *
	 */
	public enum InterfaceDirection {
		  /**
		 * Facing up
		 */
		UP,
		  /**
		 * Facing down
		 */
		DOWN,
		  /**
		 * Facing left
		 */
		LEFT,
		  /**
		 * Facing right
		 */
		RIGHT,
	}
	
	/**
	 * @author Marco
	 *
	 * An enum for relevant tile types in the maze module
	 *
	 */
	public enum TileType {
		/**
		 * FreeTile
		 */
		FREE,
		/**
		 * WallTile
		 */
		WALL,
		/**
		 * DoorTile
		 */
		DOOR,
		/**
		 * ExitTile
		 */
		EXIT,
		/**
		 * ExitDoor
		 */
		EXITDOOR
	}
	
	/**
	 * @author Marco
	 * 
	 * An enum for item types in the maze module
	 *
	 */
	public enum ItemType {
		/**
		 * Key item
		 */
		KEY,
		/**
		 * Treasure item
		 */
		TREASURE,
	}
	
	/**
	 * @author Marco
	 * 
	 * An enum for the types of sound triggers in the maze module
	 *
	 */
	public enum SoundType {
		/**
		 * Player move sound
		 */
		PLAYER_MOVE,
		/**
		 * Collide with wall sound
		 */
		WALL_COLLISION,
		/**
		 * Pickup item sound
		 */
		PICKUP_ITEM,
		/**
		 * Player dies sound
		 */
		PLAYER_DEATH,
		/**
		 * Unlock a door sound
		 */
		DOOR_UNLOCK,
		/**
		 * Level complete sound
		 */
		END_LEVEL
	}
}
