package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.RoundRectangle2D;
import nz.ac.vuw.ecs.swen225.gp20.render.MazeInterface.ItemType;

/**
 * @author Marco McGowan-Arnold - 300390611
 * 
 * Draws the items the player has collected in the form of an inventory readout
 *
 */
public class RenderInventory {
	private RenderTreasure rTreasure;
	private RenderKey rKey;
	
	private static final Color FLOOR_COLOR = new Color(150,150,150);
	
	/**
	 * 
	 * Initializes the inventory renderer passing it the object renderers
	 * 
	 * @param rTreasure 
	 * @param rKey 
	 * 
	 */
	public RenderInventory(RenderTreasure rTreasure, RenderKey rKey) {
		this.rTreasure = rTreasure;
		this.rKey = rKey;
	}

	/**
	 * 
	 * Draws the inventory
	 * 
	 * @param g2
	 * @param maze
	 * @param x
	 * @param y
	 * @param tileSize
	 */
	public void draw(Graphics2D g2, MazeInterface maze, double x, double y, int tileSize) {
		//loop through inventory (capped at 7 because there will never be more than a few items)
		for(int row = 0; row <8; row++) {
			drawTile(g2, x, y+tileSize*row, tileSize);
			if(maze.getInventorySize() > row) {
				ItemType type = maze.getFromInventory(row);
				if(type.equals(ItemType.KEY)) {
					//draw the key
					Color keyColor = maze.getKeyColorInventory(row);
					//push matrix - key movement
					Graphics2D gTemp = (Graphics2D) g2.create();
					rKey.draw(g2, x, y+tileSize*row-tileSize/9, tileSize, keyColor);
					//pop matrix
					g2.dispose();
					g2 = (Graphics2D) gTemp.create();
				}
			}
			//draw score in last slot of inventory
			if(row == 7) {
				//get treasure
				int treasure = maze.getTreasure();
				
				if(treasure > 0) {
					//push matrix - key movement
					Graphics2D gTemp = (Graphics2D) g2.create();
					rTreasure.draw(g2, x, y+tileSize*row-tileSize/9, tileSize);
					//pop matrix
					g2.dispose();
					g2 = (Graphics2D) gTemp.create();
				
					//draw amount counter
					g2.setFont(new Font("Arial", Font.PLAIN, (int)(tileSize/2))); 
					g2.setColor(new Color(0,0,0));
					g2.drawString("x " + treasure, (float)(x+tileSize), (float)(y+tileSize*row-tileSize/9+tileSize*0.7));
				}
			}
		}
		
		
	}
	
	private void drawTile(Graphics2D g2, double x, double y, int tileSize) {
		//draw an inventory slot
		g2.setColor(FLOOR_COLOR);
		g2.fill(new RoundRectangle2D.Double(x+tileSize/10, y+tileSize/10, tileSize-tileSize/5, tileSize-tileSize/5, tileSize/5, tileSize/5));
	}
	
}
