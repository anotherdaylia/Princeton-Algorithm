package com.lia.lab.KdTree;

/**
 * Write a data type to represent a set of points in the unit square (all points have x- and y-coordinates between 0 and 1)
 * using a 2d-tree to support efficient range search (find all of the points contained in a query rectangle)
 * and nearest neighbor search (find a closest point to a query point). 2d-trees have numerous applications,
 * ranging from classifying astronomical objects to computer animation to speeding up neural networks to
 * mining data to image retrieval.
 *
 * Brute-force implementation.
 * Write a mutable data type PointSET.java that represents a set of points in the unit square.
 * Implement the following API by using a red-black BST (using either SET from algs4.jar or java.util.TreeSet).
 * Created by liqu on 3/6/16.
 */

import edu.princeton.cs.algs4.*;
import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> bst;

    // construct an empty set of points
    public PointSET() {
        bst = new TreeSet<>();
    }

    // is the set empty?
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    // number of points in the set
    public int size() {
        return bst.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        bst.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        return bst.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        for (Point2D p : bst) {
            p.draw();
        }
    }

    // all points that are inside the rectangle
    public Iterable<Point2D> range(RectHV rect) {
        if(bst.isEmpty()) return null;

        Queue<Point2D> list = new Queue<>();
        for (Point2D p : bst) {
            if(rect.contains(p)) {
                list.enqueue(p);
            }
        }
        return list;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        double min = Integer.MAX_VALUE;
        Point2D np = new Point2D(0,0);
        for (Point2D e : bst) {
            if (p.distanceTo(e) < min) {
                min = p.distanceTo(e);
                np = e;
            }
        }
        return np;
    }

    public static void main(String[] args) { }
}
