package com.codenzasoft.advent2025.day9;

public record Line(Point p1, Point p2) {

  public boolean isVertical() {
    return p1.x() == p2.x();
  }

  public boolean isHorizontal() {
    return p1.y() == p2.y();
  }

  public long getMinX() {
    return Math.min(p1.x(), p2.x());
  }

  public long getMinY() {
    return Math.min(p1.y(), p2.y());
  }

  public long getMaxX() {
    return Math.max(p1.x(), p2.x());
  }

  public long getMaxY() {
    return Math.max(p1.y(), p2.y());
  }

  public boolean xRayCrossesY(Point point) {
    return isVertical()
        && point.x() < getMinX()
        && point.y() >= getMinY()
        && point.y() <= getMaxY();
  }

  public boolean isOnLine(final Point point) {
    if (isHorizontal()) {
      return point.y() == getMinY() && point.x() >= getMinX() && point.x() <= getMaxX();
    }
    return point.x() == getMinX() && point.y() >= getMinY() && point.y() <= getMaxY();
  }
}
