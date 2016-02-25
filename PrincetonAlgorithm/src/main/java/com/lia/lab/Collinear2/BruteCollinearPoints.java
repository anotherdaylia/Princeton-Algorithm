package com.lia.lab.Collinear2;

import java.util.*;

/**
 * Created by liqu on 10/1/15.
 * Modified by liqu on 02/14/16
 */
public class BruteCollinearPoints {
    private final Point[] points;
    //private final Point[] pointsCopy;
    private int numberOfSegments;
    private final LineSegment[] lineSegments;

    // finds all line calculateSegments containing 4 points
    public BruteCollinearPoints(Point[] pt) {
        if (pt == null) {throw new java.lang.NullPointerException();}
        this.numberOfSegments = 0;

        this.points = new Point[pt.length];
        System.arraycopy(pt, 0, this.points, 0, pt.length);

        Arrays.sort(points);
        for (int i = 1; i < points.length; i++) {
            // throws an exception if duplicate points
            if (points[i - 1].compareTo(points[i]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }

        lineSegments = calculateSegments();
    }

    // the number of line calculateSegments
    public int numberOfSegments() {
        return numberOfSegments;
    }


    public LineSegment[] segments() {
        LineSegment[] lsToReturn = new LineSegment[lineSegments.length];
        System.arraycopy(lineSegments, 0, lsToReturn, 0, lineSegments.length);
        return lsToReturn;
    }

    // the line calculateSegments
    private LineSegment[] calculateSegments() {
        HashMap<Double, ArrayList<Point[]>> map = new HashMap<>();

        for (int p = 0; p < points.length; p++) {
            for (int q = p + 1; q < points.length; q++) {
                for (int r = q + 1; r < points.length; r++) {
                    for (int s = r + 1; s < points.length; s++) {

                        Double slope1 = points[p].slopeTo(points[q]);
                        Double slope2 = points[p].slopeTo(points[r]);
                        Double slope3 = points[p].slopeTo(points[s]);

                        if ((Double.compare(slope1, slope2) == 0) && (Double.compare(slope1, slope3) == 0)){

                            ArrayList<Point> collinear = new ArrayList<>();
                            collinear.add(points[p]);
                            collinear.add(points[q]);
                            collinear.add(points[r]);
                            collinear.add(points[s]);
                            Collections.sort(collinear);

                            // check if the new collinear overlap with existing collinear
                            if (map.containsKey(slope1)) {
                                ArrayList<Point[]> linesgmtList = map.get(slope1);
                                // the collinear does not overlap w/ existing collinear, add to list directly
                                /* Bug fixed:
                                this block of code have to be placed in the last,
                                b/c after adding more line segments
                                the size of the arraylist will be changed */
                                Point[] newLs = new Point[2];
                                newLs[0] = collinear.get(0);
                                newLs[1] = collinear.get(collinear.size()-1);
                                linesgmtList.add(newLs);
                                map.put(slope1, linesgmtList);
                                numberOfSegments++;

                            } else { // if map does not contain the slope of collinear, add the collinear
                                Point[] newLs = new Point[2];
                                newLs[0] = collinear.get(0);
                                newLs[1] = collinear.get(collinear.size()-1);
                                ArrayList<Point[]> linesgmtList = new ArrayList<>();
                                linesgmtList.add(newLs);
                                map.put(slope1, linesgmtList);
                                numberOfSegments++;
                            }
                        }
                    }
                }
            }
        }
        return convertToArr(map);
    }

    private LineSegment[] convertToArr (HashMap<Double, ArrayList<Point[]>> map) {
        ArrayList<LineSegment> lsList = new ArrayList<>();

        for (ArrayList<Point[]> list : map.values()) {
            for (Point[] points : list) {
                LineSegment ls = new LineSegment(points[0], points[1]);
                lsList.add(ls);
            }
        }

        LineSegment[] lineSgmtArr = new LineSegment[lsList.size()];
        lineSgmtArr = lsList.toArray(lineSgmtArr);

        return lineSgmtArr;
    }

}