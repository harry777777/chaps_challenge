package nz.ac.vuw.ecs.swen225.gp20.Record;

import java.util.ArrayList;

/**
 * @autho Harry
 * Recorder object used to instantiate a recording of a match, eacg
 *
 */
public class Recorder {
    private Recording recording;

    public Recorder() {
        this.recording = new Recording("test");

    }

    public Recording getRecording() {
        return recording;
    }


}
