package com.codenzasoft.advent2024.day15;

import com.codenzasoft.advent2024.day6.Direction;
import com.codenzasoft.advent2024.day6.Position;
import java.util.*;

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
    return new WarehouseMap(rows, cols, Set.copyOf(walls), Set.copyOf(boxes), robot);
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

  // TODO:
  public WarehouseMap moveRobot(Direction direction) {
    final List<Position> moves = new ArrayList<>();
    if (canMove(robot(), direction, moves)) {
      final Set<Position> newBoxes = new HashSet<>(boxes);
      moves.forEach(newBoxes::remove);
      moves.forEach(move -> newBoxes.add(move.move(direction)));
      return new WarehouseMap(rows, cols, walls, Set.copyOf(newBoxes), robot.move(direction));
    }
    return this;
  }

  public boolean canMove(Position position, Direction direction, List<Position> boxesToMove) {
    final Position destination = position.move(direction);
    if (isInBounds(destination)) {
      if (isEmpty(destination)) {
        if (!robot().equals(position)) {
          boxesToMove.add(position);
        }
        return true;
      }
      if (isWall(destination)) {
        return false;
      }
      if (isBox(destination)) {
        final boolean ok = canMove(destination, direction, boxesToMove);
        if (ok) {
          if (!robot().equals(position)) {
            boxesToMove.add(position);
          }
        }
        return ok;
      }
    }
    return false;
  }

  public boolean isEmpty(Position position) {
    return !(walls.contains(position) || boxes.contains(position) || robot().equals(position));
  }

  public boolean isBox(Position position) {
    return boxes.contains(position);
  }

  public boolean isWall(Position position) {
    return walls.contains(position);
  }

  public boolean isInBounds(Position position) {
    return position.row() > 0
        && position.row() < rows()
        && position.col() > 0
        && position.col() < cols();
  }

  public long boxGpsSum() {
    return boxes().stream().mapToLong(this::gps).sum();
  }

  public long gps(Position position) {
    return position.row() * 100L + position.col();
  }

  public String toString() {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < rows(); row++) {
      for (int col = 0; col < cols(); col++) {
        final Position position = new Position(row, col);
        if (isEmpty(position)) {
          builder.append(".");
        } else if (isWall(position)) {
          builder.append("#");
        } else if (isBox(position)) {
          builder.append("O");
        } else if (robot().equals(position)) {
          builder.append("@");
        }
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
