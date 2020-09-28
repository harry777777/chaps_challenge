package nz.ac.vuw.ecs.swen225.gp20.Record;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Harry
 * A recording of a game, contains a list of tickEvent objects
 */
public class Recording {
    private ArrayList<TickEvent> tickEvents; // List of events that happening on each tick of a game.
    private String name;

    /**
     * constructor for a recording
     * @param name name of the recording
     */
    public Recording(String name){
        this.name = name;
        this.tickEvents = new ArrayList<TickEvent>();

    }

    /**
     * getter for the tickEvents list
     * @return ArrayList<tickEvent> all the tick events that have occured in a recording.
     */
    public ArrayList<TickEvent> getTickEvents() {
        return tickEvents;
    }



   public void saveRecording(){

   }

}

