package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

/**
 * @author Marco McGowan-Arnold - 300390611
 *
 * Holds the logic for rendering a hint onto the graphics plane
 *
 */
public class RenderHint {
	private static final Color FLOOR_COLOR = new Color(150,150,150);
	private static final Color WALL_COLOR_DARK = new Color(100, 100, 100);
	
	/**
	 * Draws the hint onto the graphics plane
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param tileSize
	 * @param doorColor
	 * @param hint
	 */
	public void draw(Graphics2D g2, double x, double y, int tileSize, String hint){
		g2.setColor(FLOOR_COLOR);
		g2.fill(new RoundRectangle2D.Double(x-tileSize*3, y+tileSize/10, tileSize*7, tileSize-tileSize/5, tileSize/5, tileSize/5));
		g2.setStroke(new BasicStroke((int)(tileSize/50)));
		
		Font font = new Font("Arial", Font.PLAIN, (int)(tileSize/4));
		g2.setFont(font); 
		g2.setColor(WALL_COLOR_DARK);
		g2.drawString(hint, (float)(x-tileSize*2.5), (float)(y+tileSize*0.6));
	}
}
