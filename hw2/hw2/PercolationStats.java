package hw2;

import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] trials;

    public PercolationStats(int N, int T, PercolationFactory pf) {

        if (N <= 0 || T <= 0) {
            throw new IllegalArgumentException();
        }

        this.trials = new double[T];

        for (int t = 0; t < T; t += 1) {

            Percolation p = pf.make(N);
            List<Integer> blocked = new ArrayList<>();
            for (int i = 0; i < (N * N); i += 1) {
                blocked.add(i);
            }

            while (!p.percolates()) {
                System.out.println(blocked.size());
                int index = StdRandom.uniform(blocked.size());
                int toOpen = blocked.get(index);
                int row = Math.floorDiv(toOpen, N);
                int col = Math.floorMod(toOpen, N);
                p.open(row, col);
                blocked.remove(index);
            }

            trials[t] = (double) p.numberOfOpenSites() / (double) (N * N);

        }
    }

    public double mean() {
        return StdStats.mean(trials);
    }

    public double stddev() {
        return StdStats.stddev(trials);
    }

    public double confidenceLow() {
        return mean() - ((1.96 * stddev()) / Math.pow((double) trials.length, .5));
    }

    public double confidenceHigh() {
        return mean() + ((1.96 * stddev()) / Math.pow((double) trials.length, .5));
    }

}
