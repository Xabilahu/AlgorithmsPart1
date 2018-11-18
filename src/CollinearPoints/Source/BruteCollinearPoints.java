package CollinearPoints.Source;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private Point[] pointArray;
    private LineSegment[] lineSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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
            Arrays.sort(this.pointArray);//solves bad LineSegment creation
            LineSegment[] temp = new LineSegment[this.pointArray.length];
            int count = 0;
            double slope1, slope2, slope3;
            for (int p = 0; p < this.pointArray.length - 3; p++) {
                for (int q = p + 1; q < this.pointArray.length - 2; q++) {
                    for (int r = q + 1; r < this.pointArray.length - 1; r++) {
                        for (int s = r + 1; s < this.pointArray.length; s++) {
                            slope1 = this.pointArray[p].slopeTo(this.pointArray[q]);
                            slope2 = this.pointArray[p].slopeTo(this.pointArray[r]);
                            slope3 = this.pointArray[p].slopeTo(this.pointArray[s]);
                            if (Double.compare(slope1, slope2) == 0 && Double.compare(slope1, slope3) == 0)
                                temp[count++] = new LineSegment(this.pointArray[p], this.pointArray[s]);
                        }
                    }
                }
            }
            this.lineSegments = new LineSegment[count];
            for (int k = 0; k < count; k++) this.lineSegments[k] = temp[k];
        } else throw new IllegalArgumentException();
    }

    // the number of line segments
    public int numberOfSegments(){
        return this.lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments(){
        return this.lineSegments.clone();
    }

    public static void main(String[] args){
        In in = new In(args[0]);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}