package com.codenzasoft.advent2024.day15;

import com.codenzasoft.advent2024.day6.Direction;
import com.codenzasoft.advent2024.day6.Position;
import java.util.*;

public record WarehouseMap(int rows, int cols, List<MapObject> objects) {

  public static WarehouseMap parse(List<String> input) {
    final int rows = input.size();
    final int cols = input.getFirst().length();
    List<MapObject> objects = new ArrayList<>();
    for (int row = 0; row < rows; row++) {
      final String line = input.get(row);
      for (int col = 0; col < cols; col++) {
        final char symbol = line.charAt(col);
        switch (symbol) {
          case '#':
            objects.add(new MapObject(new Position(row, col), 1, 1, Type.WALL));
            break;
          case 'O':
            objects.add(new MapObject(new Position(row, col), 1, 1, Type.BOX));
            break;
          case '@':
            objects.add(new MapObject(new Position(row, col), 1, 1, Type.ROBOT));
            break;
          default:
            break;
        }
      }
    }
    return new WarehouseMap(rows, cols, List.copyOf(objects));
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
    final List<MapObject> toMove = new ArrayList<>();
    final MapObject robot = getRobot();
    if (robot.attemptMove(direction, this, toMove)) {
      final List<MapObject> newMapObjects = new ArrayList<>(objects);
      toMove.forEach(newMapObjects::remove);
      toMove.forEach(mapObject -> newMapObjects.add(mapObject.move(direction)));
      return new WarehouseMap(rows, cols, newMapObjects);
    }
    return this;
  }

  public Optional<MapObject> getObject(Position position) {
    return objects.stream().filter(o -> o.contains(position)).findFirst();
  }

  public boolean isInBounds(Position position) {
    return position.row() > 0
        && position.row() < rows()
        && position.col() > 0
        && position.col() < cols();
  }

  public MapObject getRobot() {
    return objects.stream().filter(o -> o.type().equals(Type.ROBOT)).findFirst().orElseThrow();
  }

  public long boxGpsSum() {
    return objects.stream().filter(o -> o.type().equals(Type.BOX)).mapToLong(this::gps).sum();
  }

  public long gps(MapObject mapObject) {
    return mapObject.anchor().row() * 100L + mapObject.anchor().col();
  }

  public String toString() {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < rows(); row++) {
      for (int col = 0; col < cols(); col++) {
        final Position position = new Position(row, col);
        final Optional<MapObject> mapObject = getObject(position);
        if (mapObject.isPresent()) {
          builder.append(mapObject.get().toString());
        } else {
          builder.append(".");
        }
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
