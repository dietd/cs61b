package byow.proj3;

import java.io.Serializable;

public class Tile implements Serializable {
    private int x;
    private int y;

    public Tile(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String toString() {
        return "Tile: " + x + " " + y;
    }
}
