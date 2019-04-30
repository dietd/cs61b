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
    private int index;

    public Room(Tile ll, int width, int height, int index) {
        this.ll = ll;
        this.ur = new Tile(ll.getX() + width - 1,
                ll.getY() + height - 1);
        this.width = width;
        this.height = height;
        this.index = index;
    }

    public Tile getRandomWall(Random rng) {

        int side = rng.nextInt(4);
        List<Tile> walls = new ArrayList<>();

        if (side == 0) {
            for (int y = ll.getY() + 1; y < ur.getY() - 1; y += 1) {
                walls.add(new Tile(ll.getX(), y));
            }
        } else if (side == 1) {
            for (int y = ll.getY() + 1; y < ur.getY() - 1; y += 1) {
                walls.add(new Tile(ur.getX(), y));
            }
        } else if (side == 2) {
            for (int x = ll.getX() + 1; x < ur.getX() - 1; x += 1) {
                walls.add(new Tile(x, ur.getY()));
            }
        } else {
            for (int x = ll.getX() + 1; x < ur.getX() - 1; x += 1) {
                walls.add(new Tile(x, ll.getY()));
            }
        }

        return walls.get(rng.nextInt(walls.size()));
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

    public boolean overlap(Room r) {
        return !(r.ur.getX() < ll.getX()
                || r.ur.getY() < ll.getY()
                || ur.getX() < r.ll.getX()
                || ur.getY() < r.ll.getY());
    }

    public boolean overlap(Hallway h) {
        return !(h.ur().getX() < ll.getX()
                || h.ur().getY() < ll.getY()
                || ur.getX() < h.ll().getX()
                || ur.getY() < h.ll().getY());
    }

    public boolean insideWorld() {
        return (ll.getX() >= 0
                && ur.getX() < Constants.WIDTH
                && ll.getY() >= 0
                && ur.getY() < Constants.HEIGHT);
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
