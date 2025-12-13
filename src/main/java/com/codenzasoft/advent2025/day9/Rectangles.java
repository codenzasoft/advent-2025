package com.codenzasoft.advent2025.day9;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.awt.*;
import java.util.List;

public class Rectangles {

  public static void main(final String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-9.txt");
    final List<Point> points = parsePoints(lines);
    System.out.println("The answer to part 1 is: " + part1(points));
  }

  public static List<Point> parsePoints(List<String> lines) {
    return lines.stream()
        .map(l -> l.split(","))
        .map(text -> new Point(Integer.parseInt(text[0]), Integer.parseInt(text[1])))
        .toList();
  }

  public static long part1(final List<Point> points) {
    long maxArea = 0;
    int combinations = 0;
    for (int outerIndex = 0; outerIndex < points.size(); outerIndex++) {
      final Point outerPoint = points.get(outerIndex);
      for (int innerIndex = outerIndex + 1; innerIndex < points.size(); innerIndex++) {
        final Point innerPoint = points.get(innerIndex);
        final long area =
            (long) (Math.abs(outerPoint.x - innerPoint.x) + 1)
                * (long) (Math.abs(outerPoint.y - innerPoint.y) + 1);
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
