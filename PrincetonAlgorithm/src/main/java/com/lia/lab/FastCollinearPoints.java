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
    public int numberOfSegments() {
        return numberOfSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        HashMap<Double, Point[]> pointMap = new HashMap<>();
        HashMap<Double, ArrayList<Point[]>> map = new HashMap<>();

        for (int p = 0; p < points.length; p++) {
            pointsCopy = new Point[points.length];
            for (int i = 0; i < points.length; i++) {
                pointsCopy[i] = points[i];
            }
            Arrays.sort(pointsCopy, points[p].slopeOrder());
//            if (points[p].compareTo(new Point(2419, 14586)) == 0) {
//                System.out.print("P is " + points[p] + "\n\t");
//                for (Point ap: pointsCopy) {
//                    System.out.print(ap + " ");
//                }
//                System.out.println();
//            }
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
                    System.out.println(points[p]);
                    for (Point cp: collinear) {
                        System.out.println(cp);
                    }
                    System.out.println();

                    checkOverlap(collinear, p, map);

                    collinear.clear();
                    collinear.add(pointsCopy[q]);

                }else {
                    collinear.clear();
                    collinear.add(pointsCopy[q]);
                }
            }

            if (collinear.size() > 2) {
                System.out.println(points[p]);
                for (Point cp: collinear) {
                    System.out.println(cp);
                }
                System.out.println();

                checkOverlap(collinear, p, map);
            }

        }

        ArrayList<LineSegment> lsList = new ArrayList<>();

        Iterator it_map = map.keySet().iterator();
        while(it_map.hasNext()) {
            ArrayList<Point[]> linesgmtList = map.get(it_map.next());
            Iterator it_list = linesgmtList.iterator();
            while (it_list.hasNext()) {
                Point[] pointPair = (Point[]) it_list.next();
                LineSegment ls = new LineSegment(pointPair[0], pointPair[1]);
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
            int index = Integer.MAX_VALUE; // track the linesegment needs update

            for (Point[] pointPair : linesgmtList) {
                Point ls_p = pointPair[0];

                // check if points on the collinear is on the existing collier
                if (ls_p.slopeTo(collinear.get(0)) != slope
                        && ls_p.slopeTo(collinear.get(0)) != Double.NEGATIVE_INFINITY) {
                    count++;
                    if (count < linesgmtList.size()) {
                        continue;
                    } else {
                        index = linesgmtList.indexOf(pointPair);
                        break;
                    }
                } else {
                    index = linesgmtList.indexOf(pointPair);
                    break;
                }
            }

            // the collinear does not overlap w/ existing coliinears, add to list directly
            if (count >= linesgmtList.size() && index != Integer.MAX_VALUE) {
                Point[] pointPair = new Point[2];
                pointPair[0] = collinear.get(0);
                pointPair[1] = collinear.get(collinear.size()-1);
                linesgmtList.add(pointPair);
                map.put(slope, linesgmtList);
                numberOfSegments++;
            }

            // the collinear has overlap w. existing collinears, update linesegment
            if (count < linesgmtList.size() && index != Integer.MAX_VALUE) {
                Point[] pointPair = linesgmtList.get(index);
                Point ls_p = pointPair[0];
                Point ls_q = pointPair[1];

                if (collinear.get(0).compareTo(ls_p) < 0 || collinear.get(3).compareTo(ls_q) > 0) {
                    ls_p = collinear.get(0);
                    ls_q = collinear.get(collinear.size()-1);

                    Point[] ls = new Point[2];
                    ls[0] = ls_p;
                    ls[1] = ls_q;

                    linesgmtList.set(index, ls);
                    map.put(slope, linesgmtList);
                }
            }

        } else { // if map does not contain the slope of collinear, add the collinear
            Point[] pointPair = new Point[2];
            pointPair[0] = collinear.get(0);
            pointPair[1] = collinear.get(collinear.size()-1);
            ArrayList<Point[]> linesgmtList = new ArrayList<>();
            linesgmtList.add(pointPair);
            map.put(slope, linesgmtList);
            numberOfSegments++;
        }
    }

}