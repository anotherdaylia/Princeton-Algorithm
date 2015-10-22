package com.lia.lab;

import java.util.*;

/**
 * Created by liqu on 10/3/15.
 */
public class FastCollinearPoints {
    private Point[] points;
    private Point[] pointsCopy;
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
    public int numberOfSegments() { return numberOfSegments; }

    // the line segments
    public LineSegment[] segments() {
        HashMap<Double, Set<Point[]>> map = new HashMap<>();

        for (int p = 0; p < points.length; p++) {
            pointsCopy = new Point[points.length];
            for (int i = 0; i < points.length; i++) {
                pointsCopy[i] = points[i];
            }
            Arrays.sort(pointsCopy, points[p].slopeOrder());

            ArrayList<Point> collinear = new ArrayList<>(points.length);

            for (int q = 0 ; q < pointsCopy.length; q++) {
                if (points[p].compareTo(pointsCopy[q]) == 0) { continue; }

                if (collinear.isEmpty()) {
                    collinear.add(pointsCopy[q]);
                } else if (points[p].slopeTo(pointsCopy[q - 1]) == points[p].slopeTo(pointsCopy[q])) {
                    collinear.add(pointsCopy[q]);
                } else if(collinear.size() > 2) {
                    checkOverlap(collinear, p, map);
                    collinear.clear();
                    collinear.add(pointsCopy[q]);
                } else {
                    collinear.clear();
                    collinear.add(pointsCopy[q]);
                }
            }

            if (collinear.size() > 2) {
                checkOverlap(collinear, p, map);
            }

        }

        return convertToArr(map);
    }

    private LineSegment[] convertToArr(HashMap<Double, Set<Point[]>> map) {
        ArrayList<LineSegment> lsList = new ArrayList<>();

        Iterator it_map = map.keySet().iterator();
        while(it_map.hasNext()) {
            Set<Point[]> linesgmtSet = map.get(it_map.next());
            Iterator it_set = linesgmtSet.iterator();
            while (it_set.hasNext()) {
                Point[] pointPair = (Point[]) it_set.next();
                LineSegment ls = new LineSegment(pointPair[0], pointPair[1]);
                lsList.add(ls);
            }
        }

        LineSegment[] lineSgmtArr = new LineSegment[lsList.size()];
        lineSgmtArr = lsList.toArray(lineSgmtArr);

        return lineSgmtArr;
    }

    private void checkOverlap(ArrayList<Point> collinear, int p, HashMap<Double, Set<Point[]>> map) {
        collinear.add(points[p]);
        Collections.sort(collinear);
        Double slope = collinear.get(0).slopeTo(collinear.get(collinear.size()-1));

        if (map.containsKey(slope)) {
            Set<Point[]> linesgmtSet = map.get(slope);
            int count = 0; // track the number of linesegment have gone through in linesgmtSet
            Point[] p_ix = new Point[2]; // track the linesegment need update

            for (Point[] line : linesgmtSet) {
                Point ls_p = line[0];

                // check if points on the collinear is on the existing collier
                if (ls_p.slopeTo(collinear.get(0)) == slope || ls_p.compareTo(collinear.get(0)) == 0) { // Overlap
                    p_ix = line;
                    break;
                } else { // Not overlap
                    count++;
                    if (count < linesgmtSet.size()) {
                        continue;
                    } else {
                        break;
                    }
                }
            }

            // the collinear does not overlap w/ existing coliinears, add to list directly
            if (count < linesgmtSet.size() && p_ix != null) {
                Point[] line = p_ix;
                Point line_p = line[0];
                Point line_q = line[1];

                if (collinear.get(0).compareTo(line_p) < 0 || collinear.get(collinear.size()-1).compareTo(line_q) > 0) {
                    line_p = collinear.get(0);
                    line_q = collinear.get(collinear.size()-1);

                    Point[] newline = new Point[2];
                    newline[0] = line_p;
                    newline[1] = line_q;
                    linesgmtSet.remove(p_ix);
                    linesgmtSet.add(newline);
                    map.put(slope, linesgmtSet);
                }
            }

            /* Bug fixed:
            this block of code have to be placed in the last, b/c after adding more linesegment
            the size of the set will be changed */
            // the collinear has overlap w. existing collinears, update linesegment
            if (count >= linesgmtSet.size() && p_ix != null) {
                Point[] newline = new Point[2];
                newline[0] = collinear.get(0);
                newline[1] = collinear.get(collinear.size()-1);

                linesgmtSet.add(newline);
                map.put(slope, linesgmtSet);
                numberOfSegments++;
            }

        } else { // if map does not contain the slope of collinear, add the collinear
            Point[] newline = new Point[2];
            newline[0] = collinear.get(0);
            newline[1] = collinear.get(collinear.size()-1);

            Set<Point[]> linesgmtSet = new HashSet<>();
            linesgmtSet.add(newline);
            map.put(slope, linesgmtSet);
            numberOfSegments++;
        }
    }

}