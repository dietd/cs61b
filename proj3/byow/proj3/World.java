package byow.proj3;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.proj3.ai.AStarGraph;
import byow.proj3.ai.AStarSolver;
import byow.proj3.ai.TileGraph;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World implements Serializable {

    private TETile[][] world;
    private Tile avatar;
    private int health = 3;
    private String avatarName = "Duke of York";
    private Tile enemy;
    private int seed;
    private Random rng;
    private TileGraph graph;
    private List<Tile> enemyPath;


    public World(int seed) {

        this.rng = new Random(seed);

        world = new TETile[Constants.WIDTH][Constants.HEIGHT];

        for (int i = 0; i < Constants.WIDTH; i += 1) {
            for (int j = 0; j < Constants.HEIGHT; j += 1) {
                world[i][j] = Tileset.NOTHING;
            }
        }

        RoomFactory rf = new RoomFactory(world, rng);
        this.graph = new TileGraph(world);
        this.enemyPath = new ArrayList<>();

        avatar = rf.getRandomRoom().getRandomInside(rng);
        enemy = rf.getRandomRoom().getRandomInside(rng);
        world[enemy.getX()][enemy.getY()] = Tileset.FLOWER;
        world[avatar.getX()][avatar.getY()] = Tileset.AVATAR;
        this.seed = seed;
    }

    public TETile[][] getWorld() {
        return world;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String name) {
        this.avatarName = name;
    }

    public int getSeed() {
        return seed;
    }

    private boolean isOccupied(int x, int y) {
        return isWall(x, y) || world[x][y].description().equals("flower");
    }

    private boolean isWall(int x, int y) {
        return world[x][y].description().equals("wall");
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

    /** Moves enemy to inputted tile*/
    public void moveEnemy(Tile t) {
        world[enemy.getX()][enemy.getY()] = Tileset.FLOOR;
        world[t.getX()][t.getY()] = Tileset.FLOWER;
        enemy = t;
    }



    public AStarGraph<Tile> getGraph() {
        return graph;
    }

    public void updateEnemy() {
        AStarSolver<Tile> astar = new AStarSolver<>(graph, enemy, avatar, 20);
        if (astar.solution().size() > 1) {
            moveEnemy(astar.solution().get(1));
        }
        this.enemyPath = astar.solution();
    }

    public void updateHealth() {
        if (avatar.equals(enemy)) {
            health -= 1;
        }
    }

    public String getHealthString() {
        String n = "";
        for (int i = 0; i < health; i += 1) {
            n += " ❤";
        }
        if (health <= 0) {
            return "GAME OVER";
        }
        return n;
    }

    public void drawEnemyPath() {

            if (enemyPath.size() > 3) {
                for (int i = 1; i < enemyPath.size() - 1; i += 1) {
                    Tile t = enemyPath.get(i);
                    if (!t.equals(avatar) && !t.equals(enemy)) {
                        Tileset.HLFLOOR.draw(t.getX(), t.getY());
                    }
                }
            }

    }
}
