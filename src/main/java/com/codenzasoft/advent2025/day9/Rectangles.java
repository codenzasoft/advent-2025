package com.codenzasoft.advent2025.day9;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;

public class Rectangles {

  public static void main(final String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-9.txt");
    final List<Point> points = parsePoints(lines);
    System.out.println("The answer to part 1 is: " + part1(points));
  }

  public static List<Point> parsePoints(List<String> lines) {
    return lines.stream().map(Point::parse).toList();
  }

  public static long part1(final List<Point> points) {
    long maxArea = 0;
    int combinations = 0;
    for (int outerIndex = 0; outerIndex < points.size(); outerIndex++) {
      final Point outerPoint = points.get(outerIndex);
      for (int innerIndex = outerIndex + 1; innerIndex < points.size(); innerIndex++) {
        final Point innerPoint = points.get(innerIndex);
        final Rectangle rectangle = new Rectangle(outerPoint, innerPoint);
        final long area = rectangle.getArea();
        combinations++;
        if (area > maxArea) {
          maxArea = area;
        }
      }
    }
    System.out.println("Combinations: " + combinations);
    return maxArea;
  }
}
