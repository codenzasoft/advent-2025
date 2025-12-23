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
    return rectangle.getLines().stream().allMatch(this::contains);
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
    //    if (contains(line.p1()) && contains(line.p2())) {
    //      if (line.isHorizontal()) {
    //        return getVerticalLines().stream().noneMatch(v -> v.crosses(line));
    //      }
    //      if (line.isVertical()) {
    //        return getHorizontalLines().stream().noneMatch(h -> h.crosses(line));
    //      }
    //    }
    //    return false;
  }
}
