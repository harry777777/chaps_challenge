package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;
import java.awt.geom.Line2D;

import nz.ac.vuw.ecs.swen225.gp20.maze.Player;
import nz.ac.vuw.ecs.swen225.gp20.maze.Treasure;
import nz.ac.vuw.ecs.swen225.gp20.maze.Actor;
import nz.ac.vuw.ecs.swen225.gp20.maze.Key;
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
	private RenderTreasure rTreasure;
	private RenderKey rKey;
	private List<Actor> actors;
	
	//Maze
	private static final Color FLOOR_COLOR = new Color(150,150,150);
	private static final Color WALL_COLOR = new Color(120, 120, 120);
	private static final Color BACKGROUND_COLOR = new Color(0,180,0); //0,195,0
	private static final Color GRID_COLOR = new Color(3,251,3);
	
	private int count = 0; //frame counter
	
	/**
	 * 
	 * Initializes the view port renderer passing it the object renderers
	 * 
	 */
	public ViewPort(RenderTreasure rTreasure, RenderKey rKey, List<Actor> actors) {
		this.rTreasure = rTreasure;
		this.rKey = rKey;
		this.actors = actors;
	}
	
	/**
	 * Draws the view port
	 * 
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

		int playerX = player.getX();
		int playerY = player.getY();
		
		double xMapOffset = playerX*tileSize;
		double yMapOffset = playerY*tileSize;
		
		int centerX = x+(viewWidth/2)*tileSize;
		int centerY = y+(viewHeight/2)*tileSize;
		
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
		
		//g2.setColor(BACKGROUND_COLOR);
	  	//g2.fillRect(x-1000, y-1000, 2000, 2000);
		
		//crop the drawing plane
		g2.clip(new RoundRectangle2D.Double(x-tileSize*1.25, y-tileSize*1.25, viewWidth*tileSize+tileSize*2.5, viewHeight*tileSize+tileSize*2.5, 20, 20));
		
		//temp tile boarder draw: could be an interesting effect
		for(int row = 0; row < tiles.length; row++) {
	    	for(int col = 0; col < tiles[row].length; col++) {
	    		Tile current = tiles[row][col];
	    		
	    		//g2.setColor(GRID_COLOR);
	    		g2.setColor(WALL_COLOR);
    			g2.draw(new Rectangle2D.Double(centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize, tileSize));
    			if(current instanceof FreeTile) {
	    			FreeTile currentT = (FreeTile) current;
	    			if(currentT.getItem() != null) {
	    				drawX(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    			}
    			}
	    	}
	    }
		
		//crop the drawing plane
		g2.clip(new RoundRectangle2D.Double(x, y, viewWidth*tileSize+1, viewHeight*tileSize+1, 20, 20));
		
		//draw background
		
		g2.setColor(FLOOR_COLOR);
	  	g2.fillRect(x, y, viewWidth*tileSize+2, viewHeight*tileSize+2);
		
	  	//draw the maze in the view port
		for(int row = 0; row < tiles.length; row++) {
	    	for(int col = 0; col < tiles[row].length; col++) {
	    		Tile current = tiles[row][col];
	    		
	    		if(current instanceof FreeTile) {
	    			FreeTile currentT = (FreeTile) current;
	    			//drawFloor(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    			if(currentT.getItem() != null) {
	    				//push matrix
	    				Graphics2D gTemp = (Graphics2D) g2.create();
	    				if(currentT.getItem() instanceof Treasure) {
	    					rTreasure.draw(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    				}
	    				if(currentT.getItem() instanceof Key) {
	    					Color keyColor = ((Key) currentT.getItem()).getColor();
	    					rKey.draw(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize, keyColor);
	    				}
	    				//pop matrix
	    				g2.dispose();
	    				g2 = (Graphics2D) gTemp.create();
	    			}
	    		}else if(current instanceof WallTile){
	    			drawWall(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    		}
	    		
	    	}
	    }
	    
		//drawFrame(g2, 5, 15);
		
		//Location playerLocation = player.getLocation();
	    //rPlayer.draw(g2, x+playerLocation.x*tileSize, y+playerLocation.y*tileSize, tileSize, player); //temp
		rPlayer.draw(g2, centerX, centerY, tileSize, player); //temp

		for(Actor a: actors) {
			drawBad(g2, centerX, centerY, tileSize);
		}
		
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
	
	private void drawBad(Graphics2D g2, double x, double y, int tileSize) {
		//draw a plane color for the floor (temp)
		g2.setColor(WALL_COLOR);
		g2.fill(new Rectangle2D.Double(x+tileSize/4, y+tileSize/4, tileSize/2, tileSize/2));
	}
	
	private void drawX(Graphics2D g2, double x, double y, int tileSize) {
		g2.draw(new Line2D.Double(x+tileSize/4, y+tileSize/4, x-tileSize/4+tileSize, y-tileSize/4+tileSize));
		g2.draw(new Line2D.Double(x+tileSize/4, y-tileSize/4+tileSize, x-tileSize/4+tileSize, y+tileSize/4));
	}
	
	private void drawWall(Graphics2D g2, double x, double y, int tileSize) {
		//draw a plane color for the floor (temp)
		g2.setColor(WALL_COLOR);
		g2.fill(new Rectangle2D.Double(x, y, tileSize, tileSize));
		g2.draw(new Rectangle2D.Double(x, y, tileSize, tileSize));
	}

}
