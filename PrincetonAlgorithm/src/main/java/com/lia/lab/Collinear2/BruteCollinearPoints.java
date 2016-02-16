package com.lia.lab.Collinear2;

import java.util.*;

/**
 * Created by liqu on 10/1/15.
 */
public class BruteCollinearPoints {
    private final Point[] points;
    private final Point[] pointsCopy;
    private int numberOfSegments;
    private final LineSegment[] lineSegments;

    // finds all line calculateSegments containing 4 points
    public BruteCollinearPoints(Point[] pt) {
        if (pt == null) {throw new java.lang.NullPointerException();}
        this.numberOfSegments = 0;

        this.points = new Point[pt.length];
        this.pointsCopy = new Point[points.length];
        System.arraycopy(pt, 0, this.points, 0, pt.length);
        System.arraycopy(this.points, 0, pointsCopy, 0, points.length);

        Arrays.sort(pointsCopy);
        for (int i = 1; i < pointsCopy.length; i++) {
            // throws an exception if duplicate points
            if (pointsCopy[i - 1].compareTo(pointsCopy[i]) == 0) {
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

        for (int p = 0; p < pointsCopy.length; p++) {
            for (int q = p + 1; q < pointsCopy.length; q++) {
                for (int r = q + 1; r < pointsCopy.length; r++) {
                    for (int s = r + 1; s < pointsCopy.length; s++) {

                        Double slope1 = pointsCopy[p].slopeTo(pointsCopy[q]);
                        Double slope2 = pointsCopy[p].slopeTo(pointsCopy[r]);
                        Double slope3 = pointsCopy[p].slopeTo(pointsCopy[s]);

                        if ((Double.compare(slope1, slope2) == 0) && (Double.compare(slope1, slope3) == 0)){

                            ArrayList<Point> collinear = new ArrayList<>();
                            collinear.add(pointsCopy[p]);
                            collinear.add(pointsCopy[q]);
                            collinear.add(pointsCopy[r]);
                            collinear.add(pointsCopy[s]);
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