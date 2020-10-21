package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

/**
 * @author Marco
 * 
 * Holds the logic for drawing treasures onto the view plane
 *
 */
public class RenderTreasure {
	
	private static final Color CHAP_BODY_LIGHT = new Color(234, 222, 189);
	private static final Color CHAP_BODY_MEDIUM = new Color(166,157,134);
	private static final Color CHAP_BODY = new Color(128, 121, 103); //(166, 157, 134);
	private static final Color CHAP_BODY_DARK = new Color(77,73,62);
	private static final Color SHADOW = new Color(0,0,0,30);

	private double centerX;
	private double centerY;
	private double rightEdge;
	private double bottomEdge;
	
	private double height = 0;
	private boolean up;
	
	
	/**
	 * 
	 * Draws an instance of the treasure item on the graphics plane
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param tileSize
	 */
	public void draw(Graphics2D g2, double x, double y, int tileSize){
		
		
		
		centerX = x+tileSize/2;
		centerY = y+tileSize/2;
		rightEdge = x+tileSize;
		bottomEdge = y+tileSize;
		
		BufferedImage bi = new BufferedImage(tileSize, tileSize, BufferedImage.TYPE_INT_RGB);
		Graphics2D big = bi.createGraphics();
		Rectangle r = new Rectangle(0, 0, tileSize, tileSize);
		
		big.setColor(CHAP_BODY_LIGHT);
		big.fillRect(0, 0, tileSize, tileSize); //background color
		
		big.setColor(CHAP_BODY_MEDIUM);
		big.fillRect(0, tileSize/4, tileSize, tileSize/10); //dark stripe
		
		TexturePaint tp = new TexturePaint(bi, r);
		g2.setPaint(tp);
		
		
		//push matrix - ball movement
		Graphics2D gTemp = (Graphics2D) g2.create();
		
		g2.translate(x+tileSize/4, y+tileSize/4+height/2);
		
		g2.fill(new Ellipse2D.Double(0, 0, tileSize/2, tileSize/2)); //ball
		
		//pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
		
		//push matrix - shadow scaling
		gTemp = (Graphics2D) g2.create();
		
		g2.translate(x+tileSize/2-0.5, bottomEdge-tileSize/11);
		
		g2.scale(1+height/15, 1+height/50);
		
		g2.setColor(SHADOW);
		g2.fill(new Ellipse2D.Double(-(tileSize/4)/2, 0, (tileSize/4), tileSize/13)); //shadow
		
		//pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();


	}
	
	/**
	 * Steps the animation
	 */
	public void step() {
		//do animation
		if(up) {
			if(height < 10) {
				height+=0.25;
			}else {
				up = !up;
			}
		}else {
			if(height > 0) {
				height-=0.25;
			}else {
				up = !up;
			}
		}
	}
}
