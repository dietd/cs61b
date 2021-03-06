package byow.Core;

import byow.InputDemo.KeyboardInputSource;
import byow.InputDemo.StringInputDevice;
import byow.TileEngine.TERenderer;
import byow.TileEngine.TETile;
import byow.proj3.GameRenderer;
import byow.proj3.World;
import byow.proj3.files.FileSystem;
import edu.princeton.cs.algs4.Stopwatch;
import edu.princeton.cs.introcs.StdDraw;

public class Engine {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 30;
    /**
     * Method used for exploring a fresh world. This method should handle all inputs,
     * including inputs from the main menu.
     */
    public void interactWithKeyboard() {
        GameRenderer g = new GameRenderer();
        Stopwatch timer = new Stopwatch();
        KeyboardInputSource keys = new KeyboardInputSource();
        mainMenuLoop(g, keys);
        gameLoop(g, keys, timer);
    }

    private void mainMenuLoop(GameRenderer g, KeyboardInputSource keys) {

        while (true) {

            char s = keys.getNextKey();

            g.drawMainMenuFrame();

            StdDraw.pause(50);

            if (s == 'l') {
                g.setWorld(FileSystem.load());
                System.out.println("loaded world");
                break;
            }

            if (s == 'n') {

                g.drawNumber("");

                int num = 0;

                while (true) {
                    s = keys.getNextKey();
                    if (s == 's') {
                        break;
                    }
                    if (Character.isDigit(s)) {
                        num = num * 10 + Character.getNumericValue(s);
                    }
                    g.drawNumber(Integer.toString(num));
                }

                g.drawGenWorldFrame();
                g.setWorld(new World(num));
                System.out.println("the world is set");
                break;
            }

            if (s == 'm') {
                g.drawLoreFrame();
                while (true) {
                    s = keys.getNextKey();
                    if (s == '#') {
                        break;
                    }
                    g.drawLoreFrame();
                }
            }

            if (s == 'q') {
                System.out.println("exited");
                return;
            }
        }
    }

    private void gameLoop(GameRenderer g, KeyboardInputSource keys, Stopwatch timer) {
        boolean togglePaths = false;
        while (true) {
            double start = timer.elapsedTime();
            while (true) {
                StdDraw.pause(50);
                if (g.getWorld().updateDoor()) {
                    g.updateLevel();

                    if (g.getLevel() == 5) {
                        g.drawVictory();
                        interactWithKeyboard();
                    }

                    g.drawGenWorldFrame();
                    g.setWorld(g.getWorld().createNewLevel());
                }
                if (g.getWorld().updateGameOverStatus()) {
                    g.drawGameOver();
                    interactWithKeyboard();
                }

                g.drawWorld(StdDraw.mouseX(), StdDraw.mouseY(), togglePaths);
                if (StdDraw.hasNextKeyTyped()) {
                    char s = StdDraw.nextKeyTyped();
                    if (s == 'd') {
                        g.getWorld().moveRight();
                    }
                    if (s == 'a') {
                        g.getWorld().moveLeft();
                    }
                    if (s == 'w') {
                        g.getWorld().moveUp();
                    }
                    if (s == 's') {
                        g.getWorld().moveDown();
                    }
                    if (s == 'p') {
                        togglePaths = !togglePaths;
                    }
                    if (s == 'm') {
                        String name = "";
                        g.drawName(name);
                        while (true) {
                            s = keys.getNextKey();
                            if (s == '#') {
                                break;
                            }
                            if (Character.isLetter(s) || s == ' ') {
                                name += s;
                            }
                            g.drawName(name);
                        }
                        g.getWorld().setAvatarName(name);
                    }
                    if (s == ':') {
                        s = keys.getNextKey();
                        if (s == 'q') {
                            System.out.println("exited and saved");
                            FileSystem.save(g.getWorld());
                            System.exit(0);
                            //return;
                        }
                    }
                }
                double end = timer.elapsedTime();
                if (end - start > 2) {
                    g.getWorld().updateEnemy();
                    start = timer.elapsedTime();
                }
            }
        }
    }

    /**
     * Method used for autograding and testing your code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The engine should
     * behave exactly as if the user typed these characters into the engine using
     * interactWithKeyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quite save. For example,
     * if we do interactWithInputString("n123sss:q"), we expect the game to run the first
     * 7 commands (n123sss) and then quit and save. If we then do
     * interactWithInputString("l"), we should be back in the exact same state.
     *
     * In other words, both of these calls:
     *   - interactWithInputString("n123sss:q")
     *   - interactWithInputString("lww")
     *
     * should yield the exact same world state as:
     *   - interactWithInputString("n123sssww")
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */

    public TETile[][] interactWithInputString(String input) {
        // passed in as an argument, and return a 2D tile representation of the
        // world that would have been drawn if the same inputs had been given
        // to interactWithKeyboard().
        //
        // See proj3.byow.InputDemo for a demo of how you can make a nice clean interface
        // that works for many different input types.

        StringInputDevice sd = new StringInputDevice(input);

        int num = 0;
        boolean exists = false;

        World w = new World(0);

        while (sd.possibleNextInput()) {

            char s = sd.getNextKey();

            if (s == 'l') {
                w = FileSystem.load();
                exists = true;
            }

            if (exists) {

                if (s == 'd') {
                    w.moveRight();
                }

                if (s == 'a') {
                    w.moveLeft();
                }

                if (s == 'w') {
                    w.moveUp();
                }

                if (s == 's') {
                    w.moveDown();
                }

                if (s == ':') {
                    s = sd.getNextKey();
                    if (s == 'q') {
                        FileSystem.save(w);
                        break;
                    }
                }
            }
            if (!exists) {
                if (s == 'n') {
                    s = sd.getNextKey();
                    while (s != 's') {
                        num = num * 10 + Character.getNumericValue(s);
                        s = sd.getNextKey();
                    }
                    w = new World(num);
                    exists = true;
                }
                if (s == 'q') {
                    return null;
                }
            }
        }
        return w.getWorld();
    }
}
