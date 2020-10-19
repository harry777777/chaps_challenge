package nz.ac.vuw.ecs.swen225.gp20.render;
import java.io.File; 
import javax.sound.sampled.*; 


/**
 * @author Marco
 * 
 * Holds the logic for playing sound effects
 *
 */
public class RenderAudio {
	private String move = "sounds/move.wav"; 
	private String item = "sounds/item.wav";
	private String death = "sounds/death.wav"; 
	private String end = "sounds/levelend.wav"; 
	private String wall = "sounds/wall.wav";
	private String unlock = "sounds/unlock.wav"; 
	
	/**
	 * Plays the move sound effect
	 */
	public void playMove() {
		playSound(move);
	}
	
	/**
	 * Plays the collect item sound effect
	 */
	public void playItem() {
		playSound(item);
	}
	
	/**
	 * Plays the player death sound effect
	 */
	public void playDeath() {
		playSound(death);
	}
	
	/**
	 * Plays the end of level sound effect
	 */
	public void playEnd() {
		playSound(end);
	}
	
	/**
	 * Plays the player colliding with wall sound effect
	 */
	public void playWall() {
		playSound(wall);
	}
	
	/**
	 * Plays the door unlocking sound effect
	 */
	public void playUnlock() {
		playSound(unlock);
	}
	
	private void playSound(String sound) {
		try {
			File f = new File("./" + sound);
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(f.toURI().toURL());  
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	        //clip.loop(Clip.LOOP_CONTINUOUSLY); 
	        //clip.stop();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}
}
