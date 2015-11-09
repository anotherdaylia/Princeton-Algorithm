package com.lia.lab;

import org.junit.Test;
import edu.princeton.cs.algs4.*;

/**
 * Created by liqu on 11/8/15.
 */
public class SolverTest {

    @Test
    public void solverTest() {
        // create initial board from file
        In in = new In("src/test/resources/puzzle0e");
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

    //@Test
    public void BoardNeighborTest() {
        // create initial board from file
        In in = new In("src/test/resources/puzzle04.txt");
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println("initial:");
        StdOut.println(initial.toString());

        for( Board neighbor : initial.neighbors() ) {
            StdOut.println("neighbor:");
            StdOut.print(neighbor.toString());
        }
    }

    @Test
    public void BoardTwinTest() {
        // create initial board from file
        In in = new In("src/test/resources/puzzle04.txt");
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        StdOut.println("initial:");
        StdOut.println(initial.toString());

        Board twin = initial.twin();
        StdOut.println("twin:");
        StdOut.println(twin.toString());
    }
}
