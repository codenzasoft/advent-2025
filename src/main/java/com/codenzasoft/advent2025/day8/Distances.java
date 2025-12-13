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
    final List<Connection> connections = buildConnections(points);
    System.out.println("Number of possible connections: " + connections.size());
    connections.sort(Comparator.comparingDouble(Connection::distance));

    final List<Set<Point3D>> circuits = new ArrayList<>();
    int i = 0;
    int connected = 0;
    while (connected < numConnections) {
      final Connection connection = connections.get(i);
      if (addConnectionToCircuits(connection, circuits)) {
        connected++;
      }
      i++;
    }

    System.out.println("Part 1 number of circuits: " + circuits.size());
    final List<Integer> sizes = new ArrayList<>(circuits.stream().map(Set::size).toList());
    sizes.sort(Comparator.reverseOrder());
    return sizes.stream().limit(3).reduce(1, (a, b) -> a * b);
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

  public static List<Connection> buildConnections(final List<Point3D> points) {
    final List<Connection> connections = new ArrayList<>();
    for (int outerIndex = 0; outerIndex < points.size(); outerIndex++) {
      final Point3D outerPoint = points.get(outerIndex);
      for (int innerIndex = outerIndex + 1; innerIndex < points.size(); innerIndex++) {
        final Point3D innerPoint = points.get(innerIndex);
        connections.add(new Connection(outerPoint, innerPoint, innerPoint.distance(outerPoint)));
      }
    }
    return connections;
  }
}
