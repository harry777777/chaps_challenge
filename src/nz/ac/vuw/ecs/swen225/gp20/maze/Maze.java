package nz.ac.vuw.ecs.swen225.gp20.maze;


public class Maze {
    private final int width, height;
    private final Tile[][] tiles;
    private final Chap chap;

    public Maze(int width, int height, Tile[][] tiles, Chap chap) {
        this.width = width;
        this.height = height;
        this.tiles = tiles;
        this.chap = chap;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int x = chap.getX();
        int y = chap.getY();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j == x && i == y) {
                    sb.append(chap.getCharRep());
                } else {
                    sb.append(tiles[i][j].getCharRep());
                }

            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
