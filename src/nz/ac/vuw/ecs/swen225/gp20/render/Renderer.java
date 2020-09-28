package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
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
	Inventory inventory;
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
		this.viewX = 200;
		this.viewY = 200;
		this.tileSize = 50;
		this.viewHeight = 9;
		this.viewWidth = 9;
		this.viewPort = new ViewPort();
		this.inventory = new Inventory();
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
		this.viewPort = new ViewPort();
		this.inventory = new Inventory();
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
		
	    //currently waiting on:
	    //need this class and method to be implemented in Application so I can start drawing things and testing
	    
		//access maze and draw
	    Tile[][] tiles = maze.getTiles();
	    Player player = maze.getPlayer();
	    viewPort.draw(g2, tiles, player, viewX, viewY, tileSize, viewWidth, viewHeight);
	    
	}
}
