package byow.Test;

import byow.Core.Engine;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;

public class Testing {
    public static void main(String[] args) {
        TERenderer tr = new TERenderer();
        tr.initialize(80, 30);
        Engine e = new Engine();
        TETile[][] world = e.interactWithInputString("n4825113166342950780sssssss");
        tr.renderFrame(world);
    }
}
