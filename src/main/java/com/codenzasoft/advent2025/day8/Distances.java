package com.codenzasoft.advent2025.day8;

import com.codenzasoft.advent2025.PuzzleHelper;

import java.util.ArrayList;
import java.util.List;

public class Distances {

  public static void main(final String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-8.txt");
    final List<Point3D> point3DS = lines.stream().map(Point3D::parse).toList();
  }

  public static long part1(final List<Point3D> points) {
    final List<Connection> connections = findNShortest(points, 10);
    final List<Circuit> circuits = new ArrayList<>();
    // does not account for joined circuits
    for (final Connection connection : connections) {
      List<Circuit> found = new ArrayList<>();
      for (final Circuit circuit : circuits) {
        if (circuit.sharesPoint(connection) ) {
          found.add(circuit);
          circuit.addConnection(connection);
        }
      }
      if (found.isEmpty()) {
        final Circuit circuit = new Circuit();
        circuit.addConnection(connection);
        circuits.add(circuit);
      } else {
        if (found.size() == 1) {
          final Circuit circuit = found.get(0);
          circuit.addConnection(connection);
        } else {
          final Circuit circuit = new Circuit();
          for (final Circuit c : found) {
            circuit.addConnections(c);
          }
          circuits.removeAll(found);
          circuits.add(circuit);
        }
      }
    }
    System.out.println("Part 1 number of circuits: " + circuits.size());
    return circuits.stream().mapToInt(Circuit::getPointsSize).reduce(1, (a, b) -> a * b);
  }

  public static List<Connection> findNShortest(final List<Point3D> points, final int numConnections) {
    final List<Connection> connections = new ArrayList<>();
    Connection prev = new Connection(null, null, 0d);
    for (int i = 0; i < numConnections; i++) {
      final Connection next = findShortestDistance(points, prev.distance());
      connections.add(next);
      prev = next;
    }
    return connections;
  }

  public static Connection findShortestDistance(final List<Point3D> points, double lowerLimit) {
    Connection shortest = new Connection(null, null, Double.MAX_VALUE);
    for (int outerIndex = 0; outerIndex < points.size(); outerIndex++) {
      final Point3D outerPoint = points.get(outerIndex);
      for (int innerIndex = outerIndex + 1; innerIndex < points.size(); innerIndex++) {
        final Point3D innerPoint = points.get(innerIndex);
        final double dist = innerPoint.distance(outerPoint);
        if (dist < shortest.distance() && dist > lowerLimit) {
          shortest = new Connection(outerPoint, innerPoint, dist);
        }
      }
    }
    return shortest;
  }

}


