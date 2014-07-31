
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 3.2 Final//EN">
<HTML>
<HEAD>


<TITLE>
Programming Assignment 3: Pattern Recognition Assignment
</TITLE></HEAD>


<BODY>
<H2>Programming Assignment 3: Pattern Recognition</H2>

<p>
<br>
Write a program to recognize line patterns in a given set of points.

<p>
Computer vision involves analyzing patterns in visual images and
reconstructing the real-world objects that produced them.  The process
in often broken up into two phases: <em>feature detection</em> and
<em>pattern recognition</em>. Feature detection involves selecting
important features of the image; pattern recognition involves
discovering patterns in the features. We will investigate a
particularly clean pattern recognition problem involving points and
line segments.  This kind of pattern recognition arises in many other
applications such as statistical data analysis.

<p>
<b>The problem.</b>
Given a set of <em>N</em> distinct points in the plane, 
draw every (maximal) line segment that connects a subset of 4 or more of the points.
<p>

<center>
<IMG SRC="lines2.png" width = "500" height = "200" alt =  "Points and lines">
</center>

<p>
<b>Point data type.</b>
Create an immutable data type <tt>Point</tt> that represents a point in the plane
by implementing the following API:

<blockquote>
<pre>
<b>public class Point implements Comparable&lt;Point&gt; {</b>
<b>   public final Comparator&lt;Point&gt; SLOPE_ORDER;</b>        <font color = gray>// compare points by slope to this point</font>

<font color = gray>   public Point(int x, int y)                         // construct the point (x, y)</font>

<font color = gray>   public   void draw()                               // draw this point</font>
<font color = gray>   public   void drawTo(Point that)                   // draw the line segment from this point to that point</font>
<font color = gray>   public String toString()                           // string representation</font>

<b>   public    int compareTo(Point that)</b>                <font color = gray>// is this point lexicographically smaller than that point?</font>
<b>   public double slopeTo(Point that)</b>                  <font color = gray>// the slope between this point and that point</font>
<b>}</b>
</pre>
</blockquote>

To get started, use the data type
<a href = "../checklists/Point.java">Point.java</a>,
which implements the constructor and the
<tt>draw()</tt>, <tt>drawTo()</tt>, and <tt>toString()</tt> methods.
Your job is to add the following components.


<ul>


<p><li> The <tt>compareTo()</tt> method should compare points by their <em>y</em>-coordinates,
breaking ties by their <em>x</em>-coordinates.
Formally, the invoking point
(<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>)
is <em>less than</em> the argument point
(<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>)
if and only if either <em>y</em><sub>0</sub> &lt; <em>y</em><sub>1</sub> or if
<em>y</em><sub>0</sub> = <em>y</em><sub>1</sub> and <em>x</em><sub>0</sub> &lt; <em>x</em><sub>1</sub>.

<p><li> The <tt>slopeTo()</tt> method should return the slope between the invoking point
(<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>) and the argument point
(<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>), which is given by the formula
(<em>y</em><sub>1</sub> &minus; <em>y</em><sub>0</sub>) / (<em>x</em><sub>1</sub> &minus; <em>x</em><sub>0</sub>).
Treat the slope of a horizontal line segment as positive zero;
treat the slope of a vertical line segment as positive infinity;
treat the slope of a degenerate line segment (between a point and itself) as negative infinity.

<p><li> The <tt>SLOPE_ORDER</tt> comparator should compare points by the slopes they
make with the invoking point (<em>x</em><sub>0</sub>, <em>y</em><sub>0</sub>).
Formally, the point (<em>x</em><sub>1</sub>, <em>y</em><sub>1</sub>) is <em>less than</em>
the point (<em>x</em><sub>2</sub>, <em>y</em><sub>2</sub>) if and only if the slope
(<em>y</em><sub>1</sub> &minus; <em>y</em><sub>0</sub>) / (<em>x</em><sub>1</sub> &minus; <em>x</em><sub>0</sub>) 
is less than the slope
(<em>y</em><sub>2</sub> &minus; <em>y</em><sub>0</sub>) / (<em>x</em><sub>2</sub> &minus; <em>x</em><sub>0</sub>).
Treat horizontal, vertical, and degenerate line segments as in the <tt>slopeTo()</tt> method.

</ul>

<p>
<b>Brute force.</b>
Write a program <tt>Brute.java</tt> that examines 4 
points at a time and checks whether
they all lie on the same line segment, printing out any such line
segments to standard output and drawing them using standard drawing.
To check whether the 4 points <em>p</em>, <em>q</em>, <em>r</em>, and <em>s</em> are collinear,
check whether the slopes between <em>p</em> and <em>q</em>, 
between <em>p</em> and <em>r</em>, and between <em>p</em> and <em>s</em>
are all equal.

<p>
The order of growth of the running time of your program should be
<em>N</em><sup>4</sup> in the worst case and 
it should use space proportional to <em>N</em>.

<p>
<b>A faster, sorting-based solution.</b>
Remarkably, it is possible to solve the problem much faster than the
brute-force solution described above.
Given a point <em>p</em>, the following method determines whether <em>p</em>
participates in a set of 4 or more collinear points.
<ul>
<li>Think of <em>p</em> as the origin.
<p><li>For each other point <em>q</em>, determine the slope it makes with <em>p</em>.
<p><li>Sort the points according to the slopes
they makes with <em>p</em>.
<p><li>Check if any 3 (or more) adjacent points in the sorted order have equal
slopes with respect to <em>p</em>.
If so, these points, together with <em>p</em>, are collinear.
</ul>

Applying this method for each of the <em>N</em> points in turn yields an
efficient algorithm to the problem.
The algorithm solves the problem because points that have equal 
slopes with respect to <em>p</em> are collinear, and sorting brings such points together.
The algorithm is fast because the bottleneck operation is sorting.

<p>

<center>
<IMG SRC="lines1.png" alt =  "Points and slopes">
</center>
<p>
Write a program <tt>Fast.java</tt> that implements this algorithm.
The order of growth of the running time of your program should be
<em>N</em><sup>2</sup> log <em>N</em> in the worst case and 
it should use space proportional to <em>N</em>.

<p>
<b>APIs.</b> 
Each program should take the name of an input file as a command-line argument,
read the input file (in the format specified below),
print to standard output the line segments discovered (in the format specified below),
and draw to standard draw the line segments discovered (in the format specified below).
Here are the APIs.

<blockquote>
<pre>
<b>public class Brute {
   public static void main(String[] args)
}</b>

<b>public class Fast {
   public static void main(String[] args)
}</b>
</pre>
</blockquote>


<p>
<b>Input format.</b>
Read the points from an input file in the following format:
An integer <em>N</em>, followed by <em>N</em>
pairs of integers (<em>x</em>, <em>y</em>), each between 0 and 32,767.
Below are two examples.

<blockquote>
<pre>
% <b>more input6.txt</b>       % <b>more input8.txt</b>
6                       8
19000  10000             10000      0
18000  10000                 0  10000
32000  10000              3000   7000
21000  10000              7000   3000
 1234   5678             20000  21000
14000  10000              3000   4000
                         14000  15000
                          6000   7000
</pre>
</blockquote>


<p>
<b>Output format.</b>
Print to standard output the line segments that your program discovers, one per line.
Print each line segment as an <em>ordered</em> sequence of its constituent points,
separated by <tt>" -> "</tt>.

<blockquote><pre>
% <b>java Brute input6.txt</b>
(14000, 10000) -> (18000, 10000) -> (19000, 10000) -> (21000, 10000)
(14000, 10000) -> (18000, 10000) -> (19000, 10000) -> (32000, 10000)
(14000, 10000) -> (18000, 10000) -> (21000, 10000) -> (32000, 10000)
(14000, 10000) -> (19000, 10000) -> (21000, 10000) -> (32000, 10000)
(18000, 10000) -> (19000, 10000) -> (21000, 10000) -> (32000, 10000)

% <b>java Brute input8.txt</b>
(10000, 0) -> (7000, 3000) -> (3000, 7000) -> (0, 10000) 
(3000, 4000) -> (6000, 7000) -> (14000, 15000) -> (20000, 21000) 

% <b>java Fast input6.txt</b>
(14000, 10000) -> (18000, 10000) -> (19000, 10000) -> (21000, 10000) -> (32000, 10000) 

% <b>java Fast input8.txt</b>
(10000, 0) -> (7000, 3000) -> (3000, 7000) -> (0, 10000)
(3000, 4000) -> (6000, 7000) -> (14000, 15000) -> (20000, 21000)
</pre>
</blockquote>

Also, draw the points using <tt>draw()</tt> and draw the line segments
using <tt>drawTo()</tt>.
Your programs should call <tt>draw()</tt> once for each point in the input file and
it should call <tt>drawTo()</tt> once for each line segment discovered.
Before drawing, use <tt>StdDraw.setXscale(0, 32768)</tt> and 
<tt>StdDraw.setYscale(0, 32768)</tt> to rescale the coordinate system.
<!--
Do not change the pen color with <tt>setPenColor()</tt> or the 
pen size with <tt>setPenRadius()</tt>.
-->

<p>
For full credit, do not print <em>permutations</em> of points on
a line segment (e.g., if you output
<em>p</em>&rarr;<em>q</em>&rarr;<em>r</em>&rarr;<em>s</em>,
do not also output either
<em>s</em>&rarr;<em>r</em>&rarr;<em>q</em>&rarr;<em>p</em> or
<em>p</em>&rarr;<em>r</em>&rarr;<em>q</em>&rarr;<em>s</em>).
Also, for full credit in <tt>Fast.java</tt>,
do not print or plot <em>subsegments</em> of a line segment containing
5 or more points (e.g., if you output 
<em>p</em>&rarr;<em>q</em>&rarr;<em>r</em>&rarr;<em>s</em>&rarr;<em>t</em>,
do not also output either
<em>p</em>&rarr;<em>q</em>&rarr;<em>s</em>&rarr;<em>t</em> or
<em>q</em>&rarr;<em>r</em>&rarr;<em>s</em>&rarr;<em>t</em>);
you should print out such subsegments in <tt>Brute.java</tt>.


<!--
<p>
<b>Analysis.</b>
Estimate (using tilde notation) the running time (in seconds) of your
two programs as a function of the number of points <em>N</em>.
Provide empirical and mathematical evidence to justify your two hypotheses.
-->

<p>
<b>Deliverables.</b>
Submit only <tt>Brute.java</tt>, <tt>Fast.java</tt>, and <tt>Point.java</tt>.
We will supply <tt>stdlib.jar</tt> and <tt>algs4.jar</tt>.
Your may not call any library functions other than 
those in <tt>java.lang</tt>, <tt>java.util</tt>, <tt>stdlib.jar</tt>, and <tt>algs4.jar</tt>.

</BODY>


<ADDRESS><SMALL>
This assignment was developed by Kevin Wayne.
<br>Copyright &copy; 2005.
</SMALL>
</ADDRESS>
</BODY></HTML>


</HTML>

