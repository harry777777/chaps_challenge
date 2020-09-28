package nz.ac.vuw.ecs.swen225.gp20.Record;

import nz.ac.vuw.ecs.swen225.gp20.persistence.JSONHandler;


import java.io.IOException;

/**
 * @author Harry
 * class responsible for replaying a recording of a recorded game.
 */
public class Replay {
    private Recording recording;
    private int playSpeed = 1;
    private boolean paused = true; // paused == true causes the view replay loop to pause.
    private boolean skip = false;  // skip == true causes the view replay loop to proceed one step further through the replay
    private int sleepTime = 0;

    /**
     *
     * @param fileName the name of the recording file the replay will be loading
     * @throws IOException
     */

    public Replay(String fileName) throws IOException {
        this.recording = loadRecording(fileName);
    }

    /**
     *
     * @param fileName name of the Json file in recordings folder to load
     * @return Recording of a game object loaded from a JSON file
     * @throws IOException
     */
    public Recording loadRecording(String fileName) throws IOException {
        JSONHandler<Recording> p = new JSONHandler<Recording>();
        Recording R = p.read("recordings/"+fileName+".json",Recording.class);
        return R;
    }

    /**
     * The method that runs through a recording of a game, can be paused and stepped through one game tick at a time,
     * default behaviour is to automatically play at the speed of game ticks
     */
    public void viewReplay() {
        for (TickEvent t : recording.getTickEvents()) {
            this.skip = false;
            try {
                while (paused) {  // if replay is paused wait
                    if (skip == true) { // check if the user has clicked the skip button
                        break;
                    }
                }

                Thread.sleep(sleepTime / playSpeed); //@todo set the default sleep time to the tick rate of the game when its decided
            } catch (InterruptedException ex) {
            }
           //@todo  implement the meat of the viewReplay Method, ie the part that does shit

        }


    }

    /**
     * A test method that prints a recording object to the console
     */
    public void testRecording(){
        System.out.println(this.recording.toString());
    }


    /**
     * Toggle the replay between paused and unpaused states.
     */
    public void togglePause() {
        this.paused = !paused;
    }

    /**
     * Set the skip value to true.
     */
    public void SkipPressed() {
       skip = true;
    }

}
