package com.lia.lab.Collinear2;
import java.util.*;

/**
 * Modified by liqu on 02/14/16.
 */
public class FastCollinearPoints {
    private final Point[] points;
    private final Point[] pointscopy;
    private int numberOfSegments = 0;
    private final LineSegment[] lineSegments;

    // finds all line calculateSegments containing 4 or more points
    public FastCollinearPoints(Point[] pt) {
        if (pt == null) {throw new java.lang.NullPointerException();}
        this.numberOfSegments = 0;

        this.points = new Point[pt.length];
        this.pointscopy = new Point[points.length];
        System.arraycopy(pt, 0, this.points, 0, pt.length);
        System.arraycopy(this.points, 0, pointscopy, 0, points.length);

        Arrays.sort(pointscopy);
        for (int i = 1; i < pointscopy.length; i++) {
            // throws an exception if duplicate points
            if (pointscopy[i - 1].compareTo(pointscopy[i]) == 0) {
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

            Arrays.sort(pointscopy, points[p].slopeOrder());

            ArrayList<Point> collinear = new ArrayList<>(points.length);

            for (int q = 0 ; q < pointscopy.length; q++) {

                if (collinear.isEmpty()) {
                    collinear.add(pointscopy[q]);
                } else if (points[p].slopeTo(pointscopy[q - 1]) == points[p].slopeTo(pointscopy[q])) {
                    collinear.add(pointscopy[q]);
                } else if(collinear.size() > 2) {
                    checkOverlap(collinear, p, map);
                    collinear.clear();
                    collinear.add(pointscopy[q]);
                } else {
                    collinear.clear();
                    collinear.add(pointscopy[q]);
                }
            }

            if (collinear.size() > 2) {
                checkOverlap(collinear, p, map);
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

    private void checkOverlap(ArrayList<Point> collinear, int p, HashMap<Double, ArrayList<Point[]>> map) {
        collinear.add(points[p]);
        Collections.sort(collinear);
        Double slope = collinear.get(0).slopeTo(collinear.get(collinear.size()-1));

        if (map.containsKey(slope)) {
            ArrayList<Point[]> linesgmtList = map.get(slope);
            int count = 0; // track the number of linesegment have gone through in linesgmtList

            for (Point[] line : linesgmtList) {
                Point ls_p = line[0];

                // check if points on the collinear is on the existing collier
                if (ls_p.slopeTo(collinear.get(0)) == slope || ls_p.compareTo(collinear.get(0)) == 0) { // Overlap
                    break;
                } else { // Not overlap
                    count++;
                }
            }

            /* Bug fixed:
            this block of code have to be placed in the last, b/c after adding more line segment
            the size of the set will be changed */

            // the collinear does not overlap w/ existing collinear, add to list directly
            if (count >= linesgmtList.size()) {
                Point[] newline = new Point[2];
                newline[0] = collinear.get(0);
                newline[1] = collinear.get(collinear.size()-1);

                linesgmtList.add(newline);
                map.put(slope, linesgmtList);
                numberOfSegments++;
            }

        } else { // if map does not contain the slope of collinear, add the collinear
            Point[] newline = new Point[2];
            newline[0] = collinear.get(0);
            newline[1] = collinear.get(collinear.size()-1);

            ArrayList<Point[]> linesgmtList = new ArrayList<>();
            linesgmtList.add(newline);
            map.put(slope, linesgmtList);
            numberOfSegments++;
        }
    }

}