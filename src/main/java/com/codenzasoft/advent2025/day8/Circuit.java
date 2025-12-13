package com.codenzasoft.advent2025.day8;

import java.util.HashSet;
import java.util.Set;

public class Circuit {

  private final Set<Point3D> points = new HashSet<>();

  public void addConnection(final Connection connection) {
    points.add(connection.p1());
    points.add(connection.p2());
  }

  public boolean sharesPoint(final Connection connection) {
    return points.contains(connection.p1()) || points.contains(connection.p2());
  }

  public int getPointsSize() {
    return points.size();
  }

  public void addConnections(final Circuit circuit) {
    points.addAll(circuit.points);
  }
}
