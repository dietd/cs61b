package byow.proj3;

import java.io.Serializable;

public class Tile implements Serializable {
    private int x;
    private int y;
    private String id;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
        this.id = x + "," + y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getId() {
        return id;
    }

    public String toString() {
        return "Tile: " + x + " " + y;
    }

    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Tile otherTile = (Tile) other;
        return this.id.equals(otherTile.id);
    }

    public int hashCode() {
        return 31 * 31 * x + 31 * y;
    }
}
