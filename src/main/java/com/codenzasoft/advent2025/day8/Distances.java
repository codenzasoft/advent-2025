package com.codenzasoft.advent2025.day8;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Distances {

  public static void main(final String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-8.txt");
    final List<Point3D> point3DS = lines.stream().map(Point3D::parse).toList();
    System.out.println("The answer to part 1 is: " + part1(point3DS, 1000));
  }

  public static long part1(final List<Point3D> points, final int numConnections) {
    final List<Set<Point3D>> circuits = findNShortest(points, numConnections);
    System.out.println("Part 1 number of circuits: " + circuits.size());
    final List<Integer> sizes = new ArrayList<>(circuits.stream().map(Set::size).toList());
    sizes.sort(Comparator.reverseOrder());
    return sizes.stream().limit(3).reduce(1, (a, b) -> a * b);
  }

  public static List<Set<Point3D>> buildCircuits(final List<Connection> connections) {
    final Map<Point3D, Set<Point3D>> mappings = new HashMap<>();
    for (final Connection connection : connections) {
      final Set<Point3D> p1top2 = mappings.computeIfAbsent(connection.p1(), k -> new HashSet<>());
      p1top2.add(connection.p2());
      final Set<Point3D> p2top1 = mappings.computeIfAbsent(connection.p2(), k -> new HashSet<>());
      p2top1.add(connection.p1());
    }
    final List<Set<Point3D>> circuits = new ArrayList<>();
    for (final Point3D point : mappings.keySet()) {
      if (circuits.stream().filter(set -> set.contains(point)).findAny().isEmpty()) {
        final Set<Point3D> circuit = new HashSet<>();
        addConnectedPoints(point, mappings, circuit);
        circuits.add(circuit);
      }
    }
    return circuits;
  }

  private static void addConnectedPoints(
      final Point3D point, final Map<Point3D, Set<Point3D>> mappings, final Set<Point3D> circuit) {
    if (circuit.contains(point)) {
      return;
    }
    circuit.add(point);
    final Set<Point3D> connected = mappings.get(point);
    if (connected != null) {
      for (final Point3D c : connected) {
        addConnectedPoints(c, mappings, circuit);
      }
    }
  }

  public static List<Set<Point3D>> findNShortest(
      final List<Point3D> points, final int numConnections) {
    final List<Set<Point3D>> circuits = new ArrayList<>();
    Connection prev = new Connection(null, null, 0d);
    int i = 0;
    while (i < numConnections) {
      final Connection next = findShortestDistance(points, prev);
      if (addConnectionToCircuits(next, circuits)) {
        i++;
      }
      prev = next;
    }
    return circuits;
  }

  public static boolean addConnectionToCircuits(
      final Connection connection, final List<Set<Point3D>> circuits) {
    final Optional<Set<Point3D>> optional =
        circuits.stream().filter(c -> connection.isPartiallyContainedIn(c)).findAny();
    if (optional.isPresent()) {
      final Set<Point3D> circuit = optional.get();
      if (connection.isCompletelyContainedIn(circuit)) {
        return false;
      }
      circuit.add(connection.p1());
      circuit.add(connection.p2());
      return true;
    } else {
      final Set<Point3D> set = new HashSet<>();
      set.add(connection.p1());
      set.add(connection.p2());
      circuits.add(set);
      return true;
    }
  }

  public static Connection findShortestDistance(
      final List<Point3D> points, final Connection limit) {
    Connection shortest = new Connection(null, null, Double.MAX_VALUE);
    final double lowerLimit = limit.distance();
    for (int outerIndex = 0; outerIndex < points.size(); outerIndex++) {
      final Point3D outerPoint = points.get(outerIndex);
      for (int innerIndex = outerIndex + 1; innerIndex < points.size(); innerIndex++) {
        final Point3D innerPoint = points.get(innerIndex);
        final double dist = innerPoint.distance(outerPoint);
        final Connection candidate = new Connection(outerPoint, innerPoint, dist);
        if (dist < shortest.distance()
            && Double.compare(dist, lowerLimit) >= 0
            && !candidate.equals(limit)) {
          shortest = candidate;
        }
      }
    }
    return shortest;
  }
}
