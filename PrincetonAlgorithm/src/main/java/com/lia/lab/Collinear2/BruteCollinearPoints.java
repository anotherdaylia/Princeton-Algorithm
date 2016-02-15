package com.lia.lab.Collinear2;

import edu.princeton.cs.algs4.*;
import java.util.*;

/**
 * Created by liqu on 10/1/15.
 */
public class BruteCollinearPoints {
    private final Point[] points;
    private final Point[] pointsCopy;
    private int numberOfSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {throw new java.lang.NullPointerException();}
        this.points = points;
        this.numberOfSegments = 0;

        this.pointsCopy = new Point[points.length];
        System.arraycopy(points, 0, pointsCopy, 0, points.length);

        Arrays.sort(pointsCopy);
        for (int i = 1; i < pointsCopy.length; i++) {
            // throws an exception if duplicate points
            if (pointsCopy[i - 1].compareTo(pointsCopy[i]) == 0) {
                throw new java.lang.IllegalArgumentException();
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
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

                            // check if the new collinear overlap with existing collinears
                            if (map.containsKey(slope1)) {
                                ArrayList<Point[]> linesgmtList = map.get(slope1);
                                int index = Integer.MAX_VALUE; // track the linesegment needs update

                                for (Point[] ls : linesgmtList) {
                                    Point ls_p = ls[0];

                                    if (ls_p.slopeTo(collinear.get(0)) == slope1) {
                                        index = linesgmtList.indexOf(ls);
                                        break;
                                    }
                                }

                                // the collinear has overlap w. existing collinears, update linesegment
                                if (index != Integer.MAX_VALUE) {
                                    Point[] ls = linesgmtList.get(index);
                                    Point ls_p = ls[0];
                                    Point ls_q = ls[1];

                                    if (collinear.get(0).compareTo(ls_p) < 0 || collinear.get(3).compareTo(ls_q) > 0) {
                                        ls_p = collinear.get(0);
                                        ls_q = collinear.get(3);

                                        Point[] newLs = new Point[2];
                                        newLs[0] = ls_p;
                                        newLs[1] = ls_q;

                                        linesgmtList.set(index, newLs);
                                        map.put(slope1, linesgmtList);
                                    }
                                }

                                // the collinear does not overlap w/ existing collinear, add to list directly
                                /* Bug fixed:
                                this block of code have to be placed in the last, b/c after adding more linesegments
                                the size of the arraylist will be changed */
                                if (index == Integer.MAX_VALUE) {
                                    Point[] newLs = new Point[2];
                                    newLs[0] = collinear.get(0);
                                    newLs[1] = collinear.get(collinear.size()-1);
                                    linesgmtList.add(newLs);
                                    map.put(slope1, linesgmtList);
                                    numberOfSegments++;
                                }

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