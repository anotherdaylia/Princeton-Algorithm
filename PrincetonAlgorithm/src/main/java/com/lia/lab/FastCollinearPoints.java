package com.lia.lab;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by liqu on 10/3/15.
 */
public class FastCollinearPoints {
    private Point[] points;
    private int numberOfSegments = 0;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {

        if (points == null) {
            throw new java.lang.NullPointerException();
        }
        this.points = points;
        this.numberOfSegments = 0;
    }
    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[points.length];

        for (int p = 0; p < points.length; p++) {
            Arrays.sort(points, points[p].slopeOrder());

            ArrayList<Point> pointList = new ArrayList<>(points.length);

            for (int q = 0 ; q < points.length; q++) {
                if (p == q) {
                    continue;
                }

                if (pointList.isEmpty()) {
                    pointList.add(points[q]);
                }else if (points[p].slopeTo(points[q - 1]) == points[p].slopeTo(points[q])) {
                    pointList.add(points[q]);
                }else if(pointList.size() > 3) {
                    pointList.add(points[p]);
                    Collections.sort(pointList);

                    LineSegment ls = new LineSegment(pointList.get(0), pointList.get(pointList.size()-1));
                    lineSegments[numberOfSegments] = ls;
                    numberOfSegments++;

                }else {
                    pointList.clear();
                    pointList.add(points[q]);
                }
            }
        }

        return lineSegments;
    }

}
