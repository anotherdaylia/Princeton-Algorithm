package com.lia.lab;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by liqu on 10/1/15.
 */
public class BruteCollinearPoints {
    private Point[] points;
    private int numberOfSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        this.points = points;
        this.numberOfSegments = 0;
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        LineSegment[] lineSegments = new LineSegment[1000];

        for (int p1 = 0; p1 < points.length; p1++) {
            for (int p2 = p1 + 1; p2 < points.length; p2++) {
                for (int p3 = p2 + 1; p3 < points.length; p3++) {
                    for (int p4 = p3 + 1; p4 < points.length; p4++) {

                        Double slope1 = points[p1].slopeTo(points[p2]);
                        Double slope2 = points[p1].slopeTo(points[p3]);
                        Double slope3 = points[p1].slopeTo(points[p4]);
                        if ((Double.compare(slope1, slope2) == 0)
                                && (Double.compare(slope1, slope3) == 0)){
                            ArrayList<Point> p = new ArrayList<>();
                            p.add(points[p1]);
                            p.add(points[p2]);
                            p.add(points[p3]);
                            p.add(points[p4]);
                            Collections.sort(p, Point.pointOrder());
                            LineSegment ls = new LineSegment(p.get(0), p.get(3));
                            lineSegments[numberOfSegments] = ls;
                            numberOfSegments++;
                        }
                    }
                }
            }
        }

        return lineSegments;
    }

}