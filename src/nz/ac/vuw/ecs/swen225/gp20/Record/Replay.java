package nz.ac.vuw.ecs.swen225.gp20.Record;

/**
 * @author Harry
 * class responsible for replaying a recording of a recorded game.
 */
public class Replay {
    private Recording recording;
    private int playSpeed = 1;
    private boolean paused = true; // paused == true causes the view replay loop to pause.
    private boolean skip = false;  // skip == true causes the view replay loop to proceed one step further through the replay

    public Replay(Recording recording) {
        this.recording = recording;
    }

    public void viewReplay() {
        for (TickEvent t : recording.getTickEvents()) {
            this.skip = false;
            try {
                while (paused) {  // if replay is paused wait
                    if (skip == true) { // check if the user has clicked the skip button
                        break;
                    }
                    wait();
                }
                Thread.sleep(1000 * playSpeed); //@todo set the default sleep time to the tick rate of the game when its decided
            } catch (InterruptedException ex) {
            }
           //@todo  implement the meat of the viewReplay Method, ie the part that does shit

        }
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
