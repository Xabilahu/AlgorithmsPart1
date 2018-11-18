package CollinearPoints.Source;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.util.Arrays;

public class FastCollinearPoints {

    private Point[] pointArray;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points != null) {
            for (int i = 0; i < points.length; i++) {
                if (points[i] == null) throw new IllegalArgumentException();//some point null
            }
            for (int i = 0; i < points.length; i++) {
                for (int j = i + 1; j < points.length; j++) {
                    if (points[i].compareTo(points[j]) == 0) throw new IllegalArgumentException(); //Equal points
                }
            }
            this.pointArray = points.clone();
            LinkedQueue<LineSegment> tempQueue = new LinkedQueue<>();

            for (int i = 0; i < this.pointArray.length - 3; i++) {
                Arrays.sort(this.pointArray);//orders array in ascending order
                Arrays.sort(this.pointArray, this.pointArray[i].slopeOrder());//orders array in slope-order keeping the ascending order
                for (int j = 1, k = 2; k < this.pointArray.length; k++) {
                    while (k < this.pointArray.length && Double.compare(this.pointArray[0].slopeTo(this.pointArray[j]), this.pointArray[0].slopeTo(this.pointArray[k])) == 0) k++;
                    if (k - j >= 3 && this.pointArray[0].compareTo(this.pointArray[j]) < 0) {//only adds Segment once when the starting point is less than the ending point
                        tempQueue.enqueue(new LineSegment(this.pointArray[0], this.pointArray[k - 1]));
                    }
                    j = k;
                }
            }

            if (!tempQueue.isEmpty()) {
                int i = 0;
                this.lineSegments = new LineSegment[tempQueue.size()];
                while (!tempQueue.isEmpty()) {
                    this.lineSegments[i++] = tempQueue.dequeue();
                }
            }
        } else throw new IllegalArgumentException();//null argument
    }

    // the number of line segments
    public int numberOfSegments() {
        return this.lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return this.lineSegments.clone();
    }

    public static void main(String[] args){
        File f = new File(System.getProperty("user.dir") + File.separator + "/src/CollinearPoints/CollinearPointsTestFiles/input6.txt");
        In in = new In(f);
//        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw(); }
        StdDraw.show();
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}