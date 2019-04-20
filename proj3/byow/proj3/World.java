package byow.proj3;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class World {

    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;

    public static void main(String[] args) {

        Random rng = new Random(123);

        TERenderer tr = new TERenderer();
        tr.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];

        for (int i = 0; i < WIDTH; i += 1) {
            for (int j = 0; j < HEIGHT; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        RoomFactory rf = new RoomFactory(rng, WIDTH, HEIGHT);

        while (rf.percentCovered() < 0.5) {
            rf.genRoom().putTiles(world);
        }

        System.out.println("Percent: " + rf.percentCovered());

        tr.renderFrame(world);
    }
}
