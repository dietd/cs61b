package bearmaps.proj2c;

import bearmaps.proj2ab.Point;
import bearmaps.proj2c.utils.Constants;

import java.util.HashMap;
import java.util.Map;

public class QuadTree {

    private Node root;

    private class Node {

        private int x, y, depth;
        private Point ulpoint, lrpoint, midpoint;
        private Node ul, ur, ll, lr;

        Node(int x, int y, Point ulpoint, Point lrpoint, int depth) {
            this.x = x;
            this.y = y;
            this.ulpoint = ulpoint;
            this.lrpoint = lrpoint;
            this.midpoint = getMidpoint(ulpoint, lrpoint);
            this.depth = depth;
        }

        private int compLon(Point p) {
            return Double.compare(p.getX(), midpoint.getX());
        }

        private int compLat(Point p) {
            return Double.compare(p.getY(), midpoint.getY());
        }
    }


    public QuadTree() {

    }

    public Map<String, Object> getRasterMap(double ullon,
            double ullat, double lrlon, double lrlat, double lonDpp) {

        Map<String, Object> rasterMap = new HashMap<>();

        Point ulpoint = new Point(ullon, ullat);
        Point lrpoint = new Point(lrlon, lrlat);

        add(ulpoint, lonDpp);
        add(lrpoint, lonDpp);

        Node ultile = getNode(ulpoint, lonDpp);
        Node lrtile = getNode(lrpoint, lonDpp);

        //System.out.println(ultile.x + ", " + ultile.y);
        //System.out.println(lrtile.x + ", " + lrtile.y);

        rasterMap.put("render_grid", getGrid(ultile, lrtile));
        rasterMap.put("raster_ul_lon", ultile.ulpoint.getX());
        rasterMap.put("raster_ul_lat", ultile.ulpoint.getY());
        rasterMap.put("raster_lr_lon", lrtile.lrpoint.getX());
        rasterMap.put("raster_lr_lat", lrtile.lrpoint.getY());
        rasterMap.put("depth", ultile.depth);
        rasterMap.put("query_success", checkPoints(ulpoint, lrpoint));
        return rasterMap;
    }

    /** UTILITIES */

    private Node getNode(Point p, double lonDpp) {
        return getNode(p, lonDpp, root);
    }

    private Node getNode(Point p, double dpp, Node n) {

        if (checkDpp(n, dpp)) {
            return n;
        }

        int compLon = n.compLon(p);
        int compLat = n.compLat(p);

        Node next;

        if (compLon < 0) {
            if (compLat > 0) {
                next = n.ul;
            } else {
                next = n.ll;
            }
        } else {
            if (compLat > 0) {
                next = n.ur;
            } else {
                next = n.lr;
            }
        }

        if (next == null) {
            return n;
        }

        return getNode(p, dpp, next);
    }

    private void add(Point p, double lonDpp) {
        Point ulpoint = new Point(Constants.ROOT_ULLON, Constants.ROOT_ULLAT);
        Point lrpoint = new Point(Constants.ROOT_LRLON, Constants.ROOT_LRLAT);
        Node newNode = new Node(0, 0, ulpoint, lrpoint, 0);
        root = add(root, p, newNode, lonDpp);
    }

    private Node add(Node n, Point p, Node newNode, double dpp) {

        if (n == null) {
            n = newNode;
        }

        if (newNode.depth >= 7 || checkDpp(n, dpp)) {
            return n;
        }

        int compLon = n.compLon(p);
        int compLat = n.compLat(p);

        Node nn;

        if (compLon < 0) {
            if (compLat > 0) {

                nn = new Node(n.x * 2, n.y * 2,
                        n.ulpoint, n.midpoint, n.depth + 1);

                n.ul = add(n.ul, p, nn, dpp);

            } else {

                Point ulpoint = new Point(n.ulpoint.getX(),
                        n.midpoint.getY());
                Point lrpoint = new Point(n.midpoint.getX(),
                        n.lrpoint.getY());
                nn = new Node(n.x * 2, n.y * 2 + 1,
                        ulpoint, lrpoint, n.depth + 1);

                n.ll = add(n.ll, p, nn, dpp);

            }

        } else {

            if (compLat > 0) {

                Point ulpoint = new Point(n.midpoint.getX(),
                        n.ulpoint.getY());
                Point lrpoint = new Point(n.lrpoint.getX(),
                        n.midpoint.getY());
                nn = new Node(n.x * 2 + 1, n.y * 2,
                        ulpoint, lrpoint, n.depth + 1);

                n.ur = add(n.ur, p, nn, dpp);

            } else {

                nn = new Node(n.x * 2 + 1, n.y * 2 + 1,
                        n.midpoint, n.lrpoint, n.depth + 1);

                n.lr = add(n.lr, p, nn, dpp);
            }
        }

        return n;
    }

    private static Point getMidpoint(Point a, Point b) {

        double midlon = (a.getX() + b.getX()) / 2;
        double midlat = (a.getY() + b.getY()) / 2;

        return new Point(midlon, midlat);
    }

    private static boolean checkDpp(Node n, double dpp) {
        double currdpp = (n.lrpoint.getX() - n.ulpoint.getX()) / Constants.TILE_SIZE;
        if (currdpp < dpp) {
            return true;
        }
        return false;
    }

    private static boolean checkPoints(Point ul, Point lr) {

        if (lr.getX() <= ul.getX() || lr.getY() >= ul.getY()) {
            return false;
        }

        return checkOverlap(ul, lr);
    }

    private static boolean checkOverlap(Point ul, Point lr) {
        if (lr.getY() > Constants.ROOT_ULLAT ||
        ul.getY() < Constants.ROOT_LRLAT ||
        lr.getX() < Constants.ROOT_ULLON ||
        ul.getX() > Constants.ROOT_LRLON) {
            return false;
        }
        return true;
    }

    private static String[][] getGrid(Node ultile, Node lrtile) {

        int xindex = ultile.x;
        int yindex = ultile.y;
        int xlength = Math.abs(ultile.y - lrtile.y) + 1;
        int ylength = Math.abs(lrtile.x - ultile.x) + 1;

        String[][] grid = new String[xlength][ylength];
        int depth = ultile.depth;

        for (int r = 0; r < xlength; r += 1) {
            for (int c = 0; c < ylength; c += 1)  {
                grid[r][c] = "d" + depth + "_x" +
                         xindex + "_y" + yindex + ".png";
                xindex += 1;
            }
            yindex += 1;
            xindex = ultile.x;
        }
        return grid;
    }
}
