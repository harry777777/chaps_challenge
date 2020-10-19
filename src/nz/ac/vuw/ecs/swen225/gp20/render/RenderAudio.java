package nz.ac.vuw.ecs.swen225.gp20.render;
import java.io.File; 
import java.io.IOException;
import javax.sound.sampled.*; 


/**
 * @author Marco
 * 
 * Holds the logic for playing sound effects
 *
 */
public class RenderAudio {
	
	// to store current position 
    Long currentFrame; 
    Clip clip; 
      
    // current status of clip 
    String status;
    
	AudioInputStream audioInputStream; 
	//static String filePath; 

	/**
	 * 
	 * Constructor for the sound effect class
	 * 
	 * @throws UnsupportedAudioFileException
	 * @throws IOException
	 * @throws LineUnavailableException
	 */
	public RenderAudio() {

	}
	
	/**
	 * Plays a test tone
	 */
	public void playTest() {
		String soundFile = "sounds/error.wav"; 
		
		try {
			File f = new File("./" + soundFile);
		    AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(f.toURI().toURL());  
			
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	        //If you want the sound to loop infinitely, then put: clip.loop(Clip.LOOP_CONTINUOUSLY); 
	        //If you want to stop the sound, then use clip.stop();
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
          
	}
}
