package com.codenzasoft.advent2024.day15;

import com.codenzasoft.advent2024.day6.Direction;
import com.codenzasoft.advent2024.day6.Position;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public record MapObject(Position anchor, int width, int height, Type type) {

  public boolean contains(Position position) {
    return position.row() >= anchor.row()
        && position.row() < anchor.row() + height
        && position.col() >= anchor.col()
        && position.col() < anchor.col() + width;
  }

  public MapObject move(Direction direction) {
    if (!type.equals(Type.WALL)) {
      return new MapObject(anchor.move(direction), width, height, type);
    }
    return this;
  }

  public boolean attemptMove(Direction direction, WarehouseMap map, List<MapObject> objectsToMove) {
    if (type.equals(Type.WALL)) {
      return false;
    }
    final List<Position> targets = getAdjacentPositions(direction);
    final List<MapObject> obstacles =
        targets.stream()
            .map(map::getObject)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .distinct()
            .toList();
    for (MapObject obstacle : obstacles) {
      if (!obstacle.attemptMove(direction, map, objectsToMove)) {
        return false;
      }
    }
    objectsToMove.add(this);
    return true;
  }

  public List<Position> getAdjacentPositions(Direction direction) {
    List<Position> positions = null;
    switch (direction) {
      case UP:
        positions = getRow(0);
        break;
      case DOWN:
        positions = getRow(height - 1);
        break;
      case LEFT:
        positions = getCol(0);
        break;
      case RIGHT:
        positions = getCol(width - 1);
        break;
    }
    return positions.stream().map(position -> position.move(direction)).toList();
  }

  public List<Position> getRow(int row) {
    final List<Position> positions = new ArrayList<>();
    for (int col = anchor.col(); col < anchor.col() + width; col++) {
      positions.add(new Position(anchor.row() + row, col));
    }
    return positions;
  }

  public List<Position> getCol(int col) {
    final List<Position> positions = new ArrayList<>();
    for (int row = anchor.row(); row < anchor.row() + height; row++) {
      positions.add(new Position(row, anchor.col() + col));
    }
    return positions;
  }

  public long gps() {
    return anchor().row() * 100L + anchor().col();
  }

  public String toString(Position position) {
    switch (type) {
      case WALL:
        return "#";
      case ROBOT:
        return "@";
      case BOX:
        if (width == height) {
          return "O";
        }
        if (position.equals(anchor)) {
          return "[";
        }
        return "]";
    }
    ;
    return "";
  }
}
