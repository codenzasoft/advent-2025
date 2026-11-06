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
    }
    ;
    return Optional.ofNullable(obstacle);
  }

  /**
   * @param position The current position
   * @param direction The current direction
   * @param grid The grid
   * @return a position that would create a loop if there were an obstacle there
   */
  public Optional<LabPosition> isLoop2(
      final LabPosition position, final Direction direction, final LabGrid grid) {
    // locate any obstacle that has been passed by before and would be hit if turned now
    Optional<LabPosition> obstacle = findObstacle(position, direction.turnRight(), grid);
    if (obstacle.isPresent()) {
      // check if an obstacle can be placed in next entry to force a turn
      final LabPosition obstaclePosition = position.move(direction);
      if (grid.isContained(obstaclePosition)) {
        // check if there is already an obstacle there
        Optional<GridEntry> whatsThere = grid.getContent(obstaclePosition);
        if (!whatsThere.map(GridEntry::isObstacle).orElse(false)) {
          return Optional.of(obstaclePosition);
        }
      }
    }
    return Optional.empty();
  }

  /**
   * @param position starting position
   * @param direction direction to travel
   * @param grid the grid
   * @return
   */
  protected Optional<LabPosition> findObstacle(
      final LabPosition position, final Direction direction, final LabGrid grid) {
    LabPosition prevPosition = position;
    LabPosition nextPosition = prevPosition.move(direction);
    while (grid.isContained(nextPosition)) {
      final Optional<GridEntry> maybe = grid.getContent(nextPosition);
      if (maybe.isPresent()) {
        final GridEntry entry = maybe.get();
        if (entry.isObstacle()) {
          final Optional<GridEntry> maybePrev = grid.getContent(prevPosition);
          if (maybePrev.isPresent() && maybePrev.get().isVisit()) {
            final Visit visit = (Visit) maybePrev.get();
            if (visit.getDirection() == direction
                || visit.getDirection() == direction.turnRight()) {
              return Optional.of(nextPosition);
            } else {
              return Optional.empty();
            }
          }
        }
      }
      prevPosition = nextPosition;
      nextPosition = nextPosition.move(direction);
    }
    return Optional.empty();
  }
}
