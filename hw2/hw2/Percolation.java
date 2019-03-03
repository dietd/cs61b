package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.HashSet;
import java.util.Set;

public class Percolation {

    private int openSites = 0;
    private int size;
    private int N;
    private WeightedQuickUnionUF gridUnions;
    private Set<Integer> bottomRow = new HashSet<>();
    private boolean percolates = false;
    private boolean[][] grid;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {

        if (N <= 0) {
            throw new IllegalArgumentException();
        }

        this.N = N;
        size = N * N;
        gridUnions = new WeightedQuickUnionUF(size + 1);
        grid = new boolean[N][N];
        for(int i = 0; i < N; i += 1) {
            gridUnions.union(i, size);
        }
    }

    //returns false if out of bounds
    private boolean check(int num) {
        if (num >= N || num < 0) {
            return false;
        }
        return true;
    }

    //throws exception
    private void outofBounds(int num) {
        if (!check(num)) {
            throw new IndexOutOfBoundsException();
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {

        outofBounds(row);
        outofBounds(col);

        if (!isOpen(row, col)) {
            grid[row][col] = true;
            openSites += 1;

            if (check(row - 1)) {
                if (isOpen(row - 1, col)) {
                    gridUnions.union(row * N + col,(row - 1) * N + col);

                }
            }

            if (check(row + 1)) {
                if (isOpen(row + 1, col)) {
                    gridUnions.union(row * N + col, (row + 1) * N + col);
                }
            }

            if (check(col + 1)) {
                if (isOpen(row, col + 1)) {
                    gridUnions.union(row * N + col, row * N + col + 1);
                }
            }

            if (check(col - 1)) {
                if (isOpen(row, col - 1)) {
                    gridUnions.union(row * N + col, row * N + col - 1);
                }
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        outofBounds(row);
        outofBounds(col);

        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        outofBounds(row);
        outofBounds(col);

        boolean full = gridUnions.connected(size, row * N + col) && grid[row][col];

        if (row == N - 1) {
            if (full) {
                percolates = true;
            }
        }

        return full;
    }

    // number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolates;
    }

    public static void main(String[] args) {

    }
}
