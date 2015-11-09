package com.lia.lab;

import edu.princeton.cs.algs4.MinPQ;

import java.util.*;

/**
 * Created by liqu on 11/7/15.
 */
public class Solver {
    private Board initial;
    private Board initialTwin;
    private int move = -1;
    private boolean isSolvable = false;
    //private Stack<Board> solutionStack = new Stack<>(); // solution boards
    private ArrayList<Board> solutionList = new ArrayList<>(); // solution boards
    private MinPQ<SearchNode> minPQ = new MinPQ<>(new Comparator<SearchNode>() {
        @Override
        public int compare(SearchNode node1, SearchNode node2) {
            return node1.priority - node2.priority;
        }
    });

    private class SearchNode implements Comparable<SearchNode>{
        private Board node;
        private int nodeMove;
        private int priority;
        private SearchNode parent;
        private boolean isTwin;

        public SearchNode(Board board, int nodeMove, SearchNode parent, boolean isTwin) {
            this.node = board;
            this.nodeMove = nodeMove;
            this.priority = board.manhattan() + nodeMove;
            this.parent = parent;
            this.isTwin = isTwin;
        }

        public int compareTo(SearchNode thatNode) {
            return this.priority - thatNode.priority;
        }

        public Board getBoard() {
            return node;
        }

        public int getMove() {
            return nodeMove;
        }

        public String toString() {
            StringBuilder str = new StringBuilder();

            str.append("\n");
            str.append("move = " + this.getMove() + "\n");
            str.append("priority = " + this.priority + "\n");
            str.append("istwin = " + this.isTwin + "\n");
            str.append(this.getBoard().toString());

            return str.toString();
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.initial = initial;
        System.out.println("initial: " + initial.toString());

//        this.initialTwin = initial.twin();
//        System.out.println("twin: " + initialTwin.toString());

        SearchNode initialNode = new SearchNode(initial, 0, null, false);
        //SearchNode twinNode = new SearchNode(initialTwin, 0, null, true);
        minPQ.insert(initialNode);
        //minPQ.insert(twinNode);
        solve();
    }

    private void solve() {

        while (!minPQ.min().getBoard().isGoal()) {

            SearchNode searchNode = minPQ.delMin();

            System.out.println("searchnode: " + searchNode.toString());

            int snMove = searchNode.getMove();

            for (Board child : searchNode.getBoard().neighbors() ) {
                SearchNode childNode = new SearchNode(child, snMove + 1, searchNode, searchNode.isTwin);
                // check if the node is the same as previous search node
                if (!childNode.equals(searchNode.parent)) {
                    // add the child node to minPQ
                    minPQ.insert(childNode);
                    System.out.println("child nodes: " + childNode.toString());
                    //System.out.println("minpq size: " + minPQ.size());
                }
            }
        }


        SearchNode curNode = minPQ.min();
        if (!curNode.isTwin) {
            this.isSolvable = true;
            this.move = curNode.getMove();

            while (curNode.parent != null) {
                solutionList.add(curNode.getBoard());
                curNode = curNode.parent;
            }
            solutionList.add(curNode.getBoard());
        }
    }

    // is the initial board isSolvable?
    public boolean isSolvable() {
        return isSolvable;
    }

    // min number of nodeMove to solve initial board; -1 if unsolvable
    public int moves() {
        return move;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (this.isSolvable){
            Collections.reverse(solutionList);
            return solutionList;
        }else{
            return null;
        }
    }

    // solve a slider puzzle (given below)
    public static void main(String[] args) {

    }
}