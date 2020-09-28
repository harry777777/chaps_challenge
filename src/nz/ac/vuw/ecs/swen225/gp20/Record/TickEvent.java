package nz.ac.vuw.ecs.swen225.gp20.Record;

import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;

/**
 * @author harry
 * Stores the events that occur on a game tick in order to create  a replay of a match.
 */
public class TickEvent {
    //@todo uncomment these and change them to correct fields when classes for the datatypes are created

    private Long time; // the time the tick occured, may not need
    private Direction moveDir; // direction of the move on this tick
    private boolean idle; // whether a move occured on this tick, idle == true if moveDir==null

    /**
     * Constructor for a tick Event object
     *
     * @param time the time the tick occured
     * @param d    the direction of the movement that occured on this tick, if null the tick was idle
     */
    public TickEvent(long time, Direction d) {
        this.time = time;
        this.moveDir = d;
        if (moveDir == null) {
            idle = true;
        } else {
            idle = false;
        }
    }

    /**
     * @return whether the player input was idle or not for a given tick,
     */
    public boolean idle() {
        if (moveDir == null) {
            idle = true;
        } else {
            idle = false;
        }
        return idle;
    }

    /**
     *
     * @param T Another tick event object
     * @return the difference between the times the tick events occured
     */
    public Long getDifference(TickEvent T) {
        return this.time - T.getTime();
    }

    public long getTime() {
        return this.time;
    }

    public Direction getMoveDir() {
        return this.moveDir;
    }


}

