package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import nz.ac.vuw.ecs.swen225.gp20.render.MazeInterface.InterfaceDirection;

/**
 * @author Marco
 * 
 * Holds the logic for drawing enemies onto the view plane
 *
 */
public class RenderEnemy {
	

	private static final Color BACKGROUND_COLOR = new Color(0,180,0);
	
	/**
	 * Draws an enemy onto the view plane
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param tileSize
	 * @param mazeInterface 
	 * @param i Enemy number in the actors array
	 */
	public void draw(Graphics2D g2, double x, double y, int tileSize, MazeInterface maze, int i){
		InterfaceDirection direction = maze.getActorDirection(i);
		int offset = maze.getActorOffset(i);
		
		//calculate enemy offset
		double xOffset = 0;
		double yOffset = 0;
		if(offset != 0) {
 
			double divisor = (double)(maze.getActorThreshold(i))/tileSize;
			
			switch (direction) {
				case LEFT: 
					xOffset = -(offset/divisor);
					break;
				case RIGHT: 
					xOffset = (offset/divisor);
					break;
				case UP: 
					yOffset = -(offset/divisor);
					break;
				case DOWN: 
					yOffset = (offset/divisor);
					break;
			}
		}
		
		drawFront(x+xOffset, y+yOffset, tileSize, g2);
	}
	
	private void drawFront(double x, double y, int tileSize, Graphics2D g2) {
		g2.setColor(BACKGROUND_COLOR);
		g2.fill(new Rectangle2D.Double(x+tileSize/4, y+tileSize/4, tileSize/2, tileSize/2));
	}
}
