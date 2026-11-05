package com.codenzasoft.advent2024.day6;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LoopDetector {

  final List<LabPosition> corners = new ArrayList<>();

  public void addCorner(final LabPosition position) {
    if (corners.size() == 3) {
      corners.removeFirst();
    }
    corners.add(position);
  }

  /**
   *
   * @param position The current position
   * @param direction The current direction
   * @return a position that would create a loop if there were an obstacle
   */
  public Optional<LabPosition> isLoop(final LabPosition position, final Direction direction) {
    if (corners.size() < 3) {
      return Optional.empty();
    }
    final LabPosition anchor = corners.getFirst();
    LabPosition obstacle = null;
    switch (direction) {
      case UP:
      case DOWN:
        if (position.row() == anchor.row()) {
          obstacle = position.move(direction);
        }
        break;
      case LEFT:
      case RIGHT:
        if (position.col() == anchor.col()) {
          obstacle = position.move(direction);
        }
        break;
    };
    return Optional.ofNullable(obstacle);
  }

  /**
   *
   * @param position The current position
   * @param direction The current direction
   * @param grid The grid
   *
   * @return a position that would create a loop if there were an obstacle there
   */
  public Optional<LabPosition> isLoop2(final LabPosition position, final Direction direction, final LabGrid grid) {
    if (corners.size() < 3) {
      return Optional.empty();
    }
    final LabPosition anchor = corners.getFirst();
    LabPosition obstacle = null;
    switch (direction) {
      case UP:
      case DOWN:
        if (position.row() == anchor.row()) {
          obstacle = position.move(direction);
        }
        break;
      case LEFT:
      case RIGHT:
        if (position.col() == anchor.col()) {
          obstacle = position.move(direction);
        }
        break;
    };
    return Optional.ofNullable(obstacle);
  }
}
