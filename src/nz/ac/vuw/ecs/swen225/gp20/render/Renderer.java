package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

/**
 * @author Marco
 * 
 * Graphics pane class: draws the maze and object locations onto the graphics pane
 *
 */
public class Renderer {
	
	Maze maze;													//TODO: remove this
	MazeInterface mazeInterface;
	ViewPort viewPort;
	RenderInventory rInventory;
	RenderAudio rAudio;
	int viewX, viewY, tileSize, viewWidth, viewHeight;
	
	/**
	 * Initializes the renderer passing it the maze
	 * 
	 * This one gives the renderer control over sizing and placement, use it in early development
	 * 
	 * @param m
	 */
	public Renderer(Maze maze) {
		this.maze = maze; 										//TODO: remove this
		this.viewX = 70; 
		this.viewY = 70; 
		this.tileSize = 50;
		this.viewHeight = 9;
		this.viewWidth = 9;
		
		this.mazeInterface = new MazeInterface(maze);
		RenderTreasure rTreasure = new RenderTreasure();
		RenderKey rKey = new RenderKey();
		List<Actor> actors = maze.getActors();					//TODO: remove this
		this.viewPort = new ViewPort(rTreasure, rKey, actors);
		this.rInventory = new RenderInventory(rKey);
		this.rAudio = new RenderAudio();
		
		//Plays sound for start of level
	    rAudio.playEnd();
	}
	
	/**
	 * Redraws the maze and objects
	 * 
	 * @param g the graphics pane
	 */
	public void draw(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		//enable smooth edges
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	    g2.setRenderingHints(rh);
	    
		//access maze and draw
	    Tile[][] tiles = maze.getTiles();					//TODO: remove this
	    Player player = maze.getPlayer();					//TODO: remove this
	    //push matrix - viewport
		Graphics2D gTemp = (Graphics2D) g2.create();
	    viewPort.draw(g2, tiles, player, viewX, viewY, tileSize, viewWidth, viewHeight);
	    //pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
	    List<Item> inventory = player.getInventory();
	    rInventory.draw(g2, inventory, viewX+tileSize*viewWidth+4, viewY, tileSize/2);
	    
	    //sounds
	    playSound();
	    
	}
	
	private void playSound() {
		Maze.SoundNotifier sound = maze.getSound();
	    if(sound == null) {
	    	return;
	    }
	    
	    switch (sound) {
        	case PLAYER_MOVE: 
        		rAudio.playMove();
        		break;
        	case WALL_COLLISION: 
        		rAudio.playWall();
        		break;
        	case PICKUP_ITEM: 
        		rAudio.playItem();
        		break;
        	case PLAYER_DEATH: 
        		rAudio.playDeath();
        		break;
        	case DOOR_UNLOCK: 
        		rAudio.playUnlock();
        		break;
        	case END_LEVEL: 
        		rAudio.playEnd();
        		break;
	    }
	}
}
