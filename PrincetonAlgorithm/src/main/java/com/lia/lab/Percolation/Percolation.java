package com.lia.lab.Percolation;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

/**
 * Created by liqu on 1/30/16.
 */
public class Percolation {
    private int N; // Dimensions of the N by N grid
    private boolean[] sites; // keep track if the site is open
    private WeightedQuickUnionUF unionUF;
    private int topSite;
    private int bottomSite;

    // create N-by-N grid, with all sites blocked
    // 2dArr[row][col]
    public Percolation( int N ) {
        if ( N <= 0 ) {
            throw new java.lang.IllegalArgumentException (
                    "Argument N must be greater than 0."
            );
        }

        this.N = N;

        // topsite and bottomsite are imaginary sites located above and below the grid
        // each connects to all the sites immediately above or below it.
        // This simplifies the pathfinding process.
        this.topSite = N * N;
        this.bottomSite = N * N + 1;
        this.unionUF = new WeightedQuickUnionUF( N * N + 2 ); // site + two imaginary sites
        this.sites = new boolean[ N * N ];

    }

    // open site (row i, column j) if it is not open already
    public void open ( int i, int j ) {
        checkInput ( i, j );
        int ix = getIndex( i-1, j-1 );

        // 1. open site (i, j)
        sites[ix] = true;

        // 2. union site to top site if it's on the 1st row
        if ( isTopSite( ix ) ) { unionUF.union( ix, topSite ); }

        // union site to bottom site if it's full
        if ( isBottomSite( ix ) ) {
            if( isFull ( i, j ) ) {
                unionUF.union( ix, bottomSite );
            }
        }

        // 3. union to the site's open neighbors
        boolean hasNeighbor = false;
        if ( i > 1 && isOpen( i - 1, j ) ) { // up
            hasNeighbor = true;
            unionUF.union( ix, getIndex( i - 2, j - 1 ) ); // -1 when getting 1d index
        }

        if ( i < N && isOpen( i + 1, j ) ) { // down
            hasNeighbor = true;
            unionUF.union( ix, getIndex( i, j - 1) );
        }

        if ( j > 1 && isOpen( i, j - 1 ) ) { // left
            hasNeighbor = true;
            unionUF.union( ix, getIndex( i - 1, j - 2) );
        }

        if ( j < N && isOpen( i, j + 1 ) ) { // right
            hasNeighbor = true;
            unionUF.union( ix, getIndex( i - 1, j) );
        }

        // 4. union site to bottomsite if it's connected to the top site!!
        if ( hasNeighbor ) {
            updateBottom ();
        }
    }

    // union full site on the lst row to bottom site.
    private void updateBottom () {
        for ( int k = 1; k <= N; k++ ) {
//            if ( isOpen( N, k ) && isFull( N, k ) ) {
//                unionUF.union( getIndex(N-1,k-1), bottomSite );
//            }
            if ( isOpen( N, k ) && unionUF.connected( getIndex( N - 1,k - 1 ), topSite ) ) {
                unionUF.union( getIndex( N-1, k-1 ), bottomSite );
            }
        }
    }

    private void checkInput ( int i, int j ) {
        if ( i < 1 || i > N ) {
            throw new java.lang.IndexOutOfBoundsException (
                    "i must be between 1 and "+ N );
        }
        if ( j < 1 || j > N ) {
            throw new java.lang.IndexOutOfBoundsException (
                    "j must be between 1 and "+ N );
        }
    }

    private int getIndex (int i, int j) {
        //checkInput(i, j);
        return ( i * N + j );
    }

    // is site (row i, column j) open?
    public boolean isOpen ( int i, int j ) {
        checkInput ( i, j );
        int ix = getIndex ( i-1, j-1 );

        return sites[ix] == true;
    }

    // is site (row i, column j) full?
    // equivalence: is site (row i, column j) connected to sites[0][j]?
    public boolean isFull ( int i, int j ) {
        checkInput (i, j);

        return unionUF.connected ( getIndex( i - 1, j - 1 ), topSite );
    }

    // does the system percolate?
    // equivalence: if the top site is connected to the bottom site?
    public boolean percolates () {
        return unionUF.connected(topSite, bottomSite);
    }

    private boolean isTopSite ( int ix ) { return ix < N; }

    public boolean isBottomSite ( int ix ) {
        return ix >= ( N - 1 ) * N;
    }

    // test client (optional)
    public static void main (String[] args) {

    }

}

