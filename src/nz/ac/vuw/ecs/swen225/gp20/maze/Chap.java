package nz.ac.vuw.ecs.swen225.gp20.maze;

/**
 * The playable character 'Chap'
 */
public class Chap implements Actor {

    private final int x;
    private final int y;

    public Chap(int y, int x) {
        this.y = y;
        this.x = x;
    }


    /**
     * @return Chap's X location.
     */
    public int getX() {
        return x;
    }

    /**
     * @return Chap's Y location.
     */
    public int getY() {
        return y;
    }

    @Override
    public char getCharRep() {
        return 'C';
    }
}
