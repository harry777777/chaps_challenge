package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;

import nz.ac.vuw.ecs.swen225.gp20.maze.actors.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;

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
		
		for(int row = 0; row < tiles.length; row++) {
	    	for(int col = 0; col < tiles[row].length; col++) {
	    		Tile current = tiles[row][col];
	    		if(current instanceof FreeTile) {
	    			drawFloor(g2, x+row*tileSize, y+col*tileSize, tileSize);
	    		}
	    		
	    	}
	    }
	    
	    //draw the player on top of the view using information from the previous two stages
	    drawChap(g2, 0, 0, tileSize); //temp
	    
	    //TODO setup the moving viewport
	}
	
	private void drawChap(Graphics2D g2, int x, int y, int tileSize) {
		//draw a rounded square as a temp representation of the chap
		g2.setStroke(new BasicStroke(2));
		g2.setColor(CHAP_BODY);
		g2.fill(new RoundRectangle2D.Double(x+tileSize/4, y+tileSize/4, tileSize/2, tileSize/2, 10, 10));
	}
	
	private void drawFloor(Graphics2D g2, int x, int y, int tileSize) {
		//draw a plane color for the floor (temp)
		g2.setColor(FLOOR_COLOR);
		g2.fillRect(x, y, tileSize, tileSize);
	}
}
