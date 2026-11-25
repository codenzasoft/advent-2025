package com.codenzasoft.advent2024.day15;

import com.codenzasoft.advent2024.day6.Direction;
import com.codenzasoft.advent2024.day6.Position;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public record WarehouseMap(
    int rows, int cols, Set<Position> walls, Set<Position> boxes, Position robot) {

  public static WarehouseMap parse(List<String> input) {
    final int rows = input.size();
    final int cols = input.get(0).length();
    Set<Position> walls = new HashSet<>();
    Set<Position> boxes = new HashSet<>();
    Position robot = null;
    for (int row = 0; row < rows; row++) {
      final String line = input.get(row);
      for (int col = 0; col < cols; col++) {
        final char symbol = line.charAt(col);
        switch (symbol) {
          case '#':
            walls.add(new Position(row, col));
            break;
          case 'O':
            boxes.add(new Position(row, col));
            break;
          case '@':
            robot = new Position(row, col);
            break;
          default:
            break;
        }
      }
    }
    return new WarehouseMap(rows, cols, walls, boxes, robot);
  }

  public static List<Direction> buildDirections(List<String> input) {
    final List<Direction> directions = new ArrayList<>();
    for (final String line : input) {
      for (final char symbol : line.toCharArray()) {
        directions.add(Direction.fromSymbol(symbol));
      }
    }
    return directions;
  }
}
