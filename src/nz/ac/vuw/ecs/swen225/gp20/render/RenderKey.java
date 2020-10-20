package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 * @author Marco
 *
 * Holds the logic for drawing keys onto the view plane
 *
 */
public class RenderKey {
	private static final Color CHAP_BODY_LIGHT = new Color(234, 222, 189);
	private static final Color CHAP_BODY_MEDIUM = new Color(166,157,134);
	private static final Color CHAP_BODY = new Color(128, 121, 103); //(166, 157, 134);
	private static final Color CHAP_BODY_DARK = new Color(77,73,62);
	private static final Color SHADOW = new Color(0,0,0,30);
	
	private double centerX;
	private double centerY;
	private double rightEdge;
	private double bottomEdge;
	
	private double height = 10;
	private boolean up = false;
	
	/**
	 * 
	 * Draws an instance of the key item on the graphics plane
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param tileSize
	 * @param keyColor 
	 * @param color
	 */
	public void draw(Graphics2D g2, double x, double y, int tileSize, Color keyColor){
		
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
		big.fill(new Rectangle2D.Double(tileSize/30, tileSize/8, tileSize/6, tileSize/4)); //dark square
		big.draw(new Rectangle2D.Double(tileSize/30, tileSize/8, tileSize/6, tileSize/4));
		
		//push matrix - KEY text
		Graphics2D gTemp = (Graphics2D) big.create();
		
		big.rotate(Math.toRadians(-90), tileSize/7.5, tileSize/2.8); //rotate so text is sideways
		
		big.setFont(new Font("Arial", Font.PLAIN, (int)(tileSize/8.3))); 
		big.setColor(CHAP_BODY_DARK);
		big.drawString("KEY", (float)(tileSize/7.5), (float)(tileSize/2.8));
		
		//pop matrix
		big.dispose();
		big = (Graphics2D) gTemp.create();
		
		big.setColor(keyColor);
		big.fill(new Rectangle2D.Double(tileSize/4, tileSize/8, tileSize/2, tileSize/4)); //key card color section
		
		big.setColor(CHAP_BODY_DARK);
		big.draw(new Rectangle2D.Double(tileSize/4, tileSize/8, tileSize/2, tileSize/4)); //key card color section outline
		
		TexturePaint tp = new TexturePaint(bi, r);
		g2.setPaint(tp);
		
		//push matrix - key movement
		gTemp = (Graphics2D) g2.create();
				
		g2.translate(x+tileSize/4, y+tileSize/4+height/2);
				
		g2.fill(new RoundRectangle2D.Double(0, 0, tileSize/2, tileSize/2, tileSize/5, tileSize/5)); //ball
				
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
		
		//g2.setColor(keyColor);
		//g2.fill(new RoundRectangle2D.Double(x+tileSize/4, y+tileSize/4, tileSize/2, tileSize/2, tileSize/5, tileSize/5));
	}
}
