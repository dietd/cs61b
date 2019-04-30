package byow.proj3;

import byow.TileEngine.TERenderer;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Font;
import java.awt.Color;


public class GameRenderer {
    private World world;
    private boolean isSet;
    private TERenderer tr = new TERenderer();
    private Font boldFont = new Font("Monaco", Font.BOLD, 30);
    private Font giantFont = new Font("Monaco", Font.BOLD, 40);
    private Font hudFont = new Font("Monaco", Font.BOLD, 17);
    private Font gameFont = new Font("Monaco", Font.BOLD, 14);

    public GameRenderer() {
        StdDraw.setCanvasSize(Constants.PIXELW, Constants.PIXELH + 32);
        StdDraw.setFont(boldFont);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setXscale(0, Constants.WIDTH);
        StdDraw.setYscale(0, Constants.HEIGHT + 2);
        StdDraw.clear(Color.BLACK);
        StdDraw.enableDoubleBuffering();
        drawMainMenuFrame();
    }

    public void updateLevel() {
        //world.setLevel(world.getLevel() + 1);
    }

    public int getLevel() {
        if (isSet) {
            return world.getLevel();
        } else {
            return 1;
        }
    }

    public void drawGameOver() {
        clear();
        StdDraw.setFont(giantFont);
        StdDraw.text(40, 15, "YOU GOT CAPTURED");
        show();
        StdDraw.pause(3000);
    }

    public void drawVictory() {
        clear();
        StdDraw.setFont(giantFont);
        StdDraw.setPenColor(Color.white);
        StdDraw.text(40, 15, "YOU HAVE ESCAPED");
        show();
        StdDraw.pause(3000);
    }

    public void setWorld(World w) {
        this.world = w;
        isSet = true;
    }

    public World getWorld() {
        return this.world;
    }

    public void drawWorld(double x, double y, boolean toggle) {

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

            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setFont(hudFont);

            StdDraw.text(5, 31, this.world.getAvatarName());
            for (int i = 0; i < this.world.getHealth(); i += 1) {
                StdDraw.text(10 + i, 31, " ❤");
            }

            StdDraw.text(20, 31, "Keys Left: " + world.getNumKeys());
            StdDraw.text(60, 31, "Controls: (M) Rename (P) Paths");
            StdDraw.text(76, 31, this.world.getWorld()[(int) x][(int) y].description());

            StdDraw.setFont(gameFont);
            this.tr.renderFrame(this.world.getWorld());

            if (toggle) {
                this.world.drawEnemyPath();
            }

            show();
        }
    }

    public void drawMainMenuFrame() {
        clear();
        StdDraw.setFont(giantFont);
        StdDraw.text(40, 25, "War of the Roses");
        StdDraw.setFont(boldFont);
        StdDraw.text(40, 21, "New World (N)");
        StdDraw.text(40, 18, "Load World (L)");
        StdDraw.text(40, 15, "Quit World (Q)");
        StdDraw.text(40, 12, "Lore (M)");
        show();
    }

    public void drawNumber(String num) {
        clear();
        StdDraw.setFont(boldFont);
        StdDraw.text(40, 24, "Please enter a seed and press 's'");
        StdDraw.text(40, 15, "[" + num + "]");
        show();
    }

    public void drawName(String name) {
        clear();
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(boldFont);
        StdDraw.text(40, 20, "Input name then press '#'");
        StdDraw.text(40, 15, "[" + name + "]");
        show();
    }

    public void drawLoreFrame() {
        clear();
        StdDraw.setFont(hudFont);
        StdDraw.text(40, 31, "War of the Roses");
        StdDraw.text(40, 29, "It’s 1484 and the “War of the Roses” is in "
                + "full swing as the House of York — the “white roses”");
        StdDraw.text(40, 27, "— and the House of Lancaster — the “red roses”"
                + " — fight for the British throne.");
        StdDraw.text(40, 25, "You’re a Yorkist spy who snuck into the "
                + "Tower of London to steal the English Crown. ");
        StdDraw.text(40, 23, "You’ve found the crown, but now you’re being chased by "
                + "loyal Lancasters through the treacherous hallsof the Tower. ");
        StdDraw.text(40, 21, "You must evade the the guards through three levels of the castle, "
                + "using keys to unlock secret passages to the next floor. ");
        StdDraw.text(40, 19, "You must avoid capture at all costs, "
                + "with only three magic hearts to protect you.");
        StdDraw.text(40, 17, "If you run out of hearts, you’ll be captured and "
                + "thrown into the dungeons of the Tower — never to be seen again.");
        StdDraw.text(40, 15, "Press '#' to exit");
        show();
    }

    public void drawGenWorldFrame() {
        String bar = "";
        int i = 0;
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(boldFont);

        while (i < 5) {
            clear();
            StdDraw.text(40, 20, "LEVEL " + getLevel());
            bar += "██";
            StdDraw.text(40, 15, bar);
            StdDraw.pause(300);
            show();
            i += 1;
        }
    }

    public void clear() {
        StdDraw.clear(Color.BLACK);
    }

    public void show() {
        StdDraw.show();
    }
}
