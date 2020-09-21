package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

/**
 * @author Marco
 * 
 * Graphics pane class: draws the maze and object locations onto the graphics pane
 *
 */
public class Renderer {
	
	Maze maze;
	int viewX, viewY, tileSize, viewWidth, viewHeight;
	
	/**
	 * Initializes the renderer passing it the maze
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
	 * Checks for changes in the maze and draws them
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
	}
}
