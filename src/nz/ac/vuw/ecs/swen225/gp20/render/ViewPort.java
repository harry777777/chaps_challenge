package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Color;
import java.awt.Graphics2D;
import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Location;

/**
 * @author Marco
 * 
 * Draws the viewport into the Maze centered on the Chap
 *
 */
public class ViewPort {
	
	private RenderPlayer rPlayer = new RenderPlayer();
	
	//Maze
	private static final Color FLOOR_COLOR = new Color(164, 149, 126);
	
	private int count = 0; //frame counter
	
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
		//drawFrame(g2, x+playerLocation.x*tileSize, y+playerLocation.y*tileSize);
		drawFrame(g2, 5, 15);

	    rPlayer.draw(g2, x+playerLocation.x*tileSize, y+playerLocation.y*tileSize, tileSize, player); //temp
	    
	   
	    
	    //drawChap(g2, x+playerLocation.getHorizontal()*tileSize, y+playerLocation.getVertical()*tileSize, tileSize, playerDirection); //old
	    
	    //TODO setup the moving viewport
	}
	
	private void drawFrame(Graphics2D g2, int x, int y) {

		//janky frame counter
		g2.setColor(new Color(0,0,0));
		g2.drawString("FrameCount: " + Integer.toString(count), x, y);
		count++;

	}
	
	private void drawFloor(Graphics2D g2, int x, int y, int tileSize) {
		//draw a plane color for the floor (temp)
		g2.setColor(FLOOR_COLOR);
		g2.fillRect(x, y, tileSize, tileSize);
	}
}
