package com.codenzasoft.advent2025.day9;

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
}
