package com.codenzasoft.advent2025.day9;

import java.util.List;

public record Polygon(List<Line> lines) {

  public List<Line> getVerticalLines() {
    return lines().stream().filter(Line::isVertical).toList();
  }

  public boolean contains(final Rectangle rectangle) {
    return rectangle.getVertices().stream().allMatch(this::contains);
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
}
