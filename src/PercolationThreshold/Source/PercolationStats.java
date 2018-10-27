package PercolationThreshold.Source;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private double[] tresholdSamples;
    private final double CONFIDENCE_96 = 1.96;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) throw new IllegalArgumentException();
        else {
            this.tresholdSamples = new double[trials];
            Percolation perc;
            for (int i = 0; i < trials; i++) {
                perc = new Percolation(n);
                while (!perc.percolates()) {
                    perc.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
                }
                this.tresholdSamples[i] = (double) perc.numberOfOpenSites() / n;
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(tresholdSamples);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(tresholdSamples);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return (this.mean() - ((CONFIDENCE_96 * this.stddev())/Math.sqrt(this.tresholdSamples.length)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return (this.mean() + ((CONFIDENCE_96 * this.stddev())/Math.sqrt(this.tresholdSamples.length)));
    }

    // test client (described below)
    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(n, trials);

        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println("95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats.confidenceHi() + "]");

    }
}
