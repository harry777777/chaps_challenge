package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * @author Marco McGowan-Arnold - 300390611
 *
 * Holds the logic for rendering exit doors
 *
 */
public class RenderExitDoor {
	private static final Color WALL_COLOR_DARK = new Color(100, 100, 100);
	private static final Color CHAP_BODY_LIGHT = new Color(234, 222, 189);
	
	/**
	 * Draws the exit door onto the graphics plane
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param tileSize
	 * @param amount
	 */
	public void draw(Graphics2D g2, double x, double y, int tileSize){
		g2.setColor(WALL_COLOR_DARK);
		g2.setStroke(new BasicStroke(1));
		g2.fill(new Rectangle2D.Double(x, y, tileSize, tileSize));
		g2.draw(new Rectangle2D.Double(x, y, tileSize, tileSize));
		g2.setColor(CHAP_BODY_LIGHT );
		g2.fill(new Ellipse2D.Double(0, 0, tileSize/2, tileSize/2)); //ball
	}
}
