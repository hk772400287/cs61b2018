package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {

    private double[] xt;
    private int T;
    public PercolationStats(int N, int T, PercolationFactory pf) {
        // perform T independent experiments on an N-by-N grid
        if (N > 0 && T > 0) {
            this.T = T;
            xt = new double[T];
            for (int i = 0; i < T; i++) {
                Percolation per = pf.make(N);
                while (!per.percolates()) {
                    int row = StdRandom.uniform(N);
                    int col = StdRandom.uniform(N);
                    per.open(row, col);
                }
                double x = per.numberOfOpenSites() * 1.0 / (N * N);
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

//    public static void main(String[] args) {
//        // use for unit testing (not required)
//        PercolationStats per = new PercolationStats(20, 10, new PercolationFactory());
//        for (double x : per.xt) {
//            System.out.println(x);
//        }
//        System.out.println(per.mean());
//        System.out.println(per.stddev());
//    }

}
