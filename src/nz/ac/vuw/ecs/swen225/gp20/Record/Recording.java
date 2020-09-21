package nz.ac.vuw.ecs.swen225.gp20.Record;

import java.util.ArrayList;

public class Recording {
    private ArrayList<TickEvent> tickEvents; // List of events that happening on each tick of a game.


    public Recording(String name){

    }

    public ArrayList<TickEvent> getTickEvents() {
        return tickEvents;
    }

}
