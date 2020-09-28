package nz.ac.vuw.ecs.swen225.gp20.Record;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Harry
 * A recording of a game, contains a list of tickEvent objects
 */
public class Recording {
    private ArrayList<TickEvent> tickEvents; // List of events that happening on each tick of a game.
    private HashMap<Long,ArrayList<TickEvent>> eventMap;
    private String name;
    private ArrayList<Long> eventTimes;// these will act as the keys to the eventMap;

    /**
     * constructor for a recording
     * @param name name of the recording
     */
    public Recording(String name){
        this.name = name;
        this.tickEvents = new ArrayList<TickEvent>();
        this. eventTimes = new ArrayList<Long>();
        this.eventMap = new HashMap<Long,ArrayList<TickEvent>>();
    }

    /**
     * getter for the tickEvents list
     * @return ArrayList<tickEvent> all the tick events that have occured in a recording.
     */
    public ArrayList<TickEvent> getTickEvents() {
        return tickEvents;
    }

    /**
     *
     * @param t the tick event object that is being added to the recording
     * @param time the time the event occured
     */
    public void addEvent(TickEvent t,Long time){
        eventTimes.add(time);
        if(eventMap.containsKey(time)){
            eventMap.get(time).add(t);
           // if an event has occured at the same time as a previously logged event we add it to the list of events that occured at this time
        }else { // no events recorded at this time so initialise a new TickEvent list and populate the map
            ArrayList<TickEvent> events = new ArrayList<TickEvent>();
            events.add(t);
            eventMap.put(time,events);
        }

    }
}

