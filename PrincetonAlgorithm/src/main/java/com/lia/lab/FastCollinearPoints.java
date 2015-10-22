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
                    //System.out.println("Empty Added" + pointsCopy[q]);
                    collinear.add(pointsCopy[q]);

                }else if (points[p].slopeTo(pointsCopy[q - 1]) == points[p].slopeTo(pointsCopy[q])) {
                    //System.out.println("Equal Added" + pointsCopy[q]);
                    collinear.add(pointsCopy[q]);

                } else if(collinear.size() > 2) {
                    checkOverlap(collinear, p, map);
                    collinear.clear();
                    collinear.add(pointsCopy[q]);

                }else {
                    collinear.clear();
                    collinear.add(pointsCopy[q]);
                }
            }

            if (collinear.size() > 2) {
                checkOverlap(collinear, p, map);
            }

        }

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

            for (Point[] pointPair : linesgmtSet) {
                Point ls_p = pointPair[0];

                // check if points on the collinear is on the existing collier
                if (ls_p.slopeTo(collinear.get(0)) == slope || ls_p.compareTo(collinear.get(0)) == 0) {

                    if (ls_p.compareTo(new Point(1227, 14586)) == 0 && collinear.get(0).compareTo(new Point(1227, 14586)) == 0) {
                        System.out.println("P is " + ls_p);
                    }

                    p_ix = pointPair;
                    break;
                } else {

                    //System.out.println("Compare 1st point = " + ls_p + " 2nd point = " + collinear.get(0));
                    count++;
                    if (count < linesgmtSet.size()) {
                        continue;
                    } else {
                        p_ix = pointPair;
                        break;
                    }
                }
            }

            // the collinear does not overlap w/ existing coliinears, add to list directly
            if (count >= linesgmtSet.size() && p_ix != null) {
                System.out.println("Find same slope but Not overlap, add directly");
                Point[] pointPair = new Point[2];
                pointPair[0] = collinear.get(0);
                pointPair[1] = collinear.get(collinear.size()-1);

                System.out.println("    adding: " + pointPair[0] + " " + pointPair[1]);
                linesgmtSet.add(pointPair);
                map.put(slope, linesgmtSet);
                numberOfSegments++;

                System.out.println("  LinesigmentSet elements: ");
                Iterator it = linesgmtSet.iterator();
                while(it.hasNext()) {
                    Point[] p_tmp = (Point[])it.next();
                    System.out.println("  " + p_tmp[0].toString() + ", " + p_tmp[1].toString());
                }
            }

            // the collinear has overlap w. existing collinears, update linesegment
            if (count < linesgmtSet.size() && p_ix != null) {
                System.out.println("Find same slope and Overlap, update points");
                Point[] pointPair = p_ix;
                Point ls_p = pointPair[0];
                Point ls_q = pointPair[1];

                if (collinear.get(0).compareTo(ls_p) < 0 || collinear.get(collinear.size()-1).compareTo(ls_q) > 0) {
                    System.out.println("  before updating: " + ls_p + " " + ls_q);
                    ls_p = collinear.get(0);
                    ls_q = collinear.get(collinear.size()-1);

                    Point[] ls = new Point[2];
                    ls[0] = ls_p;
                    ls[1] = ls_q;
                    System.out.println("  after updating: " + ls[0] + " " + ls[1]);
                    linesgmtSet.remove(p_ix);
                    linesgmtSet.add(ls);
                    map.put(slope, linesgmtSet);
                }
            }

        } else { // if map does not contain the slope of collinear, add the collinear
            System.out.println("No existing slope, add directly");
            Point[] pointPair = new Point[2];
            pointPair[0] = collinear.get(0);
            pointPair[1] = collinear.get(collinear.size()-1);

            Set<Point[]> linesgmtSet = new HashSet<>();
            linesgmtSet.add(pointPair);
            System.out.println("    adding: " + pointPair[0] + " " + pointPair[1]);
            map.put(slope, linesgmtSet);
            numberOfSegments++;

            System.out.println("  LinesigmentSet elements: ");
            Iterator it = linesgmtSet.iterator();
            while(it.hasNext()) {
                Point[] p_tmp = (Point[])it.next();
                System.out.println("  " + p_tmp[0].toString() + ", " + p_tmp[1].toString());
            }
        }
    }

}