package com.lia.lab.KdTree;

/**
 * Created by liqu on 3/6/16.
 */

import edu.princeton.cs.algs4.*;
import java.util.ArrayList;


public class KdTree {
    public Node root;
    private ArrayList<Point2D> points;

    public class Node {
        public Point2D p;
        private boolean isVertical;
        public Node left, right;
        private int N;

        private Node(Point2D p, boolean isVertical, int N) {
            this.p = p;
            this.isVertical = isVertical;
            this.N = N;
        }

        private Node(){ }
    }

    // construct an empty set of points
    public KdTree() {
        this.points = new ArrayList<>();

    }

    // is the set empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // number of points in the set
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        else return x.N;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new NullPointerException("point to be insert is null");

        root = insert(root, p, true);
        points.add(p);
    }

    private Node insert(Node x, Point2D p, boolean isVertical) {
        if (x == null) return new Node(p, isVertical, 1);

        if (x.isVertical) { // compare x
            double cmp = p.x() - x.p.x();
            if (cmp <= 0) x.left = insert(x.left, p, !x.isVertical);
            else          x.right = insert(x.right, p, !x.isVertical);
        } else { // compare y
            double cmp = p.y() - x.p.y();
            if (cmp <= 0) x.left = insert(x.left, p, !x.isVertical);
            else          x.right = insert(x.right, p, !x.isVertical);
        }

        x.N = size(x.left) + 1 + size(x.right);
        return x;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new NullPointerException("point to check is null");

        return contains(root, p);
    }

    private boolean contains(Node x, Point2D p) {
        if (x == null) return false;
        //System.out.println("x = (" + x.p.x() + ", " + x.p.y() + ")" );
        if (p.equals(x.p)) return true;

        if(x.isVertical) { // compare x
            double cmp = p.x() - x.p.x();
            if (cmp <= 0) return contains(x.left, p);
            else          return contains(x.right, p);
        } else { // compare y
            double cmp = p.y() - x.p.y();
            //System.out.println("---");
            if (cmp <= 0) return contains(x.left, p);
            else          return contains(x.right, p);
        }
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : points) {
            p.draw();
        }
    }

    // all points that are inside the rectangle
    /* To find all points contained in a given query rectangle, start at the root and recursively
     * search for points in both subtrees using the following pruning rule:
     * if the query rectangle does not intersect the rectangle corresponding to a node,
     * there is no need to explore that node (or its subtrees).
     * A subtree is searched only if it might contain a point contained in the query rectangle.
     */
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new NullPointerException();
        Queue<Point2D> list = new Queue<>();

        range(root, rect, list);
        return list;
    }

    private void range(Node x, RectHV rect, Queue<Point2D> list) {
        if (x == null) return;
        //System.out.println("??");

        if (x.isVertical) {
            if (rect.xmax() < x.p.x()) {
                range(x.left, rect, list);
            } else if (rect.xmin() > x.p.x()) {
                range(x.right, rect, list);
            } else {
                if (rect.contains(x.p)) list.enqueue(x.p);
                range(x.left, rect, list);
                range(x.right, rect, list);
            }

        } else {
            if (rect.ymax() < x.p.y()) {
                range(x.left, rect, list);
            } else if (rect.ymin() > x.p.y()) {
                range(x.right, rect, list);
            } else {
                if (rect.contains(x.p)) list.enqueue(x.p);
                range(x.left, rect, list);
                range(x.right, rect, list);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    /* To find a closest point to a given query point, start at the root and recursively
     * search in both subtrees using the following pruning rule:
     * if the closest point discovered so far is closer than the distance between the query point
     * and the rectangle corresponding to a node, there is no need to explore that node (or its subtrees).
     * That is, a node is searched only if it might contain a point that is closer than the best one found so far.
     * The effectiveness of the pruning rule depends on quickly finding a nearby point.
     * To do this, organize your recursive method so that when there are two possible subtrees to go down,
     * you always choose the subtree that is on the same side of the splitting line as the query point as the first
     * subtree to explore—the closest point found while exploring the first subtree may enable
     * pruning of the second subtree.
     */
    public Point2D nearest(Point2D p) {
        return null;
    }

    public static void main(String[] args) {

    }
}
