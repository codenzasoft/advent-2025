package com.codenzasoft.advent2025.day9;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.ArrayList;
import java.util.List;

public class Rectangles {

  public static void main(final String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-9.txt");
    final List<Point> points = parsePoints(lines);
    System.out.println("The answer to part 1 is: " + part1(points));
    System.out.println("The answer to part 2 is: " + part2(points));
  }

  public static List<Point> parsePoints(List<String> lines) {
    return lines.stream().map(Point::parse).toList();
  }

  public static long part1(final List<Point> points) {
    final List<Rectangle> rectangles = buildRectangles(points);
    System.out.println("Total rectangles: " + rectangles.size());
    return rectangles.stream().mapToLong(Rectangle::getArea).max().orElse(0L);
  }

  public static List<Rectangle> buildRectangles(final List<Point> points) {
    List<Rectangle> rectangles = new ArrayList<>();
    for (int outerIndex = 0; outerIndex < points.size(); outerIndex++) {
      final Point outerPoint = points.get(outerIndex);
      for (int innerIndex = outerIndex + 1; innerIndex < points.size(); innerIndex++) {
        final Point innerPoint = points.get(innerIndex);
        final Rectangle rectangle = new Rectangle(outerPoint, innerPoint);
        rectangles.add(rectangle);
      }
    }
    return rectangles;
  }

  public static long part2(final List<Point> points) {
    final Polygon polygon = buildPolygon(points);
    long maxArea = 0;
    int combinations = 0;
    for (int outerIndex = 0; outerIndex < points.size(); outerIndex++) {
      final Point outerPoint = points.get(outerIndex);
      for (int innerIndex = outerIndex + 1; innerIndex < points.size(); innerIndex++) {
        final Point innerPoint = points.get(innerIndex);
        final Rectangle rectangle = new Rectangle(outerPoint, innerPoint);
        if (polygon.contains(rectangle)) {
          final long area = rectangle.getArea();
          combinations++;
          if (area > maxArea) {
            maxArea = area;
          }
        }
      }
    }
    System.out.println("Combinations: " + combinations);
    return maxArea;
  }

  public static Polygon buildPolygon(final List<Point> points) {
    final List<Line> lines = new ArrayList<>();
    for (int i = 0; i < points.size() - 1; i++) {
      final Point p1 = points.get(i);
      final Point p2 = points.get(i + 1);
      final Line line = new Line(p1, p2);
      lines.add(line);
    }
    final Point firstPoint = points.get(0);
    final Point lastPoint = points.get(points.size() - 1);
    lines.add(new Line(lastPoint, firstPoint));
    final Polygon polygon = new Polygon(lines);
    return polygon;
  }
}
