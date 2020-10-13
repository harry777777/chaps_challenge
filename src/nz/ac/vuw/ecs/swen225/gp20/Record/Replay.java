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
     * Returns the next Tickevent of a replay object and updates the position of the replay object in its replay process
      *
     *
      */
    public TickEvent getNextTick(){
        TickEvent t = this.currentTickEvent;
        this.iterateReplay();
        return t;
    }

    /**
     * Increment recording index, and update the current tickEvent that the recording is waiting at.
     */
    private void iterateReplay() {
        recordingIndex++;
        if(recording.getTickEvents().get(recordingIndex)!= null) {
            this.currentTickEvent = recording.getTickEvents().get(recordingIndex);
        }
        else{ this.finished = true;
        }

    }

    /**
     * A test method that prints a recording object to the console
     */
    public void testRecording() {
        System.out.println(this.recording.toString());
    }
}


