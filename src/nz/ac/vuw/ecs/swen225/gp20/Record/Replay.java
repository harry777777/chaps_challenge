package nz.ac.vuw.ecs.swen225.gp20.Record;

import nz.ac.vuw.ecs.swen225.gp20.persistence.JSONHandler;


import java.io.IOException;

/**
 * @author Harry
 * class responsible for replaying a recording of a recorded game.
 */
public class Replay {
    private Recording recording;
    private int recordingIndex;
    private TickEvent currentTickEvent;
    private boolean finished;

    /**
     * @param fileName the name of the recording file the replay will be loading
     * @throws IOException
     */

    public Replay(String fileName) throws IOException {
        this.recording = loadRecording(fileName);
        this.recordingIndex = 0;
        this.currentTickEvent = this.recording.getTickEvents().get(0);
        this.finished = false;
    }

    /**
     * @param fileName name of the Json file in recordings folder to load
     * @return Recording of a game object loaded from a JSON file
     * @throws IOException
     */
    private Recording loadRecording(String fileName) throws IOException {
        JSONHandler<Recording> p = new JSONHandler<Recording>();
        Recording R = p.read("recordings/" + fileName + ".json", Recording.class);
        return R;
    }
     /**
      * * @author Harry
     * Returns the next Tickevent of a replay object and updates the position of the replay object in its replay process
      * when finished will return null to alert application that the recording is over.
      * @return  TickEvent or null if the recording has finished.
     *
      */
    public TickEvent getNextTick(){
        if(finished){
            return null;
        }
        TickEvent t = this.currentTickEvent;
        this.iterateReplay();
        return t;

    }

    /**
     * @author Harry
     * Increment recording index, and update the current tickEvent that the recording is waiting at,
     * Checks if the recording is at the end of the recording, updates the finished boolean if this is the case
     */
    private void iterateReplay() {
        recordingIndex++;
        if(recording.getTickEvents().size()>recordingIndex) {
            this.currentTickEvent = recording.getTickEvents().get(recordingIndex);
        }
        else{ this.finished = true;
        }

    }
    public boolean isFinished(){
        return this.finished;
    }

    /** @author Harry
     * A test method that prints a recording object to the console
     */
    public void testRecording() {
        System.out.println(this.recording.toString());
    }
}


