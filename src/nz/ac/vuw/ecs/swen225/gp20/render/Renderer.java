package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.util.List;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

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
	
	Maze maze;
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
	public Renderer(Maze m) {
		this.maze = m;
		this.viewX = 70; //20
		this.viewY = 70; //20
		this.tileSize = 50; //50
		this.viewHeight = 9;
		this.viewWidth = 9;
		
		RenderTreasure rTreasure = new RenderTreasure();
		RenderKey rKey = new RenderKey();
		List<Actor> actors = m.getActors();
		this.viewPort = new ViewPort(rTreasure, rKey, actors);
		this.rInventory = new RenderInventory(rKey);
		this.rAudio = new RenderAudio();
		
		//TODO: get rid of this
	    rAudio.playMove();
	    rAudio.playItem();
	}
	
	/**
	 * Initializes the renderer passing it the maze and drawing parameters
	 * 
	 * @param m the simulation object
	 * @param x the x position to draw the view on the graphics pane
	 * @param y the y position to draw the view on the graphics pane
	 * @param tileSize the size of the tiles, determines overall size of view
	 * @param width the number of tiles horizontally to draw the viewport
	 * @param height the number of tiles vertically to draw the view
	 */
	public Renderer(Maze m, int x, int y, int tileSize, int width, int height) {
		this.maze = m;
		this.viewX = x;
		this.viewY = y;
		this.tileSize = tileSize;
		this.viewHeight = height;
		this.viewWidth = width;
		
		RenderTreasure rTreasure = new RenderTreasure();
		RenderKey rKey = new RenderKey();
		List<Actor> actors = m.getActors();
		this.viewPort = new ViewPort(rTreasure, rKey, actors);
		this.rInventory = new RenderInventory(rKey);
		this.rAudio = new RenderAudio();

	}
	
	/**
	 * Redraws the maze and objects
	 * 
	 * @param g the graphics pane
	 */
	public void draw(Graphics g) {
		//g.create(viewX, viewY, viewWidth*tileSize, viewHeight*tileSize);
		
		Graphics2D g2 = (Graphics2D) g;
		
		//enable smooth edges
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
		//rh.put(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
	    g2.setRenderingHints(rh);

	    
		//access maze and draw
	    Tile[][] tiles = maze.getTiles();
	    Player player = maze.getPlayer();
	    //push matrix - viewport
		Graphics2D gTemp = (Graphics2D) g2.create();
	    viewPort.draw(g2, tiles, player, viewX, viewY, tileSize, viewWidth, viewHeight);
	    //pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
	    List<Item> inventory = player.getInventory();
	    rInventory.draw(g2, inventory, viewX+tileSize*2.5, viewY+tileSize*9, tileSize/2);
	    

	    
	}
}
