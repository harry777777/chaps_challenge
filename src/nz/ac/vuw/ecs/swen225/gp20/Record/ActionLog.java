package nz.ac.vuw.ecs.swen225.gp20.Record;

import java.util.ArrayList;

public class ActionLog {
    //@todo change data type from String to relevant Datatype
    private  ArrayList<String> tickEvents; // List of events that happen on each tick of a game.

    public ArrayList<String> getTickEvents() {
        return tickEvents;
    }

    /**
     * Constructor for the log of actions throughout a game
     */
    public ActionLog(){
        this.tickEvents = new ArrayList<String>();
    }



}
