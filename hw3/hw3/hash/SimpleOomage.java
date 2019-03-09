package hw3.hash;
import java.awt.Color;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdDraw;


public class SimpleOomage implements Oomage {
    protected int red;
    protected int green;
    protected int blue;

    private static final double WIDTH = 0.01;
    private static final boolean USE_PERFECT_HASH = true;

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (o.getClass() != this.getClass()) {
            return false;
        }
        SimpleOomage other = (SimpleOomage) o;
        return (this.red == other.red) && (this.green == other.green) && (this.blue == other.blue);
    }


    @Override
    public int hashCode() {
        if (!USE_PERFECT_HASH) {
            return red + green + blue;
        } else {
            int r = hashHelper(red, 1, 2, 3);
            int g = hashHelper(green, 4, 5, 6);
            int b = hashHelper(blue, 7, 8, 9);
            return r + g + b;
        }
    }

    private int hashHelper(int x, int pow1, int pow2, int pow3) {
        int a = Math.floorDiv(x, 100) * powTO(pow1);
        int b = Math.floorMod(Math.floorDiv(x, 10), 10) * powTO(pow2);
        int c = Math.floorMod(x, 10) * powTO(pow3);
        return a + b + c;
    }

    private int powTO(int pow) {
        int rv = 1;
        for (int i = 0; i < pow; i += 1) {
            rv = rv * 31;
        }
        return rv;
    }

    public SimpleOomage(int r, int g, int b) {
        if (r < 0 || r > 255 || g < 0 || g > 255 || b < 0 || b > 255) {
            throw new IllegalArgumentException();
        }
        if ((r % 5 != 0) || (g % 5 != 0) || (b % 5 != 0)) {
            throw new IllegalArgumentException("red/green/blue values must all be multiples of 5!");
        }
        red = r;
        green = g;
        blue = b;
    }

    @Override
    public void draw(double x, double y, double scalingFactor) {
        StdDraw.setPenColor(new Color(red, green, blue));
        StdDraw.filledSquare(x, y, WIDTH * scalingFactor);
    }

    public static SimpleOomage randomSimpleOomage() {
        int red = StdRandom.uniform(0, 51) * 5;
        int green = StdRandom.uniform(0, 51) * 5;
        int blue = StdRandom.uniform(0, 51) * 5;
        return new SimpleOomage(red, green, blue);
    }

    public static void main(String[] args) {
        System.out.println("Drawing 4 random simple Oomages.");
        randomSimpleOomage().draw(0.25, 0.25, 1);
        randomSimpleOomage().draw(0.75, 0.75, 1);
        randomSimpleOomage().draw(0.25, 0.75, 1);
        randomSimpleOomage().draw(0.75, 0.25, 1);
    }

    public String toString() {
        return "R: " + red + ", G: " + green + ", B: " + blue;
    }
} 
