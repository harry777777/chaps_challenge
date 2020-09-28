package nz.ac.vuw.ecs.swen225.gp20.Record;

import nz.ac.vuw.ecs.swen225.gp20.maze.utils.Direction;

/**
 * @author harry
 * Stores the events that occur on a game tick in order to create  a replay of a match.
 */
public class TickEvent {
    //@todo uncomment these and change them to correct fields when classes for the datatypes are created

    private int tick; // the time the tick occured, may not need
    private Direction moveDir; // direction of the move on this tick
    private boolean idle; // whether a move occured on this tick, idle == true if moveDir==null

    /**
     * Constructor for a tick Event object
     *
     * @param tick the time the tick occured
     * @param d    the direction of the movement that occured on this tick, if null the tick was idle
     */
    public TickEvent(int tick, Direction d) {
        this.tick = tick;
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


    public int getTick() {
        return this.tick;
    }

    public Direction getMoveDir() {
        return this.moveDir;
    }


}

