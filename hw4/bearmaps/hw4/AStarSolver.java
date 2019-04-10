package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;


public class AStarSolver<Vertex> implements ShortestPathsSolver {

    private List<Vertex> solution;
    private double solutionWeight;
    private SolverOutcome solverOutcome;
    private int numStatesExplored = 0;
    private double explorationTime;

    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {

        Stopwatch sw = new Stopwatch();

        Map<Vertex, Double> distTo = new HashMap<>();
        Map<Vertex, Vertex> prevVertex = new HashMap<>();
        Set<Vertex> visited = new HashSet<>();
        ArrayHeapMinPQ<Vertex> pq = new ArrayHeapMinPQ<>();

        pq.add(start, 0);
        distTo.put(start, 0.0);
        prevVertex.put(start, null);

        solverOutcome = SolverOutcome.UNSOLVABLE;

        while (pq.size() != 0) {

            Vertex current = pq.removeSmallest();

            if (sw.elapsedTime() > timeout) {
                solverOutcome = SolverOutcome.TIMEOUT;
                break;
            }

            if (current.equals(end)) {
                solverOutcome = SolverOutcome.SOLVED;
                break;
            }

            if (!visited.contains(current)) {

                visited.add(current);
                numStatesExplored += 1;

                for (WeightedEdge<Vertex> v : input.neighbors(current)) {

                    Vertex next = v.to();
                    Vertex prev = v.from();
                    double weight = v.weight();

                    if (!distTo.containsKey(next)) {
                        distTo.put(next, Double.POSITIVE_INFINITY);
                    }

                    double actual = distTo.get(prev) + weight;
                    double alt = actual + input.estimatedDistanceToGoal(next, end);

                    if (actual < distTo.get(next)) {
                        if (pq.contains(next)) {
                            pq.changePriority(next, alt);
                        } else {
                            pq.add(next, alt);
                        }
                        distTo.put(next, actual);
                        prevVertex.put(next, prev);
                    }
                }
            }
        }

        solution = new ArrayList<>();

        if (solverOutcome == SolverOutcome.SOLVED) {
            Vertex v = end;
            solution.add(v);
            while (v != start) {
                v = prevVertex.get(v);
                solution.add(v);
            }
            solutionWeight = distTo.get(end);
        }
        Collections.reverse(solution);

        explorationTime = sw.elapsedTime();
    }

    public SolverOutcome outcome() {
        return solverOutcome;
    }

    public List<Vertex> solution() {
        return solution;
    }

    public double solutionWeight() {
        return solutionWeight;
    }

    public int numStatesExplored() {
        return numStatesExplored;
    }

    public double explorationTime() {
        return explorationTime;
    }
}
