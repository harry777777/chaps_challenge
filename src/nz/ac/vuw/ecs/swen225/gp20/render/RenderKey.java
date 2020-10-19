package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

/**
 * @author Marco
 *
 * Holds the logic for drawing keys onto the view plane
 *
 */
public class RenderKey {
	
	
	/**
	 * 
	 * Draws an instance of the key item on the graphics plane
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param tileSize
	 * @param color
	 */
	public void draw(Graphics2D g2, double x, double y, int tileSize, Color keyColor){
		g2.setColor(keyColor);
		g2.fill(new RoundRectangle2D.Double(x+tileSize/4, y+tileSize/4, tileSize/2, tileSize/2, tileSize/5, tileSize/5));
	}
}
