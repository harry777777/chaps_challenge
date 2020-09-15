package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Actors can freely move onto those tiles.
 */
public class FreeTile implements Tile {
    @Override
    public boolean isAccessible() {
        return true;
    }

    @Override
    public char getCharRep() {
        return 'F';
    }
}
