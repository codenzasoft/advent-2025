package com.codenzasoft.advent2025.day8;

import java.util.Set;

public record Connection(Point3D p1, Point3D p2, double distance) {

  public boolean sharesPoint(final Connection connection) {
    return containsPoint(connection.p1()) || containsPoint(connection.p2());
  }

  public boolean isCompletelyContainedIn(final Set<Point3D> set) {
    return set.contains(p1()) && set.contains(p2());
  }

  public boolean isPartiallyContainedIn(final Set<Point3D> set) {
    return set.contains(p1()) || set.contains(p2());
  }

  public boolean containsPoint(final Point3D point) {
    return p1().equals(point) || p2().equals(point);
  }
}
