package creatures;

import huglife.Action;
import huglife.Creature;
import huglife.Direction;
import huglife.Occupant;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;

import static huglife.Action.ActionType.*;
import static huglife.HugLifeUtils.randomEntry;

public class Clorus extends Creature {
    private int r, g, b;

    public Clorus(double e) {
        super("clorus");
        r = 34;
        g = 0;
        b = 231;
        energy = e;
    }

    @Override
    public void move() {
        energy -= 0.03;
        if (energy < 0) {
            energy = 0;
        }
    }

    public void stay() {
        energy -= 0.01;
        if (energy < 0) {
            energy = 0;
        }
    }


    public void attack(Creature c) {
        energy += c.energy();
    }

    public Clorus replicate() {
        energy /= 2;
        return new Clorus(energy);
    }

    public Color color() {
        return new Color(r, g, b);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plips = new ArrayDeque<>();
        for(Direction d : neighbors.keySet()) {
            if (neighbors.get(d).name().equals("empty")) {
                emptyNeighbors.add(d);
            } else if (neighbors.get(d).name().equals("plip")) {
                plips.add(d);
            }
        }

        if (emptyNeighbors.isEmpty()) {
            return new Action(STAY);
        }

        if (!plips.isEmpty()) {
            return new Action(ATTACK, randomEntry(plips));
        }

        if (energy >= 1) {
            return new Action(REPLICATE, randomEntry(emptyNeighbors));
        }

        return new Action(MOVE, randomEntry(emptyNeighbors));
    }
}
