package com.lia.lab;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by liqu on 1/30/16.
 */
public class Percolation {
    int N; // Dimensions of the N by N grid
    boolean[] sites; // keep track if the site is open
    WeightedQuickUnionUF unionUF;
    int topSite;
    int bottomSite;

    // create N-by-N grid, with all sites blocked
    public Percolation(int N) {
        if (N < 0) {
            throw new java.lang.IllegalArgumentException(
                    "Argument N must be greater than 0."
            );
        }

        this.N = N;

        // topsite and bottomsite are imaginary sites located above and below the grid
        // each connects to all the sites immediately above or below it.
        // This simplifies the pathfinding process.
        this.topSite = 0;
        this.bottomSite = N * N + 1;
        this.unionUF = new WeightedQuickUnionUF( N * N + 2 ); // site + two imaginary sites
        this.sites = new boolean[ N * N ];

    }

    // open site (row i, column j) if it is not open already
    public void open (int i, int j) {
        checkInput(i, j);
        int ix = getIndex(i-1, j-1);

        // 1. open site (i, j)
        sites[ix] = true;

        // 2. union site to topsite if it's on the 1st row
        if ( isTopSite(ix) ) { unionUF.union(ix, topSite);}

        // 3. union site to bottomsite if it's on the last row
        if ( isBottomSite(ix) ) { unionUF.union(ix, bottomSite);}

        // 4. union to the site's open neighbors
        if ( i - 1> 0 && isOpen( i - 1, j )) { // up
            unionUF.union(ix, getIndex(i-1, j));
        }

        if ( i + 1< N && isOpen( i + 1, j )) { // down
            unionUF.union(ix, getIndex( i + 1, j));
        }

        if ( j - 1> 0 && isOpen( i, j - 1 )) { // left
            unionUF.union(ix, getIndex(i, j - 1));
        }

        if ( j + 1< N && isOpen( i, j + 1 )) { // right
            unionUF.union(ix, getIndex(i, j + 1));
        }

    }

    private void checkInput ( int i, int j ) {
        if ( i < 1 || i > N ) {
            throw new java.lang.IndexOutOfBoundsException(
                    "i must be between 1 and "+ N );
        }
        if ( j < 1 || j > N ) {
            throw new java.lang.IndexOutOfBoundsException(
                    "j must be between 1 and "+ N );
        }
    }

    private int getIndex (int i, int j) {
        return ( i * N + j + 1); // The  +1 compensates the topsite in the beginning
    }

    // is site (row i, column j) open?
    public boolean isOpen (int i, int j) {
        checkInput(i, j);
        int ix = getIndex(i-1, j-1);

        return sites[ix] == true;
    }

    // is site (row i, column j) full?
    // equivalence: is site (row i, column j) connected to sites[0][j]?
    public boolean isFull (int i, int j) {
        checkInput(i, j);

        return unionUF.connected(getIndex(i - 1, j - 1), topSite);
    }

    // does the system percolate?
    // equivalence: if the top site is connected to the bottom site?
    public boolean percolates() {
        return unionUF.connected(topSite, bottomSite);
    }

    private boolean isTopSite(int ix) {
        return ix <= N;
    }

    private boolean isBottomSite(int ix) {
        return ix >= (N - 1) * N + 1;
    }

    // test client (optional)
    public static void main(String[] args) {
        int tests = 10;
        int N = 5;
        Percolation perc = new Percolation(N);

        for ( int k = 1; k <= tests; k++ ) {
            int count = 0;
            while (!perc.percolates()) {
                int i = StdRandom.uniform(1, N*N);
                int j = StdRandom.uniform(1, N*N);
                perc.open(i, j);
                count++;
            }
        }

    }

}

