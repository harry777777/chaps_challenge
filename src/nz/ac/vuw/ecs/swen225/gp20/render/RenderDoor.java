package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

/**
 * @author Marco
 *
 * Holds the logic for rendering doors
 *
 */
public class RenderDoor {
	
	
	/**
	 * Draws the door onto the graphics plane
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param tileSize
	 * @param doorColor
	 * @param unlocked
	 */
	public void draw(Graphics2D g2, double x, double y, int tileSize, Color doorColor, boolean unlocked){
		g2.setColor(doorColor);
		g2.fill(new Rectangle2D.Double(x, y, tileSize, tileSize));
	}
	
	private void drawLocked(Graphics2D g2, double x, double y, int tileSize, Color doorColor) {
		
	}
	
	private void drawUnlocked(Graphics2D g2, double x, double y, int tileSize, Color doorColor) {
		
	}
}
