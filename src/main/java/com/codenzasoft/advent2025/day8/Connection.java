package com.codenzasoft.advent2025.day8;

public record Connection(Point3D p1, Point3D p2, double distance) {

  public boolean sharesPoint(final Connection connection) {
    return containsPoint(connection.p1()) || containsPoint(connection.p2());
  }

  public boolean containsPoint(final Point3D point) {
    return p1().equals(point) || p2().equals(point);
  }
}
