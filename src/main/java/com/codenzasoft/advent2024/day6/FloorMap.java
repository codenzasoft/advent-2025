package com.codenzasoft.advent2024.day6;

import java.util.*;

/**
 * @param numRows The height of the map in coordinates
 * @param numCols The width of the map in coordinates
 * @param obstacles The (row, col) coordinates position of obstacles in the map
 * @param origin The starting position and direction
 */
public record FloorMap(int numRows, int numCols, Set<LabPosition> obstacles, Step origin) {

  public static FloorMap parse(final List<String> lines) {
    final int rowCount = lines.size();
    final int colCount = lines.getFirst().length();
    final Set<LabPosition> obstacles = new HashSet<>();
    Step origin = null;
    int row = 0;
    for (final String line : lines) {
      for (int col = 0; col < colCount; col++) {
        final char value = line.charAt(col);
        final LabPosition position = new LabPosition(row, col);
        switch (value) {
          case '.':
            break;
          case '#':
            obstacles.add(position);
            break;
          case '^':
            origin = new Step(position, Direction.UP);
            break;
          case '>':
            origin = new Step(position, Direction.RIGHT);
            break;
          case 'v':
            origin = new Step(position, Direction.DOWN);
            break;
          case '<':
            origin = new Step(position, Direction.LEFT);
            break;
        }
      }
      row++;
    }
    return new FloorMap(rowCount, colCount, Set.copyOf(obstacles), origin);
  }

  public Optional<Step> nextStep(final Step prevStep) {
    final LabPosition nextPosition = prevStep.position().move(prevStep.direction());
    if (isInBounds(nextPosition)) {
      if (obstacles.contains(nextPosition)) {
        return nextStep(new Step(prevStep.position(), prevStep.direction().turnRight()));
      }
      return Optional.of(new Step(nextPosition, prevStep.direction()));
    }
    return Optional.empty();
  }

  public boolean isInBounds(final LabPosition position) {
    return position.row() >= 0
        && position.row() < numRows
        && position.col() >= 0
        && position.col() < numCols;
  }

  public FloorMap addObstacle(LabPosition obstacle) {
    final Set<LabPosition> obstaclePositions = new HashSet<>(obstacles);
    obstaclePositions.add(obstacle);
    return new FloorMap(numRows, numCols, Set.copyOf(obstaclePositions), origin);
  }
}
