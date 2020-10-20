package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import java.util.List;

import nz.ac.vuw.ecs.swen225.gp20.maze.Item;
import nz.ac.vuw.ecs.swen225.gp20.maze.Key;

/**
 * @author Marco
 * 
 * Draws the items the player has collected in the form of an inventory readout
 *
 */
public class RenderInventory {
	
	private RenderKey rKey;
	
	private static final Color FLOOR_COLOR = new Color(150,150,150);
	
	/**
	 * 
	 * Initializes the inventory renderer passing it the object renderers
	 * 
	 * @param rKey 
	 * 
	 */
	public RenderInventory(RenderKey rKey) {
		this.rKey = rKey;
	}

	/**
	 * 
	 * Draws the inventory
	 * 
	 * @param g2
	 * @param inventory
	 * @param x
	 * @param y
	 * @param tileSize
	 */
	public void draw(Graphics2D g2, List<Item> inventory, double x, double y, int tileSize) {
		for(int row = 0; row <8; row++) {
			drawTile(g2, x, y+tileSize*row, tileSize);
			if(inventory.size() > row) {
				Item current = inventory.get(row);
				if(current instanceof Key) {
					//draw the key
					Key k = (Key) current;
					//push matrix - key movement
					Graphics2D gTemp = (Graphics2D) g2.create();
					rKey.draw(g2, x, y+tileSize*row, tileSize, k.getColor());
					//pop matrix
					g2.dispose();
					g2 = (Graphics2D) gTemp.create();
				}
			}
	    	
		}
	}
	
	private void drawTile(Graphics2D g2, double x, double y, int tileSize) {
		g2.setColor(FLOOR_COLOR);
		g2.fill(new RoundRectangle2D.Double(x+tileSize/10, y+tileSize/10, tileSize-tileSize/5, tileSize-tileSize/5, 10, 10));
		//g2.draw(new Rectangle2D.Double(x, y, tileSize, tileSize));
	}
	
}
