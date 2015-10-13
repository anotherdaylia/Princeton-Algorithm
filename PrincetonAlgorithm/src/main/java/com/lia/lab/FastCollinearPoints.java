package com.lia.lab;

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

        for (int p = 0; p < points.length; p++) {
            Arrays.sort(points, points[p].slopeOrder());

            ArrayList<Point> collinearPoints = new ArrayList<>(points.length);

            for (int q = 0 ; q < points.length; q++) {
                if (p == q) {
                    continue;
                }

                if (collinearPoints.isEmpty()) {
                    collinearPoints.add(points[q]);
                }else if (points[p].slopeTo(points[q - 1]) == points[p].slopeTo(points[q])) {
                    collinearPoints.add(points[q]);
                }else if(collinearPoints.size() > 3) {
                    collinearPoints.add(points[p]);
                    Collections.sort(collinearPoints);
                    lineSegments[numberOfSegments] = new LineSegment(collinearPoints.get(0),
                                                                     collinearPoints.get(collinearPoints.size()-1));
                    numberOfSegments++;
                }else {
                    collinearPoints.clear();
                    collinearPoints.add(points[q]);
                }
            }
        }

        return lineSegments;
    }
}
