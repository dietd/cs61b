package bearmaps;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class KDTreeTest {

    @Test
    public void randomTest() {
        int points = 100000;
        List<Point> alp = new ArrayList<>();

        for (int i = 0; i < points; i += 1) {
            alp.add(randPoint());
        }

        NaivePointSet nps = new NaivePointSet(alp);
        KDTree kdt = new KDTree(alp);
        for (int i = 0; i < points; i += 1) {
            Point goal = randPoint();
            assertEquals(nps.nearest(goal.getX(), goal.getY()),
                    kdt.nearest(goal.getX(), goal.getY()));
        }
    }

    private Point randPoint() {
        double x = Math.random() * 1000;
        double y = Math.random() * 1000;
        return new Point(x, y);
    }
}
