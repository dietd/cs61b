package byow.proj3;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
    private Tile ll;
    private Tile ur;
    private int width;
    private int height;
    private Hallway hallway;

    public Room(Tile ll, int width, int height) {
        this.ll = ll;
        this.ur = new Tile(ll.getX() + width - 1,
                ll.getY() + height - 1);
        this.width = width;
        this.height = height;
    }

    public Tile ll() {
        return ll;
    }

    public Tile ur() {
        return ur;
    }

    public int size() {
        return width * height;
    }

    public boolean overlap(Room r) {
        return !(r.ur.getX() < ll.getX() ||
                r.ur.getY() < ll.getY() ||
                ur.getX() < r.ll.getX() ||
                ur.getY() < r.ll.getY()
        );
    }

    public boolean insideWorld() {
        return (ll.getX() >= 0 &&
                ur.getX() < Constants.WIDTH &&
                ll.getY() >= 0 &&
                ur.getY() < Constants.HEIGHT
        );
    }

    public void putTiles(TETile[][] world) {

        for (int i = ll.getX(); i <= ur.getX(); i++) {
            for (int j = ll.getY(); j <= ur.getY(); j++) {
                world[i][j] = Tileset.WALL;
            }
        }

        for (int i = ll.getX() + 1; i <= ur.getX() - 1; i += 1) {
            for (int j = ll.getY() + 1; j <= ur.getY() - 1; j += 1) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }
}