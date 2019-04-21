package byow.proj3;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

public class Hallway {

    protected enum hallStates {
        LR, UD
    }

    private Tile ll, ur;
    private hallStates orient;
    private int dim;

    public Hallway(Tile ll, hallStates h, int dim) {
        this.dim = dim;
        this.orient = h;
        this.ll = ll;
        if (h.equals(hallStates.LR)) {
            this.ur = new Tile(ll.getX() + dim, ll.getY() + 2);
        } else if (h.equals(hallStates.UD)) {
            this.ur = new Tile(ll.getX() + 2, ll.getY() + dim);
        }
    }

    public int size() {
        return 3 * (dim + 1);
    }

    public boolean overlap(Room r) {
        return !(r.ur().getX() - 1 < ll.getX() + 1 ||
                r.ur().getY() - 1 < ll.getY() + 1 ||
                ur.getX() - 1 < r.ll().getX() + 1 ||
                ur.getY() - 1 < r.ll().getY() + 1
        );
    }

    public boolean overlap(Hallway h) {
        if (orient != h.orient) {
            return false;
        }
        return !(h.ur.getX() < ll.getX() ||
                h.ur.getY() < ll.getY() ||
                ur.getX() < h.ll.getX() ||
                ur.getY() < h.ll.getY()
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
}
