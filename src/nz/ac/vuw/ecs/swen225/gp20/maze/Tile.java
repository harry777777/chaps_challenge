package nz.ac.vuw.ecs.swen225.gp20.maze;

public interface Tile {

    /**
     * @return if a tile is accessible by the Chap
     */
    boolean isAccessible();

    /**
     * @return a character representation of the tile. Used for .toString() output.
     */
    char getCharRep();

}

