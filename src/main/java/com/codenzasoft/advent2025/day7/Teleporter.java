package com.codenzasoft.advent2025.day7;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.awt.*;
import java.util.*;
import java.util.List;

public class Teleporter {

  public static void main(String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-7.txt");
    System.out.println("The number of splits for part 1 is: " + traceBeams(new Manifold(lines)));
    System.out.println(
        "The number of timelines for part 2 is: " + compressExits(new Manifold(lines)));
  }

  public static int traceBeams(final Manifold manifold) {
    List<Set<Point>> rows = new ArrayList<>();
    for (int i = 0; i < manifold.getRowCount(); i++) {
      rows.add(new HashSet<>());
    }
    final Point entrance = manifold.findEntrance();
    rows.get(entrance.y).add(entrance);
    int splits = 0;
    int rowIndex = 1;
    while (rowIndex < manifold.getRowCount()) {
      Set<Point> prevBeams = rows.get(rowIndex - 1);
      Set<Point> nextBeams = rows.get(rowIndex);
      for (Point beam : prevBeams) {
        if (manifold.isSplitter(new Point(beam.x, beam.y + 1))) {
          splits++;
          final Node node = new Node(new Point(beam.x, beam.y + 1));
          nextBeams.add(new Point(beam.x - 1, beam.y + 1));
          nextBeams.add(new Point(beam.x + 1, beam.y + 1));
        } else {
          nextBeams.add(new Point(beam.x, beam.y + 1));
        }
      }
      rowIndex++;
    }
    return splits;
  }

  public static int buildTree(final Manifold manifold) {
    List<List<Edge>> rows = new ArrayList<>();
    for (int i = 0; i < manifold.getRowCount(); i++) {
      rows.add(new ArrayList<>());
    }
    final Point entrance = manifold.findEntrance();
    rows.get(entrance.y).add(new Edge(entrance, null));
    int rowIndex = 1;
    Node root = null;
    int leafCount = 0;
    while (rowIndex < manifold.getRowCount()) {
      List<Edge> prevEdges = rows.get(rowIndex - 1);
      List<Edge> nextEdges = rows.get(rowIndex);
      for (Edge edge : prevEdges) {
        if (manifold.isSplitter(new Point(edge.location().x, edge.location().y + 1))) {
          final Node node = new Node(new Point(edge.location().x, edge.location().y + 1));
          if (edge.parent() == null) {
            root = node;
          } else {
            if (edge.location().x < edge.parent().getLocation().x) {
              edge.parent().setLeft(node);
            } else {
              edge.parent().setRight(node);
            }
          }
          nextEdges.add(new Edge(new Point(edge.location().x - 1, edge.location().y + 1), node));
          nextEdges.add(new Edge(new Point(edge.location().x + 1, edge.location().y + 1), node));
        } else {
          if (rowIndex == manifold.getRowCount() - 1) {
            // leaf node at exit
            leafCount++;
            final Node node = new Node(new Point(edge.location().x, edge.location().y));
            if (edge.location().x < edge.parent().getLocation().x) {
              edge.parent().setLeft(node);
            } else {
              edge.parent().setRight(node);
            }
          } else {
            nextEdges.add(
                new Edge(new Point(edge.location().x, edge.location().y + 1), edge.parent()));
          }
        }
      }
      rowIndex++;
    }
    return leafCount;
  }

  public static int countExits(final Manifold manifold) {
    List<Integer> prevEdges = new LinkedList<>();
    List<Integer> nextEdges = new LinkedList<>();
    final Point entrance = manifold.findEntrance();
    prevEdges.add(entrance.x);
    int rowIndex = 1;
    int leafCount = 0;
    while (rowIndex < manifold.getRowCount()) {
      for (Integer x : prevEdges) {
        if (rowIndex == manifold.getRowCount() - 1) {
          // leaf node at exit
          leafCount++;
        } else {
          if (manifold.isSplitter(new Point(x, rowIndex + 1))) {
            nextEdges.add(x - 1);
            nextEdges.add(x + 1);
          } else {
            nextEdges.add(x);
          }
        }
      }
      System.out.println("Row: " + rowIndex + " Edges: " + nextEdges.size());
      prevEdges = nextEdges;
      nextEdges = new LinkedList<>();
      rowIndex++;
    }
    return leafCount;
  }

  public static long compressExits(final Manifold manifold) {
    LinkedList<Beam> prev = new LinkedList<>();
    LinkedList<Beam> next = new LinkedList<>();
    final Point entrance = manifold.findEntrance();
    prev.add(new Beam(entrance.x, 1));
    int rowIndex = 1;
    long leafCount = 0;
    while (rowIndex < manifold.getRowCount()) {
      for (Beam beam : prev) {
        if (rowIndex == manifold.getRowCount() - 1) {
          // leaf node at exit
          leafCount += beam.edges();
        } else {
          if (manifold.isSplitter(new Point(beam.x(), rowIndex + 1))) {
            next.add(new Beam(beam.x() - 1, beam.edges()));
            next.add(new Beam(beam.x() + 1, beam.edges()));
          } else {
            next.add(beam);
          }
        }
      }
      // compress common edges
      prev = compress(next);
      System.out.println("Row: " + rowIndex + " compressed edges: " + prev.size());
      next = new LinkedList<>();
      rowIndex++;
    }
    return leafCount;
  }

  public static LinkedList<Beam> compress(final LinkedList<Beam> beams) {
    if (beams.isEmpty()) {
      return beams;
    }
    LinkedList<Beam> compressed = new LinkedList<>();
    Beam currBeam = beams.removeFirst();
    while (!beams.isEmpty()) {
      Beam nextBeam = beams.removeFirst();
      Optional<Beam> combined = currBeam.combine(nextBeam);
      if (combined.isEmpty()) {
        compressed.add(currBeam);
        currBeam = nextBeam;
      } else {
        currBeam = combined.get();
      }
    }
    compressed.add(currBeam);
    return compressed;
  }
}
