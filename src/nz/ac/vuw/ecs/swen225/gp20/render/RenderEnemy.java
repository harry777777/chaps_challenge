package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
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
	 * @param i 
	 */
	public void draw(Graphics2D g2, double x, double y, int tileSize, Actor actor, MazeInterface mazeInterface, int i){
		Direction direction = actor.getFacing();
		//InterfaceDirection direction = mazeInterface.getActorDirection(i);
		//int offset = mazeInterface.getActorOffset(i);
		
		//calculate enemy offset
		double xOffset = 0;
		double yOffset = 0;
		if(actor.getMove() != null) { 
		//if(offset != 0) {
			int offset = actor.getMove().getDistance();
			
			double divisor = (double)(actor.getMove().THRESHOLD)/tileSize;  
			//double divisor = (double)(mazeInterface.getActorThreshold(i))/tileSize;
			
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
		
		//System.out.println(direction);
		
		drawFront(x+xOffset, y+yOffset, tileSize, g2);
	}
	
	private void drawFront(double x, double y, int tileSize, Graphics2D g2) {
		g2.setColor(BACKGROUND_COLOR);
		g2.fill(new Rectangle2D.Double(x+tileSize/4, y+tileSize/4, tileSize/2, tileSize/2));
	}
}
