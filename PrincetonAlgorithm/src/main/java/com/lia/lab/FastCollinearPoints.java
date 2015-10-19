package com.lia.lab;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

/**
 * Created by liqu on 10/3/15.
 */
public class FastCollinearPoints {
    private Point[] points;
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
        //HashMap<Double, LineSegment> map = new HashMap<>();
        HashMap<Double, Point[]> pointMap = new HashMap<>();

        for (int p = 0; p < points.length; p++) {
            Arrays.sort(points, points[p].slopeOrder());

            ArrayList<Point> pointList = new ArrayList<>(points.length);

            for (int q = 0 ; q < points.length; q++) {
                if (p == q) {
                    continue;
                }

                if (pointList.isEmpty()) {
                    pointList.add(points[q]);
                }else if (points[p].slopeTo(points[q - 1]) == points[p].slopeTo(points[q])) {
                    pointList.add(points[q]);
                }else if(pointList.size() > 2) {
                    check(pointList, p, pointMap);

                    pointList.clear();
                    pointList.add(points[q]);

                }else {
                    pointList.clear();
                    pointList.add(points[q]);
                }
            }

            if (pointList.size() > 2) {
                check(pointList, p, pointMap);
            }

        }

        ArrayList<LineSegment> lsList = new ArrayList<>();

        Iterator it = pointMap.keySet().iterator();
        while(it.hasNext()) {
            Point[] pointPair = pointMap.get(it.next());
            LineSegment ls = new LineSegment(pointPair[0], pointPair[1]);

            lsList.add(ls);
        }

        LineSegment[] lineSgmtArr = new LineSegment[lsList.size()];
        lineSgmtArr = lsList.toArray(lineSgmtArr);

        return lineSgmtArr;
    }


    private void check(ArrayList<Point> pointList, int p, HashMap<Double, Point[]> pointMap) {
        pointList.add(points[p]);
        //Collections.sort(pointList, Point.pointOrder());
        Collections.sort(pointList);

        Double slope = pointList.get(0).slopeTo(pointList.get(pointList.size()-1));

        if (pointMap.containsKey(slope)) {
            Point ls_p = pointMap.get(slope)[0];
            Point ls_q = pointMap.get(slope)[1];

            if (pointList.get(0).compareTo(ls_p) < 0) {
                ls_p = pointList.get(0);
            }

            if (pointList.get(pointList.size()-1).compareTo(ls_q) > 0) {
                ls_q = pointList.get(pointList.size()-1);
            }

            Point[] ls = new Point[2];
            ls[0] = ls_p;
            ls[1] = ls_q;
            pointMap.put(slope, ls);

        } else {
            Point[] ls = new Point[2];
            ls[0] = pointList.get(0);
            ls[1] = pointList.get(3);
            pointMap.put(slope, ls);
            numberOfSegments++;
        }
    }

}
