package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Graphics;
import java.awt.Graphics2D;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;

/**
 * @author Marco
 * 
 * Graphics pane class: draws the gameplay onto the graphics pane
 *
 */
public class Renderer {
	
	Maze maze;
	//previous player location
	private boolean busy = false; //tells the application if it can make changes to the maze
	
	/**
	 * Initializes the renderer passing it the maze
	 * 
	 * @param m
	 */
	public Renderer(Maze m) {
		this.maze = m;
	}
	
	/**
	 * @return player animation status
	 */
	public boolean getStatus() {
		return busy;
	}

	
	/**
	 * Checks for changes in the maze and draws them
	 * 
	 * @param g
	 */
	public void draw(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		
		//access maze and check for player position change
	}
}
