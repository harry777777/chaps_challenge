package nz.ac.vuw.ecs.swen225.gp20.render;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import nz.ac.vuw.ecs.swen225.gp20.maze.Maze;


/**
 * @author Marco McGowan-Arnold - 300390611
 * 
 * Graphics pane class: draws the maze and object locations onto the graphics pane
 *
 */
public class Renderer {
	MazeInterface maze;
	ViewPort viewPort;
	RenderInventory rInventory;
	RenderAudio rAudio;
	int viewX, viewY, tileSize, viewWidth, viewHeight;
	
	/**
	 * Initializes the renderer passing it the maze
	 * 
	 * @param maze 
	 * 
	 */
	public Renderer(Maze maze) {
		this.viewX = 70; 
		this.viewY = 70; 
		this.tileSize = 50; //50 is the scale that works best for the way Application is layed out, but the renderer is fully scalable
		this.viewHeight = 9;
		this.viewWidth = 9;
		
		this.maze = new MazeInterface(maze);
		RenderTreasure rTreasure = new RenderTreasure();
		RenderKey rKey = new RenderKey();
		this.viewPort = new ViewPort(rTreasure, rKey);
		this.rInventory = new RenderInventory(rTreasure, rKey);
		this.rAudio = new RenderAudio();
		
		//Plays sound for start of level
	    rAudio.playEnd();
	}
	
	/**
	 * Redraws the maze and objects
	 * 
	 * @param g the graphics pane
	 */
	public void draw(Graphics g) {
		
		Graphics2D g2 = (Graphics2D) g;
		
		//enable smooth edges
		RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		rh.put(RenderingHints.KEY_RENDERING,RenderingHints.VALUE_RENDER_QUALITY);
	    g2.setRenderingHints(rh);
	    
		//access maze and draw
	    //push matrix - viewport
		Graphics2D gTemp = (Graphics2D) g2.create();
	    viewPort.draw(g2, maze, viewX, viewY, tileSize, viewWidth, viewHeight);
	    //pop matrix
		g2.dispose();
		g2 = (Graphics2D) gTemp.create();
	    rInventory.draw(g2, maze, viewX+tileSize*viewWidth+4, viewY, tileSize/2);
	    
	    //sounds
	    playSound();
	    
	}
	
	private void playSound() {
		MazeInterface.SoundType sound = maze.getSound();
	    if(sound == null) {
	    	return;
	    }
	    
	    switch (sound) {
        	case PLAYER_MOVE: 
        		rAudio.playMove();
        		break;
        	case WALL_COLLISION: 
        		rAudio.playWall();
        		break;
        	case PICKUP_ITEM: 
        		rAudio.playItem();
        		break;
        	case PLAYER_DEATH: 
        		rAudio.playDeath();
        		break;
        	case DOOR_UNLOCK: 
        		rAudio.playUnlock();
        		break;
        	case END_LEVEL: 
        		rAudio.playEnd();
        		break;
	    }
	}
}
