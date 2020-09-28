package nz.ac.vuw.ecs.swen225.gp20.Record;

import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.persistence.JSONHandler;


import java.io.IOException;

/**
 * @author Harry
 * Recorder object used to instantiate a recording of a match, eacg
 */
public class Recorder {
    private Recording recording;

    public Recorder() {
        this.recording = new Recording("test");

    }

    public Recording getRecording() {
        return recording;
    }

    /**
     * save a recording object as a JSON file
     * @param fileName the name the file will be saved as
     * @throws IOException
     */
    public void saveRecording(String fileName) throws IOException {
        JSONHandler<Recording> p = new JSONHandler<Recording>();
        p.write("recordings/"+fileName+".json", this.recording);

    }

    /**
     *  Record a tickEvent that occurs in the game
     * @param t The TickEvent that is being added to the recording
     */
    public void updateRecording(TickEvent t){
        this.recording.addEvent(t);
    }

    public static void main(String[] args) throws IOException {
        // this is for testing recordings

        Recorder R = new Recorder();
        R.getRecording().addEvent(new TickEvent(1, Direction.UP));
        R.getRecording().addEvent(new TickEvent(2, Direction.UP));
        R.getRecording().addEvent(new TickEvent(3, Direction.DOWN));
        R.getRecording().addEvent(new TickEvent(4, Direction.LEFT));
        R.getRecording().addEvent(new TickEvent(5,Direction.RIGHT));
        R.getRecording().addEvent(new TickEvent(6,null));
        R.saveRecording("test2");
        Replay Re = new Replay("test2");
        Re.testRecording();

    }
}
