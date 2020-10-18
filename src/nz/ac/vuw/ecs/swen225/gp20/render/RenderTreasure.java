package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

import nz.ac.vuw.ecs.swen225.gp20.maze.Player;

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
		//do animation
		if(up) {
			if(height < 5) {
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
		Rectangle r = new Rectangle((int)x, (int)(y+height/2), tileSize, tileSize);
		//Rectangle r = new Rectangle(0, 0, tileSize, tileSize);
		big.setColor(CHAP_BODY_LIGHT);
		big.fillRect(0, 0, tileSize, tileSize); //background color
		
		big.setColor(CHAP_BODY_MEDIUM);
		//big.fillRect(0, tileSize/2, tileSize, tileSize/10); //dark stripe
		
		TexturePaint tp = new TexturePaint(bi, r);
		g2.setPaint(tp);
		
		g2.fill(new Ellipse2D.Double(x+tileSize/4, y+tileSize/4+height/2, tileSize/2, tileSize/2)); //ball
		
		g2.setColor(SHADOW);
		g2.fill(new Ellipse2D.Double(centerX-(tileSize/2.5)/2-(height/2)/2, bottomEdge-tileSize/11, (tileSize/2.5)+(height/2), tileSize/11)); //shadow


	}
}
