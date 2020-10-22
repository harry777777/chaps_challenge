package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import nz.ac.vuw.ecs.swen225.gp20.render.MazeInterface.InterfaceDirection;

/**
 * @author Marco McGowan-Arnold - 300390611
 * 
 * Holds the logic for drawing enemies onto the view plane
 *
 */
public class RenderEnemy {
	

	private static final Color ENEMY_COLOR = new Color(50,50,50);
	private static final Color ENEMY_COLOR_DARK = new Color(100, 100, 100);
	
	/**
	 * Draws an enemy onto the view plane
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param tileSize
	 * @param maze
	 * @param i Enemy number in the actors array
	 */
	public void draw(Graphics2D g2, double x, double y, int tileSize, MazeInterface maze, int i){
		//get relevant information from maze
		InterfaceDirection direction = maze.getActorDirection(i);
		int offset = maze.getActorOffset(i);
		
		//calculate enemy offset (offset is used for smooth movement between tiles)
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
		
		//draw the enemy passing the draw method all the information collected above
		drawFront(x+xOffset, y+yOffset, tileSize, g2);
	}
	
	private void drawFront(double x, double y, int tileSize, Graphics2D g2) {
		//draw the enemy (no other sides because of time constraints)
		double rightEdge = x+tileSize;
		g2.setStroke(new BasicStroke(1));
		g2.setColor(ENEMY_COLOR);
		g2.fill(new Rectangle2D.Double(x+tileSize/4, y+tileSize/4, tileSize/2, tileSize/2));
		g2.draw(new Rectangle2D.Double(x+tileSize/4, y+tileSize/4, tileSize/2, tileSize/2));
		g2.setColor(ENEMY_COLOR_DARK);
		g2.fill(new Rectangle2D.Double(x+tileSize/3, y+tileSize/3, tileSize/8, tileSize/8));
		g2.fill(new Rectangle2D.Double(rightEdge-tileSize/3-tileSize/8, y+tileSize/3, tileSize/8, tileSize/8));
	}
}
