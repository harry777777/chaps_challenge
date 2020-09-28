package nz.ac.vuw.ecs.swen225.gp20.Record;

import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;
import nz.ac.vuw.ecs.swen225.gp20.persistence.JSONParser;

import java.io.IOException;
import java.util.ArrayList;

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

    public void saveRecording(String fileName) throws IOException {
        JSONParser<Recording> p = new JSONParser<Recording>();
        p.write("recordings/"+fileName+".json", this.recording);

    }

    public static void main(String[] args) throws IOException {
        // this is for testing recordings

        Recorder R = new Recorder();
        R.getRecording().addEvent(new TickEvent(1, Direction.UP));
        R.getRecording().addEvent(new TickEvent(2, Direction.UP));
        R.getRecording().addEvent(new TickEvent(3, Direction.DOWN));
        R.getRecording().addEvent(new TickEvent(4, Direction.LEFT));
        R.getRecording().addEvent(new TickEvent(5,Direction.RIGHT));
        R.saveRecording("test2");
        Replay Re = new Replay("test2");
        Re.testRecording();

    }
}
