package com.lia.lab;

/******************************************************************************
 *  Compilation:  javac RangeSearchVisualizer.java
 *  Execution:    java RangeSearchVisualizer input.txt
 *  Dependencies: PointSET.java KdTree.java
 *
 *  Read points from a file (specified as a command-line arugment) and
 *  draw to standard draw. Also draw all of the points in the rectangle
 *  the user selects by dragging the mouse.
 *
 *  The range search results using the brute-force algorithm are drawn
 *  in red; the results using the kd-tree algorithms are drawn in blue.
 *
 ******************************************************************************/

import com.lia.lab.KdTree.PointSET;
import com.lia.lab.KdTree.KdTree;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import org.junit.Test;
import java.util.*;

public class kdTreeTest {

    //Test
    public void insertTest() {
        KdTree kdtree = new KdTree();
        System.out.println("size: " + kdtree.size());
        kdtree.insert(new Point2D(0.5, 0.5));
        kdtree.insert(new Point2D(0.4, 0.4));
        kdtree.insert(new Point2D(0.3, 0.3));
        kdtree.insert(new Point2D(0.7, 0.7));
        kdtree.insert(new Point2D(0.6, 0.6));
        kdtree.insert(new Point2D(0.8, 0.8));
        System.out.println("size: " + kdtree.size());
    }

    //Test
    public void containTest() {
        KdTree kdtree = new KdTree();
        Point2D p = new Point2D(0.5, 0.5);
        System.out.println("before insertion: ");
        System.out.println("contain (0.5, 0.5)? " + kdtree.contains(p));
        System.out.println("isEmpty: " + kdtree.isEmpty());

        kdtree.insert(new Point2D(0.5, 0.5));
        kdtree.insert(new Point2D(0.4, 0.4));
        kdtree.insert(new Point2D(0.3, 0.3));
        kdtree.insert(new Point2D(0.7, 0.7));
        kdtree.insert(new Point2D(0.6, 0.6));
        kdtree.insert(new Point2D(0.8, 0.8));
        System.out.println("after insertion: ");
        System.out.println("root.left: " + kdtree.root.left.p.x() + ", " + kdtree.root.left.p.y());
        System.out.println("contain (0.5, 0.5)? " + kdtree.contains(new Point2D(0.5, 0.5)));
        System.out.println("contain (0.6, 0.6)? " + kdtree.contains(new Point2D(0.6, 0.6)));
        System.out.println("isEmpty: " + kdtree.isEmpty());
    }

    //Test
    public void rangeTest() {
        KdTree kdtree = new KdTree();
        kdtree.insert(new Point2D(0.5, 0.5));
        kdtree.insert(new Point2D(0.3, 0.3));
        kdtree.insert(new Point2D(0.1, 0.1));
        kdtree.insert(new Point2D(0.2, 0.2));
        kdtree.insert(new Point2D(0.9, 0.8));

        RectHV rect = new RectHV(0.2, 0.2, 0.9, 0.9);
        //System.out.println(rect.contains(p));

        for (Point2D e : kdtree.range(rect)) {
            System.out.println("x = (" + e.x() + ", " + e.y() + ")" );
        }
    }

    @Test
    public void clientTest() {

        String filename = "src/test/resources/pointset_sm.txt";
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the data structures with N points from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

        double x0 = 0.0, y0 = 0.0;      // initial endpoint of rectangle
        double x1 = 0.0, y1 = 0.0;      // current location of mouse
        boolean isDragging = false;     // is the user dragging a rectangle

        // draw the points
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        brute.draw();

        while (true) {
            StdDraw.show(40);

            // user starts to drag a rectangle
            if (StdDraw.mousePressed() && !isDragging) {
                x0 = StdDraw.mouseX();
                y0 = StdDraw.mouseY();
                isDragging = true;
                continue;
            }

            // user is dragging a rectangle
            else if (StdDraw.mousePressed() && isDragging) {
                x1 = StdDraw.mouseX();
                y1 = StdDraw.mouseY();
                continue;
            }

            // mouse no longer pressed
            else if (!StdDraw.mousePressed() && isDragging) {
                isDragging = false;
            }


            RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1),
                    Math.max(x0, x1), Math.max(y0, y1));
            // draw the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            brute.draw();

            // draw the rectangle
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius();
            rect.draw();

            // draw the range search results for kd-tree in blue
            StdDraw.setPenRadius(.02);
            StdDraw.setPenColor(StdDraw.BLUE);
            for (Point2D p : kdtree.range(rect))
                p.draw();

            StdDraw.show(40);
        }
    }

    //Test
    public void NearestNeighborTest() {
        String filename = "src/test/resources/pointset_sm.txt";
        In in = new In(filename);

        StdDraw.show(0);

        // initialize the two data structures with point from standard input
        PointSET brute = new PointSET();
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
            brute.insert(p);
        }

        while (true) {

            // the location (x, y) of the mouse
            double x = StdDraw.mouseX();
            double y = StdDraw.mouseY();
            Point2D query = new Point2D(x, y);

            // draw all of the points
            StdDraw.clear();
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            brute.draw();

            // draw in blue the nearest neighbor (using kd-tree algorithm)
            StdDraw.setPenColor(StdDraw.BLUE);
            kdtree.nearest(query).draw();
            StdDraw.show(0);
            StdDraw.show(40);
        }
    }
}
