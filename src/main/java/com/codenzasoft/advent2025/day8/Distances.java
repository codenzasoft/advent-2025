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
    System.out.println("The answer to part 2 is: " + part2(point3DS));
  }

  public static long part1(final List<Point3D> points, final int numConnections) {
    final List<Connection> connections = buildConnections(points);
    System.out.println("Number of possible connections: " + connections.size());
    connections.sort(Comparator.comparingDouble(Connection::distance));

    final List<Circuit> circuits = new ArrayList<>(points.stream().map(Circuit::of).toList());
    for (int i = 0; i < numConnections; i++) {
      final Connection connection = connections.get(i);
      addConnectionToCircuits(connection, circuits);
    }

    System.out.println("Part 1 number of circuits: " + circuits.size());
    final List<Integer> sizes =
        new ArrayList<>(circuits.stream().map(Circuit::getPointsSize).toList());
    sizes.sort(Comparator.reverseOrder());
    return sizes.stream().limit(3).reduce(1, (a, b) -> a * b);
  }

  public static long part2(final List<Point3D> points) {
    final List<Connection> connections = buildConnections(points);
    System.out.println("Number of possible connections: " + connections.size());
    connections.sort(Comparator.comparingDouble(Connection::distance));

    final List<Circuit> circuits = new ArrayList<>(points.stream().map(Circuit::of).toList());
    int i = 0;
    Connection lastConnection = null;
    while (circuits.size() > 1) {
      lastConnection = connections.get(i);
      addConnectionToCircuits(lastConnection, circuits);
      i++;
    }

    System.out.println("Part 2 final connection: " + lastConnection);
    return lastConnection.p1().x() * lastConnection.p2().x();
  }

  /**
   * Adds the provided {@link Connection} to a {@link Circuit} if the ends are not already
   * connected.
   *
   * @param connection A {@link Connection} to add to an existing {@link Circuit}
   * @param circuits The list of existing {@link Circuit}s.
   */
  public static void addConnectionToCircuits(
      final Connection connection, final List<Circuit> circuits) {

    if (circuits.stream().anyMatch(circuit -> circuit.contains(connection))) {
      // already connected
      return;
    }

    final List<Circuit> merge =
        circuits.stream().filter(circuit -> circuit.canAdd(connection)).toList();
    if (merge.isEmpty()) {
      // need to create a new circuit?
      throw new IllegalStateException("Oops");
    } else if (merge.size() == 1) {
      final Circuit circuit = merge.get(0);
      circuits.remove(circuit);
      circuits.add(circuit.addConnection(connection).get());
    } else if (merge.size() == 2) {
      final Circuit circuit1 = merge.get(0);
      final Circuit circuit2 = merge.get(1);
      circuits.remove(circuit1);
      circuits.remove(circuit2);
      circuits.add(Circuit.of(circuit1, circuit2));
    } else {
      throw new IllegalStateException("Oops");
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
