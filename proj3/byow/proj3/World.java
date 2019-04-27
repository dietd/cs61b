package byow.proj3;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;

import java.io.*;
import java.util.Random;

public class World implements Serializable {

    private TETile[][] world;
    private Tile avatar;
    private int seed;
    private Random rng;

    public World(int seed) {

        this.rng = new Random(seed);

        world = new TETile[Constants.WIDTH][Constants.HEIGHT];

        for (int i = 0; i < Constants.WIDTH; i += 1) {
            for (int j = 0; j < Constants.HEIGHT; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        RoomFactory rf = new RoomFactory(world, rng);
        avatar = rf.getRandomRoom().getRandomInside(rng);
        world[avatar.getX()][avatar.getY()] = Tileset.AVATAR;
        this.seed = seed;
    }

    public TETile[][] getWorld() {
        return world;
    }

    public int getSeed() {
        return seed;
    }

    private boolean isOccupied(int x, int y) {
        return world[x][y].character() == '#';
    }

    public void moveRight() {
        int x = avatar.getX() + 1;
        int y = avatar.getY();
        if (!isOccupied(x, y)) {
            world[x][y] = Tileset.AVATAR;
            world[x - 1][y] = Tileset.FLOOR;
            avatar = new Tile(x, y);
        }
    }

    public void moveLeft() {
        int x = avatar.getX() - 1;
        int y = avatar.getY();
        if (!isOccupied(x, y)) {
            world[x][y] = Tileset.AVATAR;
            world[x + 1][y] = Tileset.FLOOR;
            avatar = new Tile(x, y);
        }
    }

    public void moveUp() {
        int x = avatar.getX();
        int y = avatar.getY() + 1;
        if (!isOccupied(x, y)) {
            world[x][y] = Tileset.AVATAR;
            world[x][y - 1] = Tileset.FLOOR;
            avatar = new Tile(x, y);
        }
    }

    public void moveDown() {
        int x = avatar.getX();
        int y = avatar.getY() - 1;
        if (!isOccupied(x, y)) {
            world[x][y] = Tileset.AVATAR;
            world[x][y + 1] = Tileset.FLOOR;
            avatar = new Tile(x, y);
        }
    }
}
