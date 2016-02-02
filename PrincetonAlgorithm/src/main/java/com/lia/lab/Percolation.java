package com.lia.lab;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
/**
 * Created by liqu on 1/30/16.
 */
public class Percolation {
    int[][] sites;
    int[][] fullness;
    int[][] size;
    int N;

    // create N-by-N grid, with all sites blocked
    // When p equals 0, the system does not percolate; when p equals 1, the system percolates.
    public Percolation(int N) {
        if (N < 0) {
            throw new java.lang.IllegalArgumentException();
        }

        this.N = N;
        this.sites = new int[N][N];
        this.fullness = new int[N][N];
        this.size = new int[N][N];

        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++){
                sites[i][j] = 0; // 0 means blocked, 1 means open
                fullness[i][j] = 0; // 0 means empty, 1 means full
                size[i][j] = 1;
            }
        }
    }

    // open site (row i, column j) if it is not open already
    public void open(int i, int j) {
        if (i < 1 || i > N || j < 1 || j > N) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        if ( !isOpen(i, j) ) {
            sites[i-1][j-1] = 1;
        }

        if ( i == 1 && j == 1 ){
            fullness[i-1][j-1] = 1;
            union(sites[i-1][j-1], )
        }


    }

    private void union (int[][] s1, int[][] s2) {

    }

    // is site (row i, column j) open?
    public boolean isOpen(int i, int j) {
        if ( i < 1 || i > N || j < 1 || j > N ) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        return sites[i-1][j-1] == 1;
    }

    // is site (row i, column j) full?
    // equivalence: is site (row i, column j) connected to sites[0][j]?
    public boolean isFull(int i, int j) {
        if ( i < 1 || i > N || j < 1 || j > N ) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        for ( int k = 0; k < N - 1; k++ ) {

        }

        return false;
    }

    // does the system percolate?
    // equivalence: is any of the site in sites[N-1][j] a full site?
    public boolean percolates() {
        return false;
    }

    // test client (optional)
    public static void main(String[] args) {

    }

}
