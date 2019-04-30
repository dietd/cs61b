package byow.proj3.ai;

import java.util.List;

/**
 * Interface for shortest path solvers.
 * Created by hug.
 */
public interface ShortestPathsSolver<Vertex> {
    SolverOutcome outcome();
    List<Vertex> solution();
    double solutionWeight();
    int numStatesExplored();
    double explorationTime();
}