package byow.proj3;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
    private Tile ll;
    private int width;
    private int height;
    private Hallway hallway;

    public Room(Tile ll, int width, int height) {
        this.ll = ll;
        this.width = width;
        this.height = height;
    }

    public Tile ll() {
        return ll;
    }

    public Tile ur() {
        return new Tile(ll.getX() + width - 1, ll.getY() + height - 1);
    }

    public int size() {
        return width * height;
    }

    public boolean overlap(Room r) {
        if (r.ur().getX() < ll.getX() ||
            r.ur().getY() < ll.getY() ||
            ur().getX() < r.ll.getX() ||
            ur().getY() < r.ll.getY()
        ) {
            return false;
        }
        return true;
    }

    public boolean insideWorld() {
        Room world = new Room(new Tile(0, 0), 80, 40);
        if (overlap(world)) {
            return true;
        }
        return false;
    }

    public void putTiles(TETile[][] world) {

        for (int i = ll.getX(); i<= ur().getX(); i++) {
            for(int j = ll.getY(); j <= ur().getY(); j++) {
                world[i][j] = Tileset.WALL;
            }
        }

        for (int i = ll.getX() + 1 ; i <= ur().getX() - 1; i += 1) {
            for (int j = ll.getY() + 1; j <= ur().getY() - 1; j += 1) {
                world[i][j] = Tileset.FLOOR;
            }
        }




        //Room inside = new Room(new Tile(ll.getX() + 1, ll.getY() + 1),
                //width - 1, height - 1);

        //inside.putTiles(world, Tileset.FLOOR);
    }

    public void putTiles(TETile[][] world, Random rng) {
        int x = rng.nextInt(width) - 1;
        int y = rng.nextInt(height) - 1;
        world[x][y] = Tileset.FLOWER;
    }
}
