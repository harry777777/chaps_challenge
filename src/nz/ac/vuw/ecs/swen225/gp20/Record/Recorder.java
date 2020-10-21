package nz.ac.vuw.ecs.swen225.gp20.Record;

import java.io.IOException;

import nz.ac.vuw.ecs.swen225.gp20.persistence.JSONHandler;

/**
 * @author Harry Recorder object used to instantiate a recording of a match, eacg
 */
public class Recorder {

  private Recording recording;

  public Recorder(int level) {
    this.recording = new Recording("test",level);

  }

  public Recording getRecording() {
    return recording;
  }

  /**
   * @author Harry.
   * save a recording object as a JSON file
   * @param fileName the name the file will be saved as
   * @throws IOException
   */
  public void saveRecording(String fileName) throws IOException {
    JSONHandler<Recording> p = new JSONHandler<Recording>();
    p.write("recordings/" + fileName + ".json", this.recording);

  }

  /** @author Harry.
   * Add a TickEvent to a Recorders Recording object.
   * @param t The TickEvent that is being added to the recording
   */
  public void updateRecording(TickEvent t) {

    this.recording.addEvent(t);
  }


}
