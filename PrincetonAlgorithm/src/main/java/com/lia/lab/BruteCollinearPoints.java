package com.lia.lab;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.*;

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
        //HashMap<Double, LineSegment> lsMap = new HashMap<>();
        HashMap<Double, Point[]> pointMap = new HashMap<>();

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
                            //Collections.sort(pointList, Point.pointOrder());
                            Collections.sort(pointList);

                            if (pointMap.containsKey(slope1)) {
                                Point ls_p = pointMap.get(slope1)[0];
                                Point ls_q = pointMap.get(slope1)[1];

                                if (pointList.get(0).compareTo(ls_p) < 0) {
                                    ls_p = pointList.get(0);
                                }

                                if (pointList.get(3).compareTo(ls_q) > 0) {
                                    ls_q = pointList.get(3);
                                }

                                Point[] ls = new Point[2];
                                ls[0] = ls_p;
                                ls[1] = ls_q;
                                pointMap.put(slope1, ls);

                            } else {
                                Point[] ls = new Point[2];
                                ls[0] = pointList.get(0);
                                ls[1] = pointList.get(3);
                                pointMap.put(slope1, ls);
                                numberOfSegments++;
                            }
                        }
                    }
                }
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



}