package byow.proj3;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;

public class Hallway {

    protected enum HallStates {
        LR, UD
    }

    private Tile ll, ur;
    private HallStates orient;
    private int dim;
    protected List<Room> rooms = new ArrayList<>();
    private int index;

    public Hallway(Tile ll, HallStates h, int dim, int index) {
        this.dim = dim;
        this.orient = h;
        this.ll = ll;
        this.index = index;
        if (h.equals(HallStates.LR)) {
            this.ur = new Tile(ll.getX() + dim, ll.getY() + 2);
        } else if (h.equals(HallStates.UD)) {
            this.ur = new Tile(ll.getX() + 2, ll.getY() + dim);
        }
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

    public boolean connected(Room r) {
        return !(r.ur().getX() - 1 < ll.getX() + 1
                || r.ur().getY() - 1 < ll.getY() + 1
                || ur.getX() - 1 < r.ll().getX() + 1
                || ur.getY() - 1 < r.ll().getY() + 1);
    }

    public boolean overlap(Hallway h) {
        return !(h.ur.getX() < ll.getX()
                || h.ur.getY() < ll.getY()
                || ur.getX() < h.ll.getX()
                || ur.getY() < h.ll.getY());
    }

    public boolean overlap(Room r) {
        return !(r.ur().getX() < ll.getX()
                || r.ur().getY() < ll.getY()
                || ur.getX() < r.ll().getX()
                || ur.getY() < r.ll().getY());
    }

    public boolean connected(Hallway h) {
        if (orient != h.orient) {
            return !(h.ur.getX() - 1 < ll.getX() + 1
                    || h.ur.getY() - 1 < ll.getY() + 1
                    || ur.getX() - 1 < h.ll.getX() + 1
                    || ur.getY() - 1 < h.ll.getY() + 1);
        }
        return false;
    }

    public boolean insideWorld() {
        return (ll.getX() >= 0
                && ur.getX() < Constants.WIDTH
                && ll.getY() >= 0
                && ur.getY() < Constants.HEIGHT);
    }

    public boolean insideRoom(Room r) {
        return (ll.getX() >= r.ll().getX()
                && ur.getX() <= r.ur().getX()
                && ll.getY() >= r.ll().getY()
                && ur.getY() <= r.ur().getY());
    }

    public void putTiles(TETile[][] world) {

        for (int i = ll.getX(); i <= ur.getX(); i++) {
            for (int j = ll.getY(); j <= ur.getY(); j++) {
                if (!world[i][j].equals(Tileset.FLOOR)) {
                    world[i][j] = Tileset.WALL;
                }
            }
        }

        for (int i = ll.getX() + 1; i <= ur.getX() - 1; i += 1) {
            for (int j = ll.getY() + 1; j <= ur.getY() - 1; j += 1) {
                world[i][j] = Tileset.FLOOR;
            }
        }
    }

    public String toString() {
        return ll.toString() + ", " + ur.toString();
    }

    /**

     public static Hallway getLine(Tile t1, Tile t2) {

     if (t1.getY() == t2.getY()) {
     if (t1.getX() < t2.getX()) {
     return new Hallway(new Tile(t1.getX(), t1.getY() - 1),
     hallStates.LR, t2.getX() - t1.getX() + 1);
     }
     return new Hallway(new Tile(t2.getX(), t2.getY() - 1),
     hallStates.LR, t1.getX() - t2.getX() + 1);

     } else if (t1.getX() == t2.getX()) {

     if (t1.getY() < t2.getY()) {
     return new Hallway(new Tile(t1.getX() - 1, t1.getY() - 1),
     hallStates.UD, t2.getY() - t1.getY() + 1);
     }
     return new Hallway(new Tile(t2.getX() - 1, t2.getY() - 1),
     hallStates.UD, t1.getY() - t2.getY() + 1);
     }
     return null;
     }
     */
}
