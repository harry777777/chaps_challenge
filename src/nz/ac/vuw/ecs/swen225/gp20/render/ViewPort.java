package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;

/**
 * @author Marco
 * 
 * Draws the viewport into the Maze centered on the Chap
 *
 */
public class ViewPort {

	
	//Chap
	private static final Color CHAP_BODY = new Color(234, 222, 189);
		
	//Maze
	private static final Color FLOOR_COLOR = new Color(164, 149, 126);
	
	private int count = 0;
	
	/**
	 * Draws the view port
	 * @param g2 
	 * @param tiles 
	 * @param player 
	 * @param x 
	 * @param y 
	 * @param tileSize 
	 * @param viewWidth 
	 * @param viewHeight 
	 */
	public void draw(Graphics2D g2, Tile[][] tiles, Player player, int x, int y, int tileSize, int viewWidth, int viewHeight) {
		
		
		//find player position in the Maze and player offset
		
		//needs to be modified so it only access and draws the tiles around the player position as determined above
	    //this is done using the viewHeight and viewWidth values as well as the position of the player
		
		//random testing
		//drawFloor(g2, 100, 100, 200); 
		//g2.setColor(new Color(0,0,0));
		//g2.drawString(Integer.toString(tiles.length), 120, 200);
		
		//draw background (temp)
	    g2.setColor(new Color(86, 142, 115));
	  	g2.fillRect(x, y, tiles[0].length*tileSize, tiles.length*tileSize);
		
	  	
	  	
		
		for(int row = 0; row < tiles.length; row++) {
	    	for(int col = 0; col < tiles[row].length; col++) {
	    		Tile current = tiles[row][col];
	    		if(current instanceof FreeTile) {
	    			drawFloor(g2, x+row*tileSize, y+col*tileSize, tileSize);
	    		}else {
	    			
	    			
	    		}
	    		//temp tile boarder draw
	    		g2.setColor(new Color(0,0,0));
    			g2.drawRect(x+row*tileSize, y+col*tileSize, tileSize, tileSize);
	    		
	    	}
	    }
	    
	    //draw the player on top of the view using information from the previous two stages
		Location playerLocation = player.getLocation();
		Direction playerDirection = null;
		//if(player.getMotion() != null) {
			//playerDirection = player.getMotion().getDirection();
		//}
	    drawChap(g2, x+playerLocation.x*tileSize, y+playerLocation.y*tileSize, tileSize, playerDirection, player); //temp
	    
	    //drawChap(g2, x+playerLocation.getHorizontal()*tileSize, y+playerLocation.getVertical()*tileSize, tileSize, playerDirection); //old
	    
	    //TODO setup the moving viewport
	}
	
	private void drawChap(Graphics2D g2, int x, int y, int tileSize, Direction d, Player player) {
		//draw a rounded square as a temp representation of the chap
		g2.setStroke(new BasicStroke(2));
		g2.setColor(CHAP_BODY);
		g2.fill(new RoundRectangle2D.Double(x+tileSize/4, y+tileSize/4, tileSize/2, tileSize/2, 10, 10));
		
		
		//temp player direction showing
		int angle = 0;
		if(d != null) {
			if(d == Direction.UP) {
				angle = 180;
			}
			if(d == Direction.LEFT) {
				angle = 90;
			}
			if(d == Direction.RIGHT) {
				angle = -90;
			}
		}
		
		//push matrix
		Graphics2D gTemp = (Graphics2D) g2.create();
		
		g2.rotate(Math.toRadians(angle), x, y);
		g2.setColor(new Color(0,0,0));
		g2.drawString("Player", x, y);
		
		//pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
		
		//janky frame counter
		g2.setColor(new Color(0,0,0));
		g2.drawString("FrameCount: " + Integer.toString(count), x-20, y+50);
		count++;
		
		//player offset
		int offset = 0;
		if(player.getMove() != null) {
			offset = player.getMove().getDistance();
			g2.drawString("Offset: " + Integer.toString(offset), x-20, y+70);
		}
		
		
		
		
	}
	
	private void drawFloor(Graphics2D g2, int x, int y, int tileSize) {
		//draw a plane color for the floor (temp)
		g2.setColor(FLOOR_COLOR);
		g2.fillRect(x, y, tileSize, tileSize);
	}
}
