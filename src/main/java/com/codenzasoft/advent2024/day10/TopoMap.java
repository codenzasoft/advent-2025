package com.codenzasoft.advent2024.day10;

import com.codenzasoft.advent2024.day6.Direction;
import com.codenzasoft.advent2024.day6.Position;
import java.util.ArrayList;
import java.util.List;

public record TopoMap(int[][] rows) {

  public static TopoMap parse(List<String> input) {
    int rowCount = input.size();
    int columnCount = input.getFirst().length();
    int[][] rows = new int[rowCount][columnCount];
    for (int i = 0; i < rowCount; i++) {
      String row = input.get(i);
      for (int j = 0; j < columnCount; j++) {
        rows[i][j] = Integer.parseInt(row.substring(j, j + 1));
      }
    }
    return new TopoMap(rows);
  }

  public int getRowCount() {
    return rows.length;
  }

  public int getColumnCount() {
    return rows[0].length;
  }

  public List<Position> getTrailheads() {
    List<Position> trailheads = new ArrayList<>();
    for (int row = 0; row < rows.length; row++) {
      for (int col = 0; col < rows[0].length; col++) {
        final Position position = new Position(row, col);
        if (getValue(position) == 0) {
          trailheads.add(position);
        }
      }
    }
    return trailheads;
  }

  public boolean isSummit(final Position position) {
    return getValue(position) == 9;
  }

  public List<Position> getAdjacentOnTrail(final Position position) {
    return getAdjacentPositions(position).stream()
        .filter(this::contains)
        .filter(next -> isOnTrail(position, next))
        .toList();
  }

  public List<Position> getAdjacentPositions(final Position position) {
    final List<Position> adjacentPositions = new ArrayList<>();
    adjacentPositions.add(position.move(Direction.UP));
    adjacentPositions.add(position.move(Direction.DOWN));
    adjacentPositions.add(position.move(Direction.LEFT));
    adjacentPositions.add(position.move(Direction.RIGHT));
    return adjacentPositions;
  }

  public boolean isOnTrail(final Position from, final Position to) {
    return getValue(to) - getValue(from) == 1;
  }

  public boolean contains(final Position position) {
    return position.row() >= 0
        && position.row() < getRowCount()
        && position.col() >= 0
        && position.col() < getColumnCount();
  }

  public int getValue(final Position position) {
    if (contains(position)) {
      return rows[position.row()][position.col()];
    }
    return -1;
  }
}
