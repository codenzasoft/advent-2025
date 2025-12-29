package com.codenzasoft.advent2025.day9;

import java.util.List;

public record Polygon(List<Line> lines) {

  public List<Line> getVerticalLines() {
    return lines().stream().filter(Line::isVertical).toList();
  }

  public List<Line> getHorizontalLines() {
    return lines().stream().filter(Line::isHorizontal).toList();
  }

  public boolean contains(final Rectangle rectangle) {
    if (rectangle.getVertices().stream().allMatch(this::contains)) {
      for (final Line line : rectangle.getLines()) {
        if (line.isVertical()) {
          if (getHorizontalLines().stream().anyMatch(hline -> hline.crosses(line))) {
            return false;
          }
        } else {
          if (getVerticalLines().stream().anyMatch(vline -> vline.crosses(line))) {
            return false;
          }
        }
      }
      return true;
    }
    return false;
  }

  public boolean contains(final Point point) {
    final boolean online = lines().stream().anyMatch(line -> line.isOnLine(point));
    if (online) {
      return true;
    }
    final long crossings =
        getVerticalLines().stream().filter(line -> line.xRayCrossesY(point)).count();
    return crossings % 2 == 1;
  }

  public boolean contains(final Line line) {
    return line.points().stream().allMatch(this::contains);
  }
}
