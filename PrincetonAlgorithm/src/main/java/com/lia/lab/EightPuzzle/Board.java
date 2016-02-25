package com.lia.lab.EightPuzzle;

import java.util.Arrays;
import java.util.ArrayList;

/**
 * Created by liqu on 11/4/15.
 * Modified by liqu on 2/24/16.
 */
public class Board {
    private final int[][] blocks;
    private final int N;

    // construct a board from an N-by-N array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
        this.N = blocks.length;
        this.blocks = new int[N][];
        for (int i = 0; i < N; i++) {
            this.blocks[i] = Arrays.copyOf(blocks[i], N);
        }
    }

    // board dimension N
    public int dimension() {
        return this.N;
    }

    // number of blocks out of place
    public int hamming() {
        int count = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N && i + j < 2 * N - 2; j++) {
                if (blocks[i][j] != i * dimension() + j + 1) {
                    count++;
                }
            }
        }

        return count;
    }

    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
        int count = 0;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != 0) {
                    // i's goal position: (blocks[i][j] - 1) / N
                    // j's goal position: (blocks[i][j] - 1) % N
                    count += Math.abs((blocks[i][j] - 1) / N - i) + Math.abs((blocks[i][j] - 1) % N - j);
                }
            }
        }

        return count;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
        int[][] twinBlocks = new int[N][N];

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                twinBlocks[i][j] = blocks[i][j];
            }
        }

        if (twinBlocks[0][0] != 0 && twinBlocks[0][1] != 0) {
            exchange(twinBlocks, 0, 0, 0, 1);
        } else {
            exchange(twinBlocks, 1, 0, 1, 1);
        }

        return new Board(twinBlocks);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;

        Board thatBoard = (Board) y;
        if (this.dimension() != thatBoard.dimension()) {
            return false;
        }

        int[][] blocks_thatBoard = thatBoard.blocks;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] != blocks_thatBoard[i][j])
                    return false;
            }
        }

        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        int i_blank = N;
        int j_blank = N;

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (blocks[i][j] == 0) {
                    i_blank = i;
                    j_blank = j;
                }
            }
        }

        ArrayList<Board> neighbor = new ArrayList<>();

//        MinPQ<Board> pq = new MinPQ<>(new Comparator<Board>() {
//
//            @Override
//            public int compare(Board b1, Board b2) {
//                return b1.manhattan() - b2.manhattan();
//            }
//        });

        if (i_blank > 0) {
            // from upper of i_blank
            int[][] blocks_tmp = this.getBlocks();
            exchange(blocks_tmp, i_blank, j_blank, i_blank - 1, j_blank);
            //pq.insert(new Board(blocks_tmp));
            neighbor.add(new Board(blocks_tmp));
        }

        if (i_blank < (N-1)) {
            // from bottom of i_blank
            int[][] blocks_tmp = this.getBlocks();
            exchange(blocks_tmp, i_blank, j_blank, i_blank + 1, j_blank);
            neighbor.add(new Board(blocks_tmp));
        }

        if (j_blank > 0) {
            // from left of j_blank
            int[][] blocks_tmp = this.getBlocks();
            exchange(blocks_tmp, i_blank, j_blank, i_blank, j_blank - 1);
            neighbor.add(new Board(blocks_tmp));
        }

        if (j_blank < (N-1)) {
            // from right of j_blank
            int[][] blocks_tmp = this.getBlocks();
            exchange(blocks_tmp, i_blank, j_blank, i_blank, j_blank + 1);
            neighbor.add(new Board(blocks_tmp));
        }

        return neighbor;
    }

    private void exchange(int[][] blocks, int a, int b, int c, int d) {
        int tmp = blocks[a][b];
        blocks[a][b] = blocks[c][d];
        blocks[c][d] = tmp;
    }

    // string representation of this board (in the output format specified below)
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(N + "\n");

        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                str.append(String.format("%d", blocks[i][j]));
                str.append(" ");
            }
            str.append("\n");
        }

        return str.toString();
    }

    private int[][] getBlocks(){
        int[][] result = new int[N][];
        for (int i = 0; i < N; i++){
            result[i] = Arrays.copyOf(blocks[i], N);
        }
        return result;
    }

    // unit tests (not graded)
    public static void main(String[] args) {

    }
}
