import java.util.Arrays;


public class Brute {
    public static void main(String[] args) {
        String filename = args[0];
        
        In in = new In(filename);
        int N = in.readInt();
        Point[] points = new Point[N];
        
        // rescale coordinates and turn on animation mode
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);
        StdDraw.setPenRadius(0.01);  // make the points a bit larger

        // read points from file
        for (int i = 0; i < N; i++) {
            int x, y;
            x = in.readInt();
            y = in.readInt();
            
            Point p = new Point(x, y);
            p.draw();
            points[i] = p;
        }
        
        Arrays.sort(points);
        
        // brute force
        for (int p = 0; p < N; p++) {
            for (int q = p+1; q < N; q++) {
                double pq = points[p].slopeTo(points[q]);
                for (int r = q+1; r < N; r++) {
                    double pr = points[p].slopeTo(points[r]);
                    for (int s = r+1; s < N; s++) {
                        double ps = points[p].slopeTo(points[s]);
                        
                        // collinear
                        if (pq == pr && pq == ps && ps == pr) {
                            System.out.printf("%s -> %s -> %s -> %s\n", 
                                    points[p], points[q], points[r], points[s]);
                            points[p].drawTo(points[s]);
                        }
                    }
                }
            }
        }
        
        // display to screen all at once
        StdDraw.show(0);
    }
}
