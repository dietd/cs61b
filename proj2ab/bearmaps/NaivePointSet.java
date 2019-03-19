package bearmaps;

import java.util.List;

public class NaivePointSet implements PointSet {

    private List<Point> points;

    public NaivePointSet(List<Point> points) {
        this.points = points;
    }

    public Point nearest(double x, double y) {
        double best = Double.POSITIVE_INFINITY;
        Point nearest = null;
        Point goal = new Point(x, y);
        for (Point p : points) {
            if (Point.distance(p, goal) < best) {
                nearest = p;
                best = Point.distance(p, goal);
            }
        }
        return nearest;
    }
}
