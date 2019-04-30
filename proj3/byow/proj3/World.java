package byow.proj3;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import byow.proj3.ai.AStarGraph;
import byow.proj3.ai.AStarSolver;
import byow.proj3.ai.TileGraph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Random;


public class World implements Serializable {

    private TETile[][] world;
    private int level = 1;
    private Tile avatar, door;
    private int numKeys;
    private String avatarName = "Duke of York";
    private int health = 5;
    private List<Tile> enemies = new ArrayList<>();
    private List<List<Tile>> enemyPaths = new ArrayList<>();
    private Set<Tile> keys = new HashSet<>();
    private int seed;
    private Random rng;
    private TileGraph graph;

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

        Room startingRoom = rf.getRandomRoom();
        int startingRoomIndex = startingRoom.index();
        this.avatar = startingRoom.getRandomInside(rng);

        this.numKeys = rng.nextInt(3) + 1;
        while (keys.size() < numKeys) {
            Tile temp = rf.getRandomRoom().getRandomInside(rng);
            if (!temp.equals(avatar)) {
                keys.add(temp);
            }
        }

        int numEnemies = rng.nextInt(2) + 1;
        while (enemies.size() < numEnemies) {

            Room enemyRoom = rf.getRandomRoom();
            int enemyRoomIndex = enemyRoom.index();
            Tile temp = enemyRoom.getRandomInside(rng);
            boolean overlapsKey = false;

            for (Tile k : keys) {
                if (k.equals(temp)) {
                    overlapsKey = true;
                }
            }

            if (enemyRoomIndex != startingRoomIndex && !overlapsKey) {
                enemies.add(temp);
                enemyPaths.add(new ArrayList<>());
            }
        }

        door = rf.getRandomRoom().getRandomWall(rng);
        while (tileEquals(world[door.getX()][door.getY()], Tileset.FLOOR)) {
            door = rf.getRandomRoom().getRandomWall(rng);
        }

        world[avatar.getX()][avatar.getY()] = Tileset.AVATAR;
        world[door.getX()][door.getY()] = Tileset.LOCKED_DOOR;

        for (Tile e : enemies) {
            world[e.getX()][e.getY()] = Tileset.FLOWER;
        }

        for (Tile k : keys) {
            world[k.getX()][k.getY()] = Tileset.KEY;
        }

        this.seed = seed;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int i) {
        this.level = i;
    }

    public World createNewLevel() {
        World w = new World(rng.nextInt());
        w.setLevel(this.level + 1);
        return w;
    }

    public int getNumKeys() {
        return numKeys;
    }

    public int getHealth() {
        return health;
    }

    public boolean updateDoor() {
        if (numKeys == 0) {
            world[door.getX()][door.getY()] = Tileset.UNLOCKED_DOOR;
        }
        return numKeys == 0 && avatar.equals(door);
    }

    public boolean updateGameOverStatus() {
        return health == 0;
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
        return isWall(x, y) || tileEquals(world[x][y], Tileset.NOTHING)
                || tileEquals(world[x][y], Tileset.LOCKED_DOOR);
    }

    private boolean isWall(int x, int y) {
        return world[x][y].description().equals("wall");
    }

    public void moveRight() {
        int x = avatar.getX() + 1;
        int y = avatar.getY();
        if (!isOccupied(x, y)) {
            if (keys.contains(new Tile(x, y))) {
                numKeys -= 1;
                keys.remove(new Tile(x, y));
                updateDoor();
            }
            world[x][y] = Tileset.AVATAR;
            world[x - 1][y] = Tileset.FLOOR;
            if (enemies.contains(new Tile(x, y))) {
                health -= 1;
                //world[x - 1][y] = Tileset.FLOWER;
            }
            avatar = new Tile(x, y);
        }
    }

    public void moveLeft() {
        int x = avatar.getX() - 1;
        int y = avatar.getY();
        if (!isOccupied(x, y)) {
            if (keys.contains(new Tile(x, y))) {
                numKeys -= 1;
                keys.remove(new Tile(x, y));
                updateDoor();
            }
            world[x][y] = Tileset.AVATAR;
            world[x + 1][y] = Tileset.FLOOR;
            if (enemies.contains(new Tile(x, y))) {
                health -= 1;
                //world[x + 1][y] = Tileset.FLOWER;
            }
            avatar = new Tile(x, y);
        }
    }

    public void moveUp() {
        int x = avatar.getX();
        int y = avatar.getY() + 1;
        if (!isOccupied(x, y)) {
            if (keys.contains(new Tile(x, y))) {
                numKeys -= 1;
                keys.remove(new Tile(x, y));
                updateDoor();
            }
            world[x][y] = Tileset.AVATAR;
            world[x][y - 1] = Tileset.FLOOR;
            if (enemies.contains(new Tile(x, y))) {
                health -= 1;
                //world[x][y - 1] = Tileset.FLOWER;
            }
            avatar = new Tile(x, y);
        }
    }

    public void moveDown() {
        int x = avatar.getX();
        int y = avatar.getY() - 1;
        if (!isOccupied(x, y)) {
            if (keys.contains(new Tile(x, y))) {
                numKeys -= 1;
                keys.remove(new Tile(x, y));
                updateDoor();
            }
            world[x][y] = Tileset.AVATAR;
            world[x][y + 1] = Tileset.FLOOR;
            if (enemies.contains(new Tile(x, y))) {
                health -= 1;
                //world[x][y] = Tileset.FLOWER;
            }
            avatar = new Tile(x, y);
        }
    }

    private boolean tileEquals(TETile a, TETile b) {
        return a.description().equals(b.description());
    }

    /** Moves enemy to inputted tile*/
    public void moveEnemy(int i, Tile t) {

        Tile enemy = enemies.get(i);

        if (tileEquals(world[t.getX()][t.getY()], Tileset.AVATAR)
            || tileEquals(world[enemy.getX()][enemy.getY()], Tileset.AVATAR)) {
            health -= 1;
        }

        if (keys.contains(enemy)) {
            world[enemy.getX()][enemy.getY()] = Tileset.KEY;
        } else {
            world[enemy.getX()][enemy.getY()] = Tileset.FLOOR;
        }

        world[t.getX()][t.getY()] = Tileset.FLOWER;
        enemies.set(i, t);
    }

    public AStarGraph<Tile> getGraph() {
        return graph;
    }

    public void updateEnemy() {
        for (int i = 0; i < enemies.size(); i += 1) {
            Tile e = enemies.get(i);
            AStarSolver<Tile> astar = new AStarSolver<>(graph, e, avatar, 20);

            if (astar.solution().size() > 1) {
                moveEnemy(i, astar.solution().get(1));
            } else if (astar.solution().size() == 1) {
                health -= 1;
            }

            enemyPaths.set(i, astar.solution());
        }
    }

    public void drawEnemyPath() {
        for (List<Tile> enemyPath : enemyPaths) {
            if (enemyPath.size() > 3) {
                for (int i = 1; i < enemyPath.size() - 1; i += 1) {
                    Tile t = enemyPath.get(i);
                    if (tileEquals(world[t.getX()][t.getY()], Tileset.HLFLOOR)) {
                        break;
                    }
                    if (!t.equals(avatar)
                        && !tileEquals(world[t.getX()][t.getY()], Tileset.FLOWER)) {
                        Tileset.HLFLOOR.draw(t.getX(), t.getY());
                    }
                }
            }
        }
    }
}
