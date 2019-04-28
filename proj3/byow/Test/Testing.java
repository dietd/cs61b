package byow.Test;

import byow.Core.Engine;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.proj3.Tile;
import byow.proj3.World;
import byow.proj3.ai.AStarSolver;
import byow.proj3.ai.WeirdSolver;
import byow.proj3.files.FileSystem;

public class Testing {
    public static void main(String[] args) {
        testAStar();
    }

    public static void testEquals() {
        Tile t = new Tile(1, 1);
        System.out.println(t.hashCode() == new Tile(1,1).hashCode());
    }

    public static void testAStar() {
        World world = new World(123);
        WeirdSolver<Tile> astar = new WeirdSolver<>(world.getGraph(), new Tile(45,13), new Tile(42,10), 10000);
        System.out.println(astar);
    }

    public static void genWorld() {
        TERenderer tr = new TERenderer();
        tr.initialize(80, 30);
        Engine e = new Engine();
        TETile[][] world = e.interactWithInputString("n1234s:q");
        tr.renderFrame(world);
    }

    public static void saveFile() {
        World world = new World(123);
        FileSystem.save(world);
    }

    public static void loadFile() {
        World world = FileSystem.load();
        System.out.println(world.getSeed());
    }

    public static void moveAvatar() {
        TERenderer tr = new TERenderer();
        tr.initialize(80, 30);
        Engine e = new Engine();
        TETile[][] world = e.interactWithInputString("lsdsd:q");
        tr.renderFrame(world);
    }
}
