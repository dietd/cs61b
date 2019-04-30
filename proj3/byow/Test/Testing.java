package byow.Test;

import byow.Core.Engine;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.proj3.Tile;
import byow.proj3.World;
import byow.proj3.ai.AStarSolver;
import byow.proj3.files.FileSystem;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;

public class Testing {
    public static void main(String[] args) {
        System.out.println(worldEquals(genWorld(), genWorld2()));
        //System.out.println(TETile.toString(genWorld()).equals(TETile.toString(genWorld2())));
        genWorld2();
    }

    public static void testEquals() {
        Tile t = new Tile(1, 1);
        System.out.println(t.hashCode() == new Tile(1, 1).hashCode());
    }

    public static void testAStar() {
        World world = new World(123);
        AStarSolver<Tile> astar = new AStarSolver<>(world.getGraph(),
                 new Tile(45, 13), new Tile(42, 10), 10000);
        System.out.println(astar);
    }

    public static TETile[][] genWorld() {
        TERenderer tr = new TERenderer();
        tr.initialize(80, 30);
        Engine e = new Engine();
        TETile[][] world = e.interactWithInputString("n12412412412412saaawasdaawdwsd");
        StdDraw.clear(Color.BLACK);
        tr.renderFrame(world);
        StdDraw.show();
        return world;
    }

    public static TETile[][] genWorld2() {
        //TERenderer tr = new TERenderer();
        //tr.initialize(80, 30);
        Engine e = new Engine();
        e.interactWithInputString("n12412412412412saaawasdaawd:q");

        TETile[][] world2 = e.interactWithInputString("lwsdad");
        //StdDraw.clear(Color.BLACK);
        //tr.renderFrame(world2);
        //StdDraw.show();
        return world2;
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

    public static boolean worldEquals(TETile[][] a, TETile[][] b) {
        if (a.length != b.length || a[0].length != b[0].length) {
            return false;
        } else {
            for (int i = 0; i < a.length; i += 1) {
                for (int j = 0; j < a[0].length; j += 1) {
                    if (!tileEquals(a[i][j], b[i][j])) {

                        System.out.println("NOT EQUAL : " + i + " " + j);
                        System.out.println("A :" + a[i][j].toString() + a[i][j].description());
                        System.out.println("B :" + b[i][j].toString() + b[i][j].description());
                        return false;
                    }
                }
            }
            return true;
        }
    }

    private static boolean tileEquals(TETile a, TETile b) {
        return a.description().equals(b.description());
    }
}
