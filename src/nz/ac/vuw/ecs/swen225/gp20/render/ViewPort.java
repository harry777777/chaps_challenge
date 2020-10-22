package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import nz.ac.vuw.ecs.swen225.gp20.render.MazeInterface.InterfaceDirection;
import nz.ac.vuw.ecs.swen225.gp20.render.MazeInterface.ItemType;
import nz.ac.vuw.ecs.swen225.gp20.render.MazeInterface.TileType;

/**
 * @author Marco McGowan-Arnold - 300390611
 * 
 * Draws the viewport into the Maze centered on the Chap
 *
 */
public class ViewPort {
	
	//Renderers
	private RenderPlayer rPlayer = new RenderPlayer();
	private RenderEnemy rEnemy = new RenderEnemy();
	private RenderDoor rDoor = new RenderDoor();
	private RenderExitDoor rExitDoor = new RenderExitDoor();
	private RenderHint rHint = new RenderHint();
	private RenderTreasure rTreasure;
	private RenderKey rKey;
	
	//Colors
	private static final Color FLOOR_COLOR = new Color(150,150,150);
	private static final Color WALL_COLOR = new Color(120, 120, 120);
	private static final Color WALL_COLOR_DARK = new Color(100, 100, 100);
	private static final Color SPACE_COLOR = new Color(50,50,50);
	private static final Color GRID_COLOR = new Color(0,180,0);
	private static final Color END_COLOR = new Color(220,220,220);
	
	/**
	 * 
	 * Initializes the view port renderer passing it the object renderers
	 * @param rTreasure 
	 * @param rKey 
	 * @param
	 * 
	 */
	public ViewPort(RenderTreasure rTreasure, RenderKey rKey) {
		this.rTreasure = rTreasure;
		this.rKey = rKey;
	}
	
	/**
	 * Draws the view port
	 * 
	 * @param g2 
	 * @param maze
	 * @param x 
	 * @param y 
	 * @param tileSize 
	 * @param viewWidth 
	 * @param viewHeight 
	 */
	public void draw(Graphics2D g2, MazeInterface maze, int x, int y, int tileSize, int viewWidth, int viewHeight) {

		int playerX = maze.getPlayerX();
		int playerY = maze.getPlayerY();
		
		double xMapOffset = playerX*tileSize;
		double yMapOffset = playerY*tileSize;
		
		int centerX = x+(viewWidth/2)*tileSize;
		int centerY = y+(viewHeight/2)*tileSize;
		
		//calculate offset for smooth player movement (used for smooth viewport movement)
		InterfaceDirection direction = maze.getPlayerDirection();
		double xOffset = 0;
		double yOffset = 0;
		int offset = maze.getPlayerOffset();
		if(offset != 0) {
			double divisor = (double)(maze.getPlayerThreshold())/tileSize;
			switch (direction) {
				case LEFT: 
					xOffset = -(offset/divisor);
					break;
				case RIGHT: 
					xOffset = (offset/divisor);
					break;
				case UP: 
					yOffset = -(offset/divisor);
					break;
				case DOWN: 
					yOffset = (offset/divisor);
					break;
			}
		}
		
		//crop the drawing plane for level grid
		g2.clip(new RoundRectangle2D.Double(x-tileSize*1.25, y-tileSize*1.25, viewWidth*tileSize+tileSize*2.5, viewHeight*tileSize+tileSize*2.5, 20, 20));
		
		//tile boarder draw: the outside viewport blueprint effect
		g2.setStroke(new BasicStroke(1));
		for(int row = 0; row < maze.getMazeHeight(); row++) {  //maze.getMazeHeight()
	    	for(int col = 0; col < maze.getMazeWidth(); col++) { //maze.getMazeWidth()
	    		g2.setColor(GRID_COLOR);
    			g2.draw(new Rectangle2D.Double(centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize, tileSize));
    			if(maze.getTileType(row, col).equals(TileType.FREE)) {
	    			if(maze.getItemType(row, col) != null) {
	    				drawX(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    			}
    			}
	    	}
	    }
		
		//draw on screen hint
		String hint = maze.getHintText();
		if(hint != null && !hint.equals("")) {
			rHint.draw(g2, centerX, centerY+(viewHeight/2)*tileSize+tileSize, tileSize, hint);
		}
		//rHint.draw(g2, centerX, centerY+(viewHeight/2)*tileSize+tileSize, tileSize, "shit");
		
		//crop the drawing plane for window into level (player view)
		g2.clip(new RoundRectangle2D.Double(x, y, viewWidth*tileSize+1, viewHeight*tileSize+1, 20, 20));
		
		//draw dark background
		g2.setColor(SPACE_COLOR);
	  	g2.fillRect(x, y, viewWidth*tileSize+2, viewHeight*tileSize+2);
	  	
	  	//prevent looping through things out of view things out of view by calculating where to start and end the nested for loops
	  	int topEdge = playerY-viewHeight/2-1;
	  	int bottomEdge = playerY+viewHeight/2+2;
	  	int leftEdge = playerX-viewWidth/2-1;
	  	int rightEdge = playerX+viewHeight/2+2;
	  	if(topEdge < 0) {
	  		topEdge = 0;
	  	}
	  	if(leftEdge < 0) {
	  		leftEdge = 0;
	  	}
	  	if(bottomEdge > maze.getMazeWidth()) {
	  		bottomEdge = maze.getMazeWidth();
	  	}
	  	if(rightEdge > maze.getMazeHeight()) {
	  		rightEdge = maze.getMazeHeight();
	  	}
		
	  	//draw the maze in the view port
		for(int row = leftEdge; row < rightEdge; row++) {
	    	for(int col = topEdge; col < bottomEdge; col++) {
	    		if(maze.getTileType(row, col).equals(TileType.FREE)) {
	    			//draw floor tile
	    			drawFloor(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    			//draw item
	    			if(maze.getItemType(row, col) != null) {
	    				//push matrix
	    				Graphics2D gTemp = (Graphics2D) g2.create();
	    				if(maze.getItemType(row, col).equals(ItemType.TREASURE)) {
	    					rTreasure.draw(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    				}
	    				if(maze.getItemType(row, col).equals(ItemType.KEY)) {
	    					Color keyColor = maze.getKeyColor(row, col);
	    					rKey.draw(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize, keyColor);
	    				}
	    				//pop matrix
	    				g2.dispose();
	    				g2 = (Graphics2D) gTemp.create();
	    			}
	    		}else if(maze.getTileType(row, col).equals(TileType.WALL)){
	    			//draw wall tile
	    			drawWall(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    		}else if(maze.getTileType(row, col).equals(TileType.DOOR)) {
	    			//draw floor under door
	    			drawFloor(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    			Color doorColor = maze.getDoorColor(row, col);
	    			boolean locked = maze.getDoorLocked(row, col);
	    			//push matrix
	    			Graphics2D gTemp = (Graphics2D) g2.create();
	    			//draw door
	    			rDoor.draw(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize, doorColor, locked);
	    			//pop matrix
	    			g2.dispose();
	    			g2 = (Graphics2D) gTemp.create();
	    		}else if(maze.getTileType(row, col).equals(TileType.EXIT)) {
	    			//draw floor under exit
	    			drawFloor(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    			//draw exit
	    			drawExit(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    		}else if(maze.getTileType(row, col).equals(TileType.EXITDOOR)) {
	    			boolean locked = maze.getExitDoorLocked(row, col);
	    			//draw exit door
	    			if(locked) {
	    				rExitDoor.draw(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    			}else {
	    				drawFloor(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    			}
	    		}else if(maze.getTileType(row, col).equals(TileType.LAVA)) {
	    			drawLava(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    		}else if(maze.getTileType(row, col).equals(TileType.HINT)) {
	    			drawHint(g2, centerX-xMapOffset+row*tileSize-xOffset, centerY-yMapOffset+col*tileSize-yOffset, tileSize);
	    		}
	    	}
	    }
		
		//draw the enemies
		if(maze.getNumberActors() > 0) {
			for(int i = 0; i < maze.getNumberActors(); i++) {
				int actorX = maze.getActorX(i);
				int actorY = maze.getActorY(i);
				rEnemy.draw(g2, centerX-xMapOffset+actorX*tileSize-xOffset, centerY-yMapOffset+actorY*tileSize-yOffset, tileSize, maze, i);
			}
		}

		//draw the player
		rPlayer.draw(g2, centerX, centerY, tileSize, maze);
		
		//step item animations
		rTreasure.step();
		rKey.step();
	}
	
	private void drawFloor(Graphics2D g2, double x, double y, int tileSize) {
		//draw a floor tile
		g2.setColor(FLOOR_COLOR);
		g2.setStroke(new BasicStroke(1));
		g2.fill(new Rectangle2D.Double(x, y, tileSize, tileSize));
		g2.draw(new Rectangle2D.Double(x, y, tileSize, tileSize));
		
		g2.setColor(WALL_COLOR_DARK);
		g2.fill(new RoundRectangle2D.Double(x+tileSize/10, y+tileSize/10, tileSize/20, tileSize/20, tileSize/5, tileSize/5));
		g2.fill(new RoundRectangle2D.Double(x+tileSize-tileSize/10-tileSize/20, y+tileSize/10, tileSize/20, tileSize/20, tileSize/5, tileSize/5));
		g2.fill(new RoundRectangle2D.Double(x+tileSize/10, y+tileSize-tileSize/10-tileSize/20, tileSize/20, tileSize/20, tileSize/5, tileSize/5));
		g2.fill(new RoundRectangle2D.Double(x+tileSize-tileSize/10-tileSize/20, y+tileSize-tileSize/10-tileSize/20, tileSize/20, tileSize/20, tileSize/5, tileSize/5));
		
		g2.setStroke(new BasicStroke((int)(tileSize/50)));
		g2.draw(new Line2D.Double(x+tileSize/2.5, y+tileSize/2, x+tileSize-tileSize/2.5, y+tileSize/2));
		g2.draw(new Line2D.Double(x+tileSize/2, y+tileSize/2.5, x+tileSize/2, y+tileSize-tileSize/2.5));
	}
	
	private void drawHint(Graphics2D g2, double x, double y, int tileSize) {
		//draw a hint tile
		g2.setColor(FLOOR_COLOR);
		g2.setStroke(new BasicStroke(1));
		g2.fill(new Rectangle2D.Double(x, y, tileSize, tileSize));
		g2.draw(new Rectangle2D.Double(x, y, tileSize, tileSize));
		
		g2.setColor(WALL_COLOR_DARK);
		g2.fill(new RoundRectangle2D.Double(x+tileSize/10, y+tileSize/10, tileSize/20, tileSize/20, tileSize/5, tileSize/5));
		g2.fill(new RoundRectangle2D.Double(x+tileSize-tileSize/10-tileSize/20, y+tileSize/10, tileSize/20, tileSize/20, tileSize/5, tileSize/5));
		g2.fill(new RoundRectangle2D.Double(x+tileSize/10, y+tileSize-tileSize/10-tileSize/20, tileSize/20, tileSize/20, tileSize/5, tileSize/5));
		g2.fill(new RoundRectangle2D.Double(x+tileSize-tileSize/10-tileSize/20, y+tileSize-tileSize/10-tileSize/20, tileSize/20, tileSize/20, tileSize/5, tileSize/5));
		
		g2.setStroke(new BasicStroke((int)(tileSize/50)));
		g2.setFont(new Font("Arial", Font.PLAIN, (int)(tileSize/2))); 
		g2.drawString("?", (float)(x+tileSize*0.35), (float)(y+tileSize*0.65));
	}
	
	private void drawX(Graphics2D g2, double x, double y, int tileSize) {
		//draw an X (used for outside viewport grid effect)
		g2.draw(new Line2D.Double(x+tileSize/4, y+tileSize/4, x-tileSize/4+tileSize, y-tileSize/4+tileSize));
		g2.draw(new Line2D.Double(x+tileSize/4, y-tileSize/4+tileSize, x-tileSize/4+tileSize, y+tileSize/4));
	}
	
	private void drawWall(Graphics2D g2, double x, double y, int tileSize) {
		//draw a wall tile
		g2.setColor(WALL_COLOR);
		g2.setStroke(new BasicStroke(1));
		g2.fill(new Rectangle2D.Double(x, y, tileSize, tileSize));
		g2.draw(new Rectangle2D.Double(x, y, tileSize, tileSize));
		
		g2.setColor(WALL_COLOR_DARK);
		g2.setStroke(new BasicStroke((int)(tileSize/16.6)));
		g2.draw(new Line2D.Double(x+tileSize/2, y+1.5, x+tileSize/2, y+tileSize-1.5));
		g2.draw(new Line2D.Double(x+1.5, y+tileSize/2, x+tileSize-1.5, y+tileSize/2));
		
		g2.setColor(FLOOR_COLOR);
		g2.fill(new RoundRectangle2D.Double(x+tileSize/6, y+tileSize/6, tileSize/1.5, tileSize/1.5, tileSize/5, tileSize/5));
		g2.setColor(WALL_COLOR_DARK);
		g2.draw(new RoundRectangle2D.Double(x+tileSize/6, y+tileSize/6, tileSize/1.5, tileSize/1.5, tileSize/5, tileSize/5));
		
	}
	
	private void drawExit(Graphics2D g2, double x, double y, int tileSize) {
		//draw an exit tile
		g2.setColor(WALL_COLOR_DARK);
		g2.fill(new Ellipse2D.Double(x, y, tileSize, tileSize));
		g2.setColor(WALL_COLOR);
		g2.fill(new Ellipse2D.Double(x+tileSize/6, y+tileSize/6, tileSize-tileSize/3, tileSize-tileSize/3));
		g2.setColor(END_COLOR);
		g2.fill(new Ellipse2D.Double(x+tileSize/4, y+tileSize/4, tileSize/2, tileSize/2));
	}
	
	private void drawLava(Graphics2D g2, double x, double y, int tileSize) {
		//draw a lava (void) tile
		g2.setColor(FLOOR_COLOR);
		g2.setStroke(new BasicStroke(1));
		g2.fill(new Rectangle2D.Double(x, y, tileSize, tileSize));
		g2.draw(new Rectangle2D.Double(x, y, tileSize, tileSize));
		
		double edgeWidth = tileSize/8;
		
		g2.setColor(SPACE_COLOR);
		g2.setStroke(new BasicStroke(1));
		g2.fill(new Rectangle2D.Double(x+edgeWidth, y+edgeWidth, tileSize-edgeWidth*2, tileSize-edgeWidth*2));
		//g2.draw(new Rectangle2D.Double(x, y, tileSize, tileSize));
		
	}

}
