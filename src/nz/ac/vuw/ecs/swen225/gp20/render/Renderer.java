package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;
import nz.ac.vuw.ecs.swen225.gp20.maze.Tile;

/**
 * @author Marco
 * 
 * Graphics pane class: draws the maze and object locations onto the graphics pane
 *
 */
public class Renderer {
	
	Maze maze;
	int viewX, viewY, tileSize, viewWidth, viewHeight;
	
	//Chap
	private static final Color CHAP_BODY = new Color(234, 222, 189);
	
	//Maze
	private static final Color FLOOR_COLOR = new Color(164, 149, 126);
	
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
	    //need access to the tiles in Maze, this is currently private and has no getter
	    //need access to the Chap in Maze so I can draw him, as with the Maze tiles above
	    //need this class and method to be implemented in Application so I can start drawing things and testing
	    
	    
	    //find player position in the Maze and player offset
	    
		//access maze and draw
	    //needs to be modified so it only access and draws the tiles around the player position as determined above
	    //this is done using the viewHeight and viewWidth values as well as the position of the player
	    Tile[][] tiles = new Tile[1][1]; //temp until I have access to the tiles in Maze
	    for(int row = 0; row < tiles.length; row++) {
	    	for(int col = 0; col < tiles[row].length; col++) {
	    		//draw contents of tiles[row][col]
	    		drawFloor(g2, viewX+row*tileSize, viewY+col*tileSize);
	    	}
	    }
	    
	    //draw the player on top of the view using information from the previous two stages
	    drawChap(g2, 0, 0); //temp
	    
	    //TODO setup the moving viewport
	}
	
	private void drawChap(Graphics2D g2, int x, int y) {
		//draw a rounded square as a temp representation of the chap
		g2.setStroke(new BasicStroke(2));
		g2.setColor(CHAP_BODY);
		g2.fill(new RoundRectangle2D.Double(x+tileSize/4, y+tileSize/4, tileSize/2, tileSize/2, 10, 10));
	}
	
	private void drawFloor(Graphics2D g2, int x, int y) {
		//draw a plane color for the floor (temp)
		g2.setColor(FLOOR_COLOR);
		g2.fillRect(x, y, tileSize, tileSize);
	}
	
}
