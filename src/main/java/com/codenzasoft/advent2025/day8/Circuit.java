package com.codenzasoft.advent2025.day8;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public record Circuit(Set<Point3D> points) {

  public static Circuit of(Set<Point3D> points) {
    return new Circuit(Set.copyOf(points));
  }

  public static Circuit of(Point3D point) {
    return new Circuit(Set.of(point));
  }

  public static Circuit of(final Circuit circuit1, final Circuit circuit2) {
    final Set<Point3D> points = new HashSet<>(circuit1.points());
    points.addAll(circuit2.points());
    return new Circuit(points);
  }

  public static Circuit empty() {
    return new Circuit(Set.of());
  }

  public Optional<Circuit> addConnection(final Connection connection) {
    if (canAdd(connection)) {
      final Set<Point3D> pointsCopy = new HashSet<>(points);
      pointsCopy.add(connection.p1());
      pointsCopy.add(connection.p2());
      return Optional.of(Circuit.of(pointsCopy));
    }
    return Optional.empty();
  }

  public boolean contains(final Connection connection) {
    return points().contains(connection.p1()) && points().contains(connection.p2());
  }

  public boolean canAdd(final Connection connection) {
    if (points().isEmpty()) {
      return true;
    }
    if (contains(connection)) {
      return false;
    }
    return points().contains(connection.p1()) || points().contains(connection.p2());
  }

  public boolean canMerge(final Circuit circuit) {
    return circuit.points().stream().anyMatch(p -> points().contains(p));
  }

  public int getPointsSize() {
    return points.size();
  }

  public Optional<Circuit> merge(final Circuit circuit) {
    if (canMerge(circuit)) {
      final Set<Point3D> pointsCopy = new HashSet<>(points());
      pointsCopy.addAll(circuit.points());
      return Optional.of(Circuit.of(pointsCopy));
    }
    return Optional.empty();
  }
}
