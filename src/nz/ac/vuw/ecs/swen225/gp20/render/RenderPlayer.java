package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
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
	
	private static final Color CHAP_BODY = new Color(234, 222, 189);

	
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
		int xOffset = 0;
		int yOffset = 0;
		if(player.getMove() != null) {
			int offset = player.getMove().getDistance();
			
			double divisor = (double)(player.getMove().THRESHOLD)/tileSize;
			if(direction.equals(Direction.LEFT)) {
				xOffset = -(int)(offset/divisor);
			}
			if(direction.equals(Direction.RIGHT)) {
				xOffset = (int)(offset/divisor);
			}
			if(direction.equals(Direction.UP)) {
				yOffset = -(int)(offset/divisor);
			}
			if(direction.equals(Direction.DOWN)) {
				yOffset = (int)(offset/divisor);
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
		g2.drawString("Player", x+8+xOffset, y+12+yOffset);
		
		//pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
	}
	
	private void drawFront(int x, int y, int tileSize, Graphics2D g2) {
		//draw a rounded square as a temp representation of the chap
		g2.setStroke(new BasicStroke(2));
		g2.setColor(CHAP_BODY);
		g2.fill(new RoundRectangle2D.Double(x+tileSize/4, y+tileSize/4, tileSize/2, tileSize/2, 10, 10));
	}
}
