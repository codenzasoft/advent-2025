package com.codenzasoft.advent2025.day9;

import java.util.ArrayList;
import java.util.List;

public record Line(Point p1, Point p2) {

  public boolean isVertical() {
    return p1().x() == p2().x();
  }

  public boolean isHorizontal() {
    return p1().y() == p2().y();
  }

  public long getMinX() {
    return Math.min(p1().x(), p2().x());
  }

  public long getMinY() {
    return Math.min(p1().y(), p2().y());
  }

  public long getMaxX() {
    return Math.max(p1().x(), p2().x());
  }

  public long getMaxY() {
    return Math.max(p1().y(), p2().y());
  }

  public boolean xRayCrossesY(Point point) {
    return isVertical() && point.x() < getMinX() && point.y() > getMinY() && point.y() <= getMaxY();
  }

  public List<Point> points() {
    final List<Point> points = new ArrayList<>();
    if (isHorizontal()) {
      for (long x = getMinX(); x <= getMaxX(); x++) {
        points.add(new Point(x, getMinY()));
      }
    } else {
      for (long y = getMinY(); y <= getMaxY(); y++) {
        points.add(new Point(getMinX(), y));
      }
    }
    return points;
  }

  public boolean crosses(final Line line) {
    if (isVertical() && line.isHorizontal()) {
      return line.getMinX() < getMinX()
          && line.getMaxX() > getMinX()
          && line.getMinY() >= getMinY()
          && line.getMinY() < getMaxY();
    }
    if (isHorizontal() && line.isVertical()) {
      return line.getMaxY() > getMinY()
          && line.getMinY() < getMinY()
          && line.getMinX() >= getMinX()
          && line.getMinX() < getMaxX();
    }
    return false;
  }

  public boolean isOnLine(final Point point) {
    if (isHorizontal()) {
      return point.y() == getMinY() && point.x() >= getMinX() && point.x() <= getMaxX();
    }
    return point.x() == getMinX() && point.y() >= getMinY() && point.y() <= getMaxY();
  }
}
