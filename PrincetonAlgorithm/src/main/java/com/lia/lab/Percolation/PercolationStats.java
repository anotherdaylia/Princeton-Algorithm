package com.lia.lab.Percolation;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

/**
 * Created by liqu on 1/30/16.
 */
public class PercolationStats {
    //private Percolation perc;
    private int T; // # of experiments
    private double[] fractions;

    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T) {
        if ( N <= 0 || T <= 0 ){
            throw new java.lang.IllegalArgumentException();
        }

        this.T = T;
        fractions = new double[T];

        for ( int k = 0; k < T; k++ ) {
            //System.out.println("k = " + k);
            double count = 0;
            Percolation perc = new Percolation(N);
            while ( !perc.percolates() ) {
                int i = StdRandom.uniform(1, N+1);
                int j = StdRandom.uniform(1, N+1);
                if ( perc.isOpen(i, j) ) continue;
                perc.open(i, j);
                count++;
            }
            fractions[k] = count / (N*N);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(fractions);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(fractions);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {

        return ( mean() - 1.96 * stddev() / Math.sqrt(T) );
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

        return (mean() + 1.96 * stddev() / Math.sqrt(T) );
    }

    // test client (described below)
    public static void main(String[] args) {

        PercolationStats percStats =  new PercolationStats(200, 100);
        System.out.println("mean = " + percStats.mean());
        System.out.println("stddev = " + percStats.stddev());
        System.out.println("95% confidence interval = " + percStats.confidenceLo()
                + ", " + percStats.confidenceHi() );

    }
}
