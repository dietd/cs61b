package byow.proj3;

import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class World {

    public static void main(String[] args) {

        Random rng = new Random(190);

        TERenderer tr = new TERenderer();
        tr.initialize(Constants.WIDTH, Constants.HEIGHT);
        TETile[][] world = new TETile[Constants.WIDTH][Constants.HEIGHT];

        for (int i = 0; i < Constants.WIDTH; i += 1) {
            for (int j = 0; j < Constants.HEIGHT; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        RoomFactory rf = new RoomFactory(world, rng);

        //Hallway h1 = new Hallway(new Tile(0, 0), Hallway.hallStates.LR, 5);
        //Hallway h2 = new Hallway(new Tile(5, 0), Hallway.hallStates.UD, 5);

        //System.out.println(h1.connected(h2));

        //h1.putTiles(world);
       // h2.putTiles(world);

        tr.renderFrame(world);
    }
}
