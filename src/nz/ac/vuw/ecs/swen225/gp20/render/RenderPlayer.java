package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;

/**
 * @author Marco
 * 
 * Holds the logic for drawing the player onto the view plane
 *
 */
public class RenderPlayer {
	private double centerX;
	private double centerY;
	private double rightEdge;
	private double bottomEdge;
	
	private double bodySize;
	private double eyeDiam;
	private double ballDiam;
	private double rodDiam;
	private double rodLength;
	
	private double wheelWidth;
	private double wheelDiam;
	private double axelLength;
	private double axelDiam;
	private double supportDiam;
	private double supportLength;
	
	private double shoulderDiam;
	private double shoulderWidth;
	private double armDiam;
	private double armLength;
	private double handSize;
	
	private static final Color CHAP_BODY_LIGHT = new Color(234, 222, 189);
	private static final Color CHAP_BODY_MEDIUM = new Color(166,157,134);
	private static final Color CHAP_BODY = new Color(128, 121, 103); //(166, 157, 134);
	private static final Color CHAP_BODY_DARK = new Color(77,73,62);

	
	/**
	 * Draws the player onto the view plane
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param tileSize
	 * @param player
	 */
	public void draw(Graphics2D g2, int x, int y, int tileSize, Player player){
		Direction direction = player.getFacing();
		
		//calculate player offset
		double xOffset = 0;
		double yOffset = 0;
		if(player.getMove() != null) {
			int offset = player.getMove().getDistance();
			
			double divisor = (double)(player.getMove().THRESHOLD)/tileSize;
			if(direction.equals(Direction.LEFT)) {
				xOffset = -(offset/divisor);
			}
			if(direction.equals(Direction.RIGHT)) {
				xOffset = (offset/divisor);
			}
			if(direction.equals(Direction.UP)) {
				yOffset = -(offset/divisor);
			}
			if(direction.equals(Direction.DOWN)) {
				yOffset = (offset/divisor);
			}

		}
		
		//draw the player
		drawFront(x+xOffset, y+yOffset, tileSize, g2);
		
		//temp player direction showing
		int angle = 0;
		if(direction != null) {
			if(direction == Direction.UP) {
				angle = 180;
			}
			if(direction == Direction.LEFT) {
				angle = 90;
			}
			if(direction == Direction.RIGHT) {
				angle = -90;
			}
		}
		
		//push matrix
		Graphics2D gTemp = (Graphics2D) g2.create();
		
		g2.rotate(Math.toRadians(angle), x+xOffset+tileSize/2, y+yOffset+tileSize/2);
		g2.setColor(new Color(0,0,0));
		g2.drawString("Player", (int)(x+8+xOffset), (int)(y+12+yOffset));
		
		//pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
	}
	
	private void drawFront(double x, double y, int tileSize, Graphics2D g2) {
		centerX = x+tileSize/2;
		centerY = y+tileSize/2;
		rightEdge = x+tileSize;
		bottomEdge = y+tileSize;
		
		bodySize = tileSize/2;
		eyeDiam = tileSize/5;
		ballDiam = tileSize/11;
		rodDiam = tileSize/25;
		rodLength = tileSize/6;
		
		wheelWidth = tileSize/10;
		wheelDiam = tileSize/3.2;
		axelLength = tileSize/3.2;
		axelDiam = tileSize/10;
		supportDiam = tileSize/14;
		supportLength = tileSize/6;
		
		shoulderDiam = tileSize/8;
		shoulderWidth = tileSize/1.4;
		armDiam = tileSize/18;
		armLength = tileSize/6;
		handSize = tileSize/10;
		
		g2.setStroke(new BasicStroke(2));
		
		g2.setColor(CHAP_BODY);
		g2.fill(new Rectangle2D.Double(centerX-supportDiam*2, centerY+tileSize/6, supportDiam, supportLength)); //left support
		g2.fill(new Rectangle2D.Double(centerX+supportDiam*1, centerY+tileSize/6, supportDiam, supportLength)); //right support
		g2.fill(new Rectangle2D.Double(centerX-bodySize/2-armDiam*1.5, centerY-shoulderDiam*0.5, armDiam, armLength)); //left arm
		g2.fill(new Rectangle2D.Double(centerX+bodySize/2+armDiam*0.5, centerY-shoulderDiam*0.5, armDiam, armLength)); //right arm
		g2.fill(new Rectangle2D.Double(centerX-rodDiam/2, y, rodDiam, rodLength)); //antenna rod
		
		g2.setColor(CHAP_BODY_MEDIUM);
		g2.fill(new RoundRectangle2D.Double(centerX-shoulderWidth/2, centerY-shoulderDiam*1.5, shoulderWidth, shoulderDiam, 3, 3)); //shoulder
		
		g2.setColor(CHAP_BODY_LIGHT);
		g2.fill(new RoundRectangle2D.Double(centerX-bodySize/2, y+tileSize/6, bodySize, tileSize/2, 5, 5)); //main body
		g2.fill(new RoundRectangle2D.Double(centerX-axelLength/2, bottomEdge-wheelDiam/2-axelDiam/2, axelLength, axelDiam, 3, 3)); //axel
		g2.fill(new RoundRectangle2D.Double(centerX-bodySize/2-handSize*1.2, centerY+handSize*0.5, handSize, handSize, 3, 3)); //left hand
		g2.fill(new RoundRectangle2D.Double(centerX+bodySize/2+handSize*0.2, centerY+handSize*0.5, handSize, handSize, 3, 3)); //right hand
		g2.fill(new Ellipse2D.Double(centerX-ballDiam/2, y, ballDiam, ballDiam)); //antenna ball
		
		g2.setColor(CHAP_BODY);
		g2.fill(new Ellipse2D.Double(centerX-eyeDiam*1.1, centerY-eyeDiam*1.1, eyeDiam, eyeDiam)); //left eye
		g2.fill(new Ellipse2D.Double(centerX+eyeDiam*0.1, centerY-eyeDiam*1.1, eyeDiam, eyeDiam)); //right eye
		
		g2.setColor(CHAP_BODY_DARK);
		g2.fill(new RoundRectangle2D.Double(centerX-wheelWidth/2, bottomEdge-wheelDiam, wheelWidth, wheelDiam, 5, 5)); //wheel
	}
}
