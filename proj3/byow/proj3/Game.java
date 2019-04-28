package byow.proj3;

import byow.TileEngine.TERenderer;
import byow.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;

public class Game {
    private World world;
    private boolean isSet;
    private TERenderer tr = new TERenderer();
    private Font boldFont = new Font("Monaco", Font.BOLD, 30);
    private Font hudFont = new Font("Monaco", Font.BOLD, 17);
    private Font gameFont = new Font("Monaco", Font.BOLD, 14);

    public Game() {
        StdDraw.setCanvasSize(Constants.PIXELW, Constants.PIXELH + 4 * 16);
        StdDraw.setFont(boldFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setXscale(0, Constants.PIXELW);
        StdDraw.setYscale(0, Constants.PIXELH);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        drawMainMenuFrame();
    }

    public void setWorld(World w) {
        this.world = w;
        isSet = true;
    }

    public World getWorld() {
        return this.world;
    }

    public void initializeTRenderer() {
        tr.initialize(Constants.WIDTH, Constants.HEIGHT);
    }

    public void drawWorld(double x, double y) {

        if (isSet) {

            x = (int) x;
            y = (int) y;

            clear();

            if (x >= Constants.WIDTH) {
                x = Constants.WIDTH - 1;
            }

            if (y >= Constants.HEIGHT) {
                y = Constants.HEIGHT - 1;
            }

            StdDraw.setFont(gameFont);
            this.tr.renderFrame(this.world.getWorld());

            //Put the drawn paths

            this.world.drawEnemyPath();

            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setFont(hudFont);
            StdDraw.text(5, 29, this.world.getWorld()[(int) x][(int) y].description());

            show();
        }
    }


    public void drawMainMenuFrame() {
        clear();
        StdDraw.text(Constants.PIXELW / 2, 3 * Constants.PIXELH / 4, "CS61B The Game");
        StdDraw.text(Constants.PIXELW / 2, Constants.PIXELH / 2, "New World (N)");
        StdDraw.text(Constants.PIXELW / 2, 1.5 * Constants.PIXELH / 4, "Load World (L)");
        StdDraw.text(Constants.PIXELW / 2, Constants.PIXELH / 4, "Quit World (Q)");
        show();
    }

    public void drawNumber(String num) {
        clear();
        StdDraw.text(Constants.PIXELW / 2, 3 * Constants.PIXELH / 4, "Please enter a seed and press 's'");
        StdDraw.text(Constants.PIXELW / 2, Constants.PIXELH / 2, "[" + num + "]");
        show();
    }

    public void drawGenWorldFrame(String num) {
        clear();
        StdDraw.text(Constants.PIXELW, 3 * Constants.PIXELH / 4, "Generating world");
        show();
    }

    public void clear() {
        StdDraw.clear(Color.BLACK);
    }

    public void show() {
        StdDraw.show();
    }
}
