package hw2;

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


            while (!p.percolates()) {
                int index = StdRandom.uniform(N * N);
                int row = Math.floorDiv(index, N);
                int col = Math.floorMod(index, N);
                p.open(row, col);
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
