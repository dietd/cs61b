package byow.proj3;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.Random;

public class Room {
    private Tile ll;
    private Tile ur;
    private int width;
    private int height;
    private int index;

    public Room(Tile ll, int width, int height, int index) {
        this.ll = ll;
        this.ur = new Tile(ll.getX() + width - 1,
                ll.getY() + height - 1);
        this.width = width;
        this.height = height;
        this.index = index;
    }

    public Tile getRandomInside(Random rng) {

        int x = rng.nextInt(ur.getX()
                - ll.getX() - 2) + ll.getX() + 1;
        int y = rng.nextInt(ur.getY()
                - ll.getY() - 2) + ll.getY() + 1;

        return new Tile(x, y);
    }

    public int index() {
        return index;
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

    public boolean overlap(Hallway h) {
        return !(h.ur().getX() < ll.getX() ||
                h.ur().getY() < ll.getY() ||
                ur.getX() < h.ll().getX() ||
                ur.getY() < h.ll().getY()
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