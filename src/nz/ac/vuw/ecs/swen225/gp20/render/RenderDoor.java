package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * @author Marco
 *
 * Holds the logic for rendering doors
 *
 */
public class RenderDoor {

	private static final Color WALL_COLOR_DARK = new Color(100, 100, 100);
	private static final Color WALL_COLOR = new Color(120, 120, 120);
	
	private double ballDiam;
	private double lazerDiam;
	
	/**
	 * Draws the door onto the graphics plane
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param tileSize
	 * @param doorColor
	 * @param locked
	 */
	public void draw(Graphics2D g2, double x, double y, int tileSize, Color doorColor, boolean locked){
		ballDiam = tileSize/5;
		lazerDiam = tileSize/8;
		
		g2.setColor(WALL_COLOR);
		g2.fill(new Rectangle2D.Double(x, y, lazerDiam, lazerDiam)); //top left
		g2.fill(new Rectangle2D.Double(x+tileSize-lazerDiam, y, lazerDiam, lazerDiam)); //top right
		g2.fill(new Rectangle2D.Double(x, y+tileSize-lazerDiam, lazerDiam, lazerDiam)); //bottom left
		g2.fill(new Rectangle2D.Double(x+tileSize-lazerDiam, y+tileSize-lazerDiam, lazerDiam, lazerDiam)); //top right
		
		//push matrix - lazer top left
		Graphics2D gTemp = (Graphics2D) g2.create();
		g2.translate(x+ballDiam/2, y+ballDiam/2);
		g2.rotate(Math.toRadians(45));
		drawLazer(g2,tileSize, doorColor, locked);	
		//pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
		
		//push matrix - lazer top right
		gTemp = (Graphics2D) g2.create();
		g2.translate(x+tileSize-ballDiam/2, y+ballDiam/2);
		g2.rotate(Math.toRadians(135));
		drawLazer(g2,tileSize, doorColor, locked);	
		//pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
		
		//push matrix - lazer bottom left
		gTemp = (Graphics2D) g2.create();
		g2.translate(x+ballDiam/2, y+tileSize-ballDiam/2);
		g2.rotate(Math.toRadians(-45));
		drawLazer(g2,tileSize, doorColor, locked);	
		//pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
		
		//push matrix - lazer bottom right
		gTemp = (Graphics2D) g2.create();
		g2.translate(x+tileSize-ballDiam/2, y+tileSize-ballDiam/2);
		g2.rotate(Math.toRadians(-135));
		drawLazer(g2,tileSize, doorColor, locked);	
		//pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
	}
	
	private void drawLazer(Graphics2D g2, int tileSize, Color doorColor, boolean locked) {
		if(locked) {
			g2.setColor(doorColor);
			g2.fill(new Rectangle2D.Double(ballDiam/2, -lazerDiam/2, tileSize/2, lazerDiam));
		}
		g2.setColor(WALL_COLOR_DARK);
		g2.fill(new Ellipse2D.Double(-ballDiam/2, -ballDiam/2, ballDiam, ballDiam));
	}
}
