package bearmaps.proj2ab;

import java.util.Comparator;
import java.util.List;

public class KDTree implements PointSet {

    private Node root;
    private Comparator<Point> compX = (i, j) -> Double.compare(i.getX(), j.getX());
    private Comparator<Point> compY = (i, j) -> Double.compare(i.getY(), j.getY());

    private class Node implements Comparable<Point> {
        private Point point;
        private Node left, right;
        private int depth;

        Node(Point point, int depth) {
            this.point = point;
            this.depth = depth;
        }

        double distance(Point p) {
            return Math.pow(Point.distance(this.point, p), .5);
        }

        public int compareTo(Point p) {
            if (depth % 2 == 0) {
                return compX.compare(this.point, p);
            }
            return compY.compare(this.point, p);
        }

        double bestBad(Point p) {
            if (depth % 2 == 0) {
                return Math.abs(this.point.getX() - p.getX());
            }
            return Math.abs(this.point.getY() - p.getY());
        }
    }

    public KDTree(List<Point> points) {
        for (Point p : points) {
            add(p);
        }
    }

    public Point nearest(double x, double y) {
        return nearest(root, new Point(x, y), root).point;
    }

    public Node nearest(Node n, Point goal, Node best) {

        if (n == null) {
            return best;
        }

        if (n.distance(goal) < best.distance(goal)) {
            best = n;
        }

        Node good, bad;

        if (n.compareTo(goal) <= 0) {
            good = n.left;
            bad = n.right;
        } else {
            good = n.right;
            bad = n.left;
        }

        best = nearest(good, goal, best);
        if (n.bestBad(goal) < best.distance(goal)) {
            best = nearest(bad, goal, best);
        }

        return best;
    }

    /** UTILITIES */

    private void add(Point p) {
        root = add(root, p, 0);
    }

    private Node add(Node n, Point p, int depth) {
        if (n == null) {
            return new Node(p, depth);
        }
        if (n.compareTo(p) <= 0) {
            n.left = add(n.left, p, depth + 1);
        } else {
            n.right = add(n.right, p, depth + 1);
        }
        return n;
    }
}
