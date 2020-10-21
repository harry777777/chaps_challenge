package nz.ac.vuw.ecs.swen225.gp20.render;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.Treasure;

import java.awt.Color;
import java.util.List;

import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.Key;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;

/**
 * @author Marco
 * 
 * The maze interface is in charge of getting any information from the maze module that is required by the renderer
 * This design means there is only one place that needs updating if significant changes are made to the maze module
 *
 */
public class MazeInterface {
	private Maze maze;
	
	/**
	 * 
	 * Constructs the maze interface passing it the maze
	 * 
	 * @param maze
	 */
	public MazeInterface(Maze maze) {
		this.maze = maze;
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
		if(maze.getTiles()[0].length <= x) {
			return null;
		}
		if(maze.getTiles().length <= y) {
			return null;
		}
		Tile current = maze.getTiles()[x][y];
		if(current instanceof FreeTile) {
			return TileType.FREE;
		}else {
			return TileType.WALL;
		}
	}
	
	/**
	 * Returns an enum for type of item on tile at x and y
	 * 
	 * @param x
	 * @param y
	 * @return the type of item on a tile
	 */
	public ItemType getItemType(int x, int y) {
		if(maze.getTiles()[0].length <= x) {
			return null;
		}
		if(maze.getTiles().length <= y) {
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
	
	public Color getKeyColor(int x, int y) {
		if(maze.getTiles()[0].length <= x) {
			return null;
		}
		if(maze.getTiles().length <= y) {
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
		return maze.getActors().size()-1;
	}
	
	/**
	 * Returns the chosen actor direction
	 * 
	 * @param i position of actor in the actors ArrayList
	 * 
	 * @return actor direction
	 */
	public InterfaceDirection getActorDirection(int i) {
		//TODO make sure player is always at position 0;
		if(i < 0) {
			return null;
		}
		if(maze.getActors().size() < i+1) {
			return null;
		}
		Direction direction = maze.getActors().get(i+1).getFacing();
		
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
		//TODO make sure player is always at position 0;
		if(maze.getActors().size() >= i+1) {
			return maze.getActors().get(i+1).getX();
		}
		return 0;
	}
	
	/**
	 * Returns the current y position of the chosen actor
	 * 
	 * @param i position of actor in the actors ArrayList
	 * 
	 * @return actor y position
	 */
	public int getActorY(int i) {
		//TODO make sure player is always at position 0;
		if(maze.getActors().size() >= i+1) {
			return maze.getActors().get(i+1).getY();
		}
		return 0;
	}
	
	/**
	 * Returns the current position offset of the chosen actor
	 * 
	 * @param i position of actor in the actors ArrayList
	 * 
	 * @return actor offset
	 */
	public int getActorOffset(int i) {
		//TODO make sure player is always at position 0;
		if(maze.getActors().size() >= i+1) {
			return maze.getActors().get(i+1).getMove().getDistance();
		}
		return 0;
	}
	
	/**
	 * Returns the current position offset of the chosen actor
	 * 
	 * @param i position of actor in the actors ArrayList
	 * 
	 * @return actor threshold
	 */
	public int getActorThreshold(int i) {
		//TODO make sure player is always at position 0;
		if(maze.getActors().size() >= i+1) {
			return maze.getActors().get(i+1).getMove().THRESHOLD;
		}
		return 0;
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
}
