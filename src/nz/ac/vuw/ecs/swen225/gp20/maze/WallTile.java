package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * Part of a wall, actors cannot move onto those tiles.
 */
public class WallTile implements Tile {

    @Override
    public boolean isAccessible() {
        return false;
    }

    @Override
    public char getCharRep() {
        return 'W';
    }
}
