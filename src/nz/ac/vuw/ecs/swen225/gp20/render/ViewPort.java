package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.FreeTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.Tile;
import nz.ac.vuw.ecs.swen225.gp20.maze.tiles.WallTile;
import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
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
	private static final Color FLOOR_COLOR = new Color(150,150,150);
	private static final Color WALL_COLOR = new Color(120, 120, 120);
	private static final Color BACKGROUND_COLOR = new Color(242,242,242);
	//Moving viewport
	private int viewWidth, viewHeight;
	
	private double lerpXCurrent = -1, lerpYCurrent = -1, viewSpeed = 0.15; //0.2 //0.025
	
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
		this.viewWidth = viewWidth;
		this.viewHeight = viewHeight;
		int playerX = player.getX();
		int playerY = player.getY();
		
		double xMapOffset = playerX*tileSize;
		double yMapOffset = playerY*tileSize;
		
		if(lerpXCurrent == -1 || lerpYCurrent == -1) {	//if the lerp values haven't been initialized, initialize them
			lerpXCurrent = xMapOffset;
			lerpYCurrent = yMapOffset;
		}
		
		if(lerpXCurrent != xMapOffset || lerpYCurrent != yMapOffset) { //update the lerp
			//lerpXCurrent = lerp(lerpXCurrent, xMapOffset, viewSpeed);
			//lerpYCurrent = lerp(lerpYCurrent, yMapOffset, viewSpeed);
			lerpXCurrent = xMapOffset;
			lerpYCurrent = yMapOffset;
		}
		
		Direction direction = player.getFacing();
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
		
		
		
		//find player position in the Maze and player offset
		
		//needs to be modified so it only access and draws the tiles around the player position as determined above
	    //this is done using the viewHeight and viewWidth values as well as the position of the player
		
		//random testing
		//drawFloor(g2, 100, 100, 200); 
		//g2.setColor(new Color(0,0,0));
		//g2.drawString(Integer.toString(tiles.length), 120, 200);
		
		//g2.clip(new Rectangle2D.Double(25, 25, 500, 500));
		g2.clip(new RoundRectangle2D.Double(50, 50, 450, 450, 20, 20));
		
		//draw background (temp)
		g2.setColor(WALL_COLOR);
	  	g2.fillRect(0, 0, 1000, 1000);
		
	  	
	  	
		
		for(int row = 0; row < tiles.length; row++) {
	    	for(int col = 0; col < tiles[row].length; col++) {
	    		Tile current = tiles[row][col];
	    		
	    		//temp tile boarder draw
	    		g2.setColor(FLOOR_COLOR);
    			//g2.draw(new Rectangle2D.Double(x-lerpXCurrent+row*tileSize, y-lerpYCurrent+col*tileSize, tileSize, tileSize));
	    		
	    		if(row < playerX-viewWidth/2 || row > playerX+viewWidth/2 || col < playerY-viewHeight/2 || col > playerY+viewHeight/2) {
	    			//continue;
	    		}
	    		if(current instanceof FreeTile) {
	    			//drawFloor(g2, x-lerpXCurrent+row*tileSize, y-lerpYCurrent+col*tileSize, tileSize);
	    			drawFloor(g2, x-lerpXCurrent+row*tileSize-xOffset, y-lerpYCurrent+col*tileSize-yOffset, tileSize);
	    		}else if(current instanceof WallTile){
	    			//drawWall(g2, x-lerpXCurrent+row*tileSize, y-lerpYCurrent+col*tileSize, tileSize);
	    			drawWall(g2, x-lerpXCurrent+row*tileSize-xOffset, y-lerpYCurrent+col*tileSize-yOffset, tileSize);
	    		}
	    		
	    	}
	    }
	    
	    //draw the player on top of the view using information from the previous two stages
		Location playerLocation = player.getLocation();

		//g2.setColor(BACKGROUND_COLOR);
		//g2.fill(new Rectangle2D.Double(0, 0, tileSize/2, 1000)); //left edge
		//g2.fill(new Rectangle2D.Double(0, 0, 1000, tileSize/2)); //top edge
		
		
		drawFrame(g2, 5, 15);
		
		

	    //rPlayer.draw(g2, x-lerpXCurrent+playerLocation.x*tileSize, y-lerpYCurrent+playerLocation.y*tileSize, tileSize, player); //temp
		rPlayer.draw(g2, x, y, tileSize, player); //temp
	    
	  
	    

	}
	
	private void drawFrame(Graphics2D g2, int x, int y) {

		//janky frame counter
		g2.setColor(new Color(0,0,0));
		g2.drawString("FrameCount: " + Integer.toString(count), x, y);
		count++;

	}
	
	private void drawFloor(Graphics2D g2, double x, double y, int tileSize) {
		//draw a plane color for the floor (temp)
		g2.setColor(FLOOR_COLOR);
		g2.fill(new Rectangle2D.Double(x, y, tileSize, tileSize));
		g2.draw(new Rectangle2D.Double(x, y, tileSize, tileSize));
	}
	
	private void drawWall(Graphics2D g2, double x, double y, int tileSize) {
		//draw a plane color for the floor (temp)
		g2.setColor(WALL_COLOR);
		g2.fill(new Rectangle2D.Double(x, y, tileSize, tileSize));
		g2.draw(new Rectangle2D.Double(x, y, tileSize, tileSize));
	}
	
	private double lerp(double a, double b, double amount){
	    return a + amount * (b - a);
	}
}
