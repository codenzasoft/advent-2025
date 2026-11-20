package com.codenzasoft.advent2024.day12;

import com.codenzasoft.advent2024.day6.Direction;
import com.codenzasoft.advent2024.day6.Position;
import java.util.*;
import java.util.stream.Collectors;

public record Region(char plantType, Set<Position> positions) {

  public int computePerimeter(final GardenMap map) {
    int perimeter = 0;
    for (final Position position : positions) {
      for (final Direction direction : Direction.values()) {
        final Position neighbour = position.move(direction);
        if (map.containsPosition(neighbour)) {
          if (!contains(neighbour)) {
            perimeter++;
          }
        } else {
          perimeter++;
        }
      }
    }
    return perimeter;
  }

  public int getArea() {
    return positions().size();
  }

  public boolean contains(final Position position) {
    return positions.contains(position);
  }

  public List<Side> computeSides(final GardenMap map) {
    final List<Edge> edges = computeEdges(map);
    final List<Side> sides = new ArrayList<>();
    final Map<Integer, List<Edge>> horizontalEdgesByRow =
        edges.stream()
            .filter(Edge::isHorizontal)
            .collect(Collectors.groupingBy(edge -> edge.position().row()));
    final Map<Integer, List<Edge>> verticalEdgesByCol =
        edges.stream()
            .filter(Edge::isVertical)
            .collect(Collectors.groupingBy(edge -> edge.position().col()));
    sides.addAll(
        horizontalEdgesByRow.values().stream()
            .flatMap(list -> this.buildSides(list, Edge.COL_COMPARATOR).stream())
            .toList());
    sides.addAll(
        verticalEdgesByCol.values().stream()
            .flatMap(list -> this.buildSides(list, Edge.ROW_COMPARATOR).stream())
            .toList());
    return sides;
  }

  /**
   * Builds sides from adjacent edges in the same row or column.
   *
   * @param edges Edges in the same row or column, but could be on either side.
   * @param comparator Used to sort the edges before joining
   * @return A collection of sides
   */
  private List<Side> buildSides(final List<Edge> edges, final Comparator<Edge> comparator) {
    final List<Side> sides = new ArrayList<>();
    final Map<Direction, List<Edge>> topsAndBottoms =
        edges.stream().collect(Collectors.groupingBy(Edge::direction));
    for (final Map.Entry<Direction, List<Edge>> entry : topsAndBottoms.entrySet()) {
      final Direction direction = entry.getKey();
      final List<Edge> edgeList = entry.getValue().stream().sorted(comparator).toList();
      Side side = null;
      for (final Edge edge : edgeList) {
        if (side == null) {
          side = new Side(edge.position(), edge.position(), direction);
        } else {
          final Optional<Side> maybe = side.append(edge);
          if (maybe.isEmpty()) {
            sides.add(side);
            side = new Side(edge.position(), edge.position(), direction);
          } else {
            side = maybe.get();
          }
        }
      }
      sides.add(side);
    }
    return sides;
  }

  public List<Edge> computeEdges(final GardenMap map) {
    final List<Edge> edges = new ArrayList<>();
    for (final Position position : positions) {
      for (final Direction direction : Direction.values()) {
        final Position neighbour = position.move(direction);
        if (map.containsPosition(neighbour)) {
          if (!contains(neighbour)) {
            edges.add(new Edge(position, direction));
          }
        } else {
          edges.add(new Edge(position, direction));
        }
      }
    }
    return edges;
  }
}
