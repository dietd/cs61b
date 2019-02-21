package creatures;

import org.junit.Test;

import static huglife.Action.ActionType.*;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestClorus {
    @Test
    public void testReplicate() {
        Clorus a = new Clorus(100);
        Clorus b = a.replicate();
        assertNotEquals(a, b);
        assertEquals(a.energy(), b.energy());
    }

    @Test public void testMove() {
        Clorus a = new Clorus(1);
        a.move();
        assertEquals(0.7, a.energy(), 0.001);
    }

    @Test public void testStay() {
        Clorus a = new Clorus(1);
        a.stay();
        assertEquals(0.9, a.energy(), 0.001);
    }

    @Test public void testAction() {
        Clorus a = new Clorus(2);

        HashMap<Direction, Occupant> pliped = new HashMap<>();
        pliped.put(Direction.TOP, new Plip(100));
        pliped.put(Direction.BOTTOM, new Plip(100));
        pliped.put(Direction.LEFT, new Plip(200));
        pliped.put(Direction.RIGHT, new Plip(100));

        Action actual = a.chooseAction(pliped);
        Action expected = new Action(STAY);
        assertEquals(expected, actual);

        pliped.replace(Direction.TOP, new Empty());
        pliped.replace(Direction.BOTTOM, new Empty());
        pliped.replace(Direction.LEFT, new Empty());
        actual = a.chooseAction(pliped);
        expected = new Action(ATTACK, Direction.RIGHT);
        assertEquals(expected, actual);

        pliped.replace(Direction.RIGHT, new Empty());
        actual = a.chooseAction(pliped);
        assertEquals(REPLICATE, actual.type);

        Clorus b = new Clorus(0.99);
        actual = b.chooseAction(pliped);
        assertEquals(MOVE, actual.type);
    }
}
