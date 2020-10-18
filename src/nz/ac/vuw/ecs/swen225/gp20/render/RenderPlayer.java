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
	private double eyeDepth;
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
	
	private double wheelAngle = 0;
	private double bodyAngle = 0;
	private static final double BODY_ANGLE_MAX = 10;
	private static final double BODY_ANGLE_LERP = 0.25;
	
	private static final Color CHAP_BODY_LIGHT = new Color(234, 222, 189);
	private static final Color CHAP_BODY_MEDIUM = new Color(166,157,134);
	private static final Color CHAP_BODY = new Color(128, 121, 103); //(166, 157, 134);
	private static final Color CHAP_BODY_DARK = new Color(77,73,62);
	private static final Color SHADOW = new Color(0,0,0,30);

	
	/**
	 * Draws the player onto the view plane
	 * 
	 * @param g2
	 * @param x
	 * @param y
	 * @param tileSize
	 * @param player
	 */
	public void draw(Graphics2D g2, double x, double y, int tileSize, Player player){
		Direction direction = player.getFacing();
		
		//calculate player offset
		double xOffset = 0;
		double yOffset = 0;
		if(player.getMove() != null) {
			int offset = player.getMove().getDistance();
			if(offset != 0) { //increment wheel rotation and set body angle
				wheelAngle -= 10;
				//bodyAngle = 10;
				bodyAngle = lerp(bodyAngle, BODY_ANGLE_MAX, BODY_ANGLE_LERP);
			}
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
		}else {
			//bodyAngle = 0;
			bodyAngle = lerp(bodyAngle, 0, BODY_ANGLE_LERP);
		}
		
		//draw player from correct direction
		if(direction == null || direction == Direction.DOWN) {
			drawFront(x+xOffset, y+yOffset, tileSize, g2);
		}else if(direction == Direction.UP) {
			drawBack(x+xOffset, y+yOffset, tileSize, g2);
		}else if(direction == Direction.LEFT) {
			drawLeft(x+xOffset, y+yOffset, tileSize, g2);
		}else if(direction == Direction.RIGHT) {
			drawRight(x+xOffset, y+yOffset, tileSize, g2);
		}
		
	}
	
	private void drawFront(double x, double y, int tileSize, Graphics2D g2) {
		centerX = y+tileSize/2;
		bottomEdge = y+tileSize;
		eyeDiam = tileSize/5;
	
		Graphics2D gTemp = (Graphics2D) g2.create();
		drawFrontBack(x, y, tileSize, g2);
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
		
		g2.setColor(CHAP_BODY_LIGHT);
		g2.fill(new RoundRectangle2D.Double(centerX-bodySize/2, y+tileSize/6, bodySize, bodySize/2, 5, 5)); //main body (to block antenna)
		
		g2.setColor(CHAP_BODY);
		g2.fill(new Ellipse2D.Double(centerX-eyeDiam*1.1, centerY-eyeDiam*1.1-bodyAngle/5, eyeDiam, eyeDiam)); //left eye
		g2.fill(new Ellipse2D.Double(centerX+eyeDiam*0.1, centerY-eyeDiam*1.1-bodyAngle/5, eyeDiam, eyeDiam)); //right eye
		
	}
	
	private void drawBack(double x, double y, int tileSize, Graphics2D g2) {
		
		Graphics2D gTemp = (Graphics2D) g2.create();
		drawFrontBack(x, y, tileSize, g2);
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
		
		g2.setColor(CHAP_BODY_MEDIUM);
		g2.fill(new RoundRectangle2D.Double(centerX-(bodySize/1.5)/2, centerY-bodySize/2.5+bodyAngle/5, bodySize/1.5, bodySize/2, 5, 5));
		
	}
	
	private void drawLeft(double x, double y, int tileSize, Graphics2D g2) {
		centerX = x+tileSize/2;
		centerY = y+tileSize/2;
		rightEdge = x+tileSize;
		bottomEdge = y+tileSize;
		
		bodySize = tileSize/2;
		eyeDiam = tileSize/5;
		eyeDepth = tileSize/10;
		ballDiam = tileSize/11;
		rodDiam = tileSize/25;
		rodLength = tileSize/6;
		
		wheelDiam = tileSize/3.2;
		axelDiam = tileSize/10;
		supportDiam = tileSize/14;
		supportLength = tileSize/6;
		
		shoulderDiam = tileSize/8;
		armDiam = tileSize/18;
		armLength = tileSize/6;
		handSize = tileSize/10;
		
		g2.setColor(SHADOW);
		g2.fill(new Ellipse2D.Double(centerX-tileSize/3.7+bodyAngle/4, bottomEdge-tileSize/13, tileSize/2, tileSize/11)); //shadow
		
		
		g2.setColor(CHAP_BODY_DARK);
		g2.fill(new Ellipse2D.Double(centerX-wheelDiam/2, bottomEdge-wheelDiam, wheelDiam, wheelDiam)); //wheel
		
		//wheel rotation - push matrix
		Graphics2D gTemp = (Graphics2D) g2.create();
		g2.rotate(Math.toRadians(wheelAngle), centerX, bottomEdge-wheelDiam/2);
		g2.setColor(CHAP_BODY_LIGHT);
		g2.fill(new Rectangle2D.Double(centerX-wheelDiam/4, bottomEdge-wheelDiam+wheelDiam/4, wheelDiam/2, wheelDiam/2)); //square
		//wheel rotation pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
		
		//push matrix
		gTemp = (Graphics2D) g2.create();
		//body tilt
		g2.rotate(Math.toRadians(bodyAngle), centerX, bottomEdge-wheelDiam/2);
		
		g2.setColor(CHAP_BODY);
		g2.fill(new Rectangle2D.Double(centerX-bodySize/2.2-eyeDepth, centerY-eyeDiam*1.1, eyeDepth, eyeDiam)); //eye
		g2.fill(new Rectangle2D.Double(centerX-supportDiam/2, centerY+tileSize/6, supportDiam, supportLength)); //support
		g2.fill(new Rectangle2D.Double(centerX-rodDiam/2, y, rodDiam, rodLength)); //antenna rod
		
		g2.setColor(CHAP_BODY_LIGHT);
		g2.fill(new RoundRectangle2D.Double(centerX-bodySize/2.2, y+tileSize/6, bodySize*0.8, bodySize, 5, 5)); //main body
		g2.fill(new Ellipse2D.Double(centerX-ballDiam/2, y, ballDiam, ballDiam)); //antenna ball
		
		g2.setColor(CHAP_BODY_MEDIUM);
		g2.fill(new Ellipse2D.Double(centerX-axelDiam/2, bottomEdge-wheelDiam/2-axelDiam/2, axelDiam, axelDiam)); //axel
		
		//arm tilt
		g2.rotate(Math.toRadians(-bodyAngle*2.5+10), centerX, centerY-shoulderDiam*0.8);
		g2.setColor(CHAP_BODY);
		g2.fill(new Rectangle2D.Double(centerX-armDiam/2, centerY-shoulderDiam*0.8, armDiam, armLength)); //arm
		g2.setColor(CHAP_BODY_LIGHT);
		g2.fill(new RoundRectangle2D.Double(centerX-handSize/2, centerY+handSize*0.5, handSize, handSize, 3, 3)); //hand
		g2.setColor(CHAP_BODY_MEDIUM);
		g2.fill(new Ellipse2D.Double(centerX-shoulderDiam/2, centerY-shoulderDiam*1.5, shoulderDiam, shoulderDiam)); //shoulder
		g2.draw(new RoundRectangle2D.Double(centerX-handSize/2, centerY+handSize*0.5, handSize, handSize, 3, 3)); //hand
		
		//pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
		
	}
	
	private void drawRight(double x, double y, int tileSize, Graphics2D g2) {

		//push matrix
		Graphics2D gTemp = (Graphics2D) g2.create();
		
		g2.translate(x, 0);
	    g2.scale(-1, 1);
	    g2.translate(-x-tileSize, 0);
		drawLeft(x, y, tileSize, g2);
		
		//pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
	}
	
	private void drawFrontBack(double x, double y, int tileSize, Graphics2D g2) {
		centerX = x+tileSize/2;
		centerY = y+tileSize/2;
		rightEdge = x+tileSize;
		bottomEdge = y+tileSize;
		
		bodySize = tileSize/2;
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
		
		//g2.setStroke(new BasicStroke(2));
		
		g2.setColor(SHADOW);
		g2.fill(new Ellipse2D.Double(centerX-tileSize/4, bottomEdge-tileSize/13, tileSize/2, tileSize/11)); //shadow
		
		//push matrix - left arm
		Graphics2D gTemp = (Graphics2D) g2.create();
		
		g2.rotate(Math.toRadians(bodyAngle), centerX-bodySize/2-armDiam*1.5, centerY-shoulderDiam*1);
		g2.setColor(CHAP_BODY);
		g2.fill(new Rectangle2D.Double(centerX-bodySize/2-armDiam*1.5, centerY-shoulderDiam*0.5, armDiam, armLength)); //left arm
		g2.setColor(CHAP_BODY_LIGHT);
		g2.fill(new RoundRectangle2D.Double(centerX-bodySize/2-handSize*1.2, centerY+handSize*0.5, handSize, handSize, 3, 3)); //left hand
		
		//pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
		
		//push matrix - right arm
		gTemp = (Graphics2D) g2.create();
		
		g2.rotate(Math.toRadians(-bodyAngle), centerX+bodySize/2+armDiam*1, centerY-shoulderDiam*1);
		g2.setColor(CHAP_BODY);
		g2.fill(new Rectangle2D.Double(centerX+bodySize/2+armDiam*0.5, centerY-shoulderDiam*0.5, armDiam, armLength)); //right arm
		g2.setColor(CHAP_BODY_LIGHT);
		g2.fill(new RoundRectangle2D.Double(centerX+bodySize/2+handSize*0.2, centerY+handSize*0.5, handSize, handSize, 3, 3)); //right hand
		
		//pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
		
		g2.setColor(CHAP_BODY_MEDIUM);
		g2.fill(new RoundRectangle2D.Double(centerX-shoulderWidth/2, centerY-shoulderDiam*1.5, shoulderWidth, shoulderDiam, 3, 3)); //shoulder
		
		g2.setColor(CHAP_BODY_LIGHT);
		g2.fill(new RoundRectangle2D.Double(centerX-bodySize/2, y+tileSize/6, bodySize, bodySize, 5, 5)); //main body
		
		g2.setColor(CHAP_BODY);
		g2.fill(new Rectangle2D.Double(centerX-supportDiam*2, centerY+tileSize/6, supportDiam, supportLength)); //left support
		g2.fill(new Rectangle2D.Double(centerX+supportDiam*1, centerY+tileSize/6, supportDiam, supportLength)); //right support
		
		g2.setColor(CHAP_BODY_MEDIUM);
		g2.fill(new RoundRectangle2D.Double(centerX-axelLength/2, bottomEdge-wheelDiam/2-axelDiam/2, axelLength, axelDiam, 3, 3)); //axel
		
		//antenna
		g2.setColor(CHAP_BODY);
		g2.fill(new Rectangle2D.Double(centerX-rodDiam/2, y+bodyAngle/5, rodDiam, rodLength)); //antenna rod
		g2.setColor(CHAP_BODY_LIGHT);
		g2.fill(new Ellipse2D.Double(centerX-ballDiam/2, y+bodyAngle/5, ballDiam, ballDiam)); //antenna ball
		
		g2.setColor(CHAP_BODY_DARK);
		g2.fill(new RoundRectangle2D.Double(centerX-wheelWidth/2, bottomEdge-wheelDiam, wheelWidth, wheelDiam, 5, 5)); //wheel
	}
	
	private double lerp(double a, double b, double amount){
	    return a + amount * (b - a);
	}
	
	
}
