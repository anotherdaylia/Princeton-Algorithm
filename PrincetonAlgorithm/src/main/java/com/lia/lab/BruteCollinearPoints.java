package com.lia.lab;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

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
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {

                        Double slope1 = points[p].slopeTo(points[q]);
                        Double slope2 = points[p].slopeTo(points[r]);
                        Double slope3 = points[p].slopeTo(points[s]);

                        if ((Double.compare(slope1, slope2) == 0)
                                && (Double.compare(slope1, slope3) == 0)){

                            ArrayList<Point> pointList = new ArrayList<>();
                            pointList.add(points[p]);
                            pointList.add(points[q]);
                            pointList.add(points[r]);
                            pointList.add(points[s]);
                            Collections.sort(pointList, Point.pointOrder());

                            lineSegments[numberOfSegments] = new LineSegment(pointList.get(0), pointList.get(3));
                            numberOfSegments++;

//                            if (lineSegments == null ){
//                                lineSegments[numberOfSegments] = new LineSegment(pointList.get(0), pointList.get(3));
//                            } else {
//                                numberOfSegments++;
//                                lineSegments[numberOfSegments] = new LineSegment(pointList.get(0), pointList.get(3));
//                            }

                        }
                    }
                }
            }
        }

        return lineSegments;
    }

}