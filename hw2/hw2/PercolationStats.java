package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    public double[] xt;
    public int T;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if (N > 0 && T > 0) {
            Percolation per = pf.make(N);
            this.T = T;
            xt = new double[T];
            for (int i = 0; i < T; i++) {
                while (!per.percolates()) {
                    int row = StdRandom.uniform(N);
                    int col = StdRandom.uniform(N);
                    per.open(row, col);
                }
                double x = per.numberOfOpenSites() / (N * N);
                xt[i] = x;
            }
        } else {
            throw new java.lang.IllegalArgumentException();
        }

    }

    public double mean() {
        // sample mean of percolation threshold
        return StdStats.mean(xt);
    }

    public double stddev() {
        // sample standard deviation of percolation threshold
        return StdStats.stddev(xt);
    }

    public double confidenceLow() {
        // low endpoint of 95% confidence interval
        double μ = mean();
        double σ = stddev();
        return μ - 1.96 * σ /  java.lang.Math.sqrt(T);
    }

    public double confidenceHigh() {
        // high endpoint of 95% confidence interval
        double μ = mean();
        double σ = stddev();
        return μ + 1.96 * σ /  java.lang.Math.sqrt(T);
    }


}
