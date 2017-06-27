import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import static java.util.Arrays.asList;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toList;

public class Test {
    final Set<Rectangle> rectangles;
    static int segment;

    Test(List<Rectangle> rectangles) {
        if (isNull(rectangles) || rectangles.isEmpty())
            throw new IllegalArgumentException();
        setSegmentSize(rectangles.get(0));
        this.rectangles = new HashSet<>(rectangles);
    }

    void setSegmentSize(Rectangle r) {
        NavigableSet<Point> points = new TreeSet<>((o1, o2) -> o1.x - o2.x);
        points.addAll(asList(r.p1, r.p2, r.p3, r.p4));
        segment = (points.pollLast().x - points.pollFirst().x)*2;
    }

    void map(List<Point> points) {
        points.forEach(rectangles::contains);
    }

    public static void main(String[] args) {
        Test t = new Test(asList(
                new Rectangle(new Point(0, 0),  new Point(10, 10), new Point(0, 10),  new Point(10, 0)),
                new Rectangle(new Point(50, 0), new Point(60, 10), new Point(50, 10), new Point(60, 0))
        ));

        List<Point> points = asList(new Point(5, 5), new Point(49, 6));
        t.map(points);

        System.out.println(points.stream().filter(p -> nonNull(p.r)).collect(toList()));
    }
}

class Point {
    final int x;
    final int y;
    Rectangle r;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     *  The most important part.
     *  Here must be some logic which
     *  decides if this point fits
     *  given rectangle.
     */
    boolean fits(Rectangle r) {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o instanceof Rectangle) {
            Rectangle r = (Rectangle) o;
            if (fits(r)) {
                this.r = r;
                return true;
            }
            return false;
        }

        if (!(o instanceof Point)) return false;

        Point point = (Point) o;

        if (x != point.x) return false;
        return y == point.y;

    }

    @Override
    public int hashCode() {
        return x/Test.segment;
    }

    @Override
    public String toString() {
        return "P{" + hashCode() + ", r=" + r + '}';
    }
}

class Rectangle {
    final Point p1;
    final Point p2;
    final Point p3;
    final Point p4;

    final int centerX;

    Rectangle(Point p1, Point p2, Point p3, Point p4) {
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;

        centerX = (p1.x + p2.x + p3.x + p4.x)/4;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rectangle)) return false;

        Rectangle rectangle = (Rectangle) o;

        if (!p1.equals(rectangle.p1)) return false;
        if (!p2.equals(rectangle.p2)) return false;
        if (!p3.equals(rectangle.p3)) return false;
        return p4.equals(rectangle.p4);

    }

    @Override
    public int hashCode() {
        return centerX/Test.segment;
    }

    @Override
    public String toString() {
        return "R{" + hashCode() + '}';
    }
}

