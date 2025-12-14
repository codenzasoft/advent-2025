package com.codenzasoft.advent2025.day9;

import java.util.List;

public record Rectangle(Point corner1, Point corner2) {

  public long getArea() {
    return getWidth() * getHeight();
  }

  public long getWidth() {
    return Math.abs(corner1.x() - corner2.x()) + 1;
  }

  public long getHeight() {
    return Math.abs(corner1.y() - corner2.y()) + 1;
  }

  public long getMinX() {
    return Math.min(corner1.x(), corner2.x());
  }

  public long getMinY() {
    return Math.min(corner1.y(), corner2.y());
  }

  public long getMaxX() {
    return Math.max(corner1.x(), corner2.x());
  }

  public long getMaxY() {
    return Math.max(corner1.y(), corner2.y());
  }

  public List<Point> getVertices() {
    return List.of(
        new Point(getMinX(), getMinY()),
        new Point(getMaxX(), getMinY()),
        new Point(getMaxX(), getMaxY()),
        new Point(getMinX(), getMaxY()));
  }

  public boolean contains(Point point) {
    return point.x() >= getMinX()
        && point.x() <= getMaxX()
        && point.y() >= getMinY()
        && point.y() <= getMaxY();
  }
}
