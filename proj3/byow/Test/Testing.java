package byow.Test;

import byow.Core.Engine;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.proj3.World;
import byow.proj3.files.FileSystem;

public class Testing {
    public static void main(String[] args) {
        moveAvatar();
        moveAvatar();
        moveAvatar();
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
