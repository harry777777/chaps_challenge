package nz.ac.vuw.ecs.swen225.gp20.Record;

import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;

/**
 * @author harry
 * Stores the events that occur on a game tick in order to create  a replay of a match.
 */
public class TickEvent {
    //@todo uncomment these and change them to correct fields when classes for the datatypes are created
    // private direction move;
    private int time; // the time the tick occured, may not need
    private Direction moveDir;
    private boolean idle;

    /**
     * Constructor for a tick Event object
     *
     * @param time the time the tick occured
     * @param d    the direction of the movement that occured on this tick, if null the tick was idle
     */
    public TickEvent(int time, Direction d) {
        this.time = time;
        this.moveDir = d;
    }

    /**
     *
     * @return whether the player input was idle or not for a given tick,
     */
    public boolean idle() {
        if (moveDir == null) return true;
        return false;
    }


    public Direction getMoveDir() {
        return this.moveDir;
    }


}

