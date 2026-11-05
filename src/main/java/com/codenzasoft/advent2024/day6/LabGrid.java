package com.codenzasoft.advent2024.day6;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LabGrid {

  private final GridContent[][] rows;
  private LabPosition position;
  private Direction direction;
  private final LoopDetector loopDetector = new LoopDetector();
  private final List<LabPosition> obstacles = new ArrayList<>();

  public LabGrid(final GridContent[][] rows, final LabPosition position, final Direction direction) {
    this.rows = rows;
    this.position = position;
    this.direction = direction;
  }

  public static LabGrid parse(final List<String> lines) {
    final int rowCount = lines.size();
    final int colCount = lines.getFirst().length();
    final GridContent[][] rows = new GridContent[rowCount][colCount];
    int row = 0;
    LabPosition position = null;
    Direction direction = null;
    for (final String line : lines) {
      for (int col = 0; col < colCount; col++) {
        final char value = line.charAt(col);
        switch (value) {
          case '.':
            rows[row][col] = GridContent.EMPTY;
            break;
          case '#':
            rows[row][col] = GridContent.OBSTACLE;
            break;
          case '^':
            position = new LabPosition(row, col);
            direction = Direction.UP;
            rows[row][col] = GridContent.VISITED;
            break;
          case '>':
            position = new LabPosition(row, col);
            direction = Direction.RIGHT;
            rows[row][col] = GridContent.VISITED;
            break;
          case 'v':
            position = new LabPosition(row, col);
            direction = Direction.DOWN;
            rows[row][col] = GridContent.VISITED;
            break;
          case '<':
            position = new LabPosition(row, col);
            direction = Direction.LEFT;
            rows[row][col] = GridContent.VISITED;
            break;
        }
      }
      row++;
    }
    return new LabGrid(rows, position, direction);
  }

  void visit(final LabPosition position, final Direction direction) {
    this.rows[position.row()][position.col()] = GridContent.VISITED;
  }

  public Optional<GridContent> getContent(final LabPosition position) {
    if (isContained(position)) {
      return Optional.of(this.rows[position.row()][position.col()]);
    }
    return Optional.empty();
  }

  public boolean isContained(final LabPosition position) {
    return position.row() >= 0 && position.row() < this.rows.length && position.col() >= 0 && position.col() < this.rows[0].length;
  }

  public boolean move() {
    final LabPosition nextPosition = position.move(direction);
    final Optional<GridContent> nextContent = getContent(nextPosition);
    if (nextContent.isEmpty()) {
      // off the grid
      return false;
    }
    switch (nextContent.get()) {
      case VISITED:
      case EMPTY:
        visit(nextPosition, direction);
        position = nextPosition;
        final Optional<LabPosition> loopObstacle = loopDetector.isLoop(position, direction);
        if (loopObstacle.map(this::isContained).orElse(false)) {
          obstacles.add(loopObstacle.get());
        }
        break;
      case OBSTACLE:
        direction = direction.turnRight();
        loopDetector.addCorner(position);
        break;
    }
    return true;
  }

  public int getLoopCounter() {
    return obstacles.size();
  }

  public int countVisitedPositions() {
    int count = 0;
    for (GridContent[] row : rows) {
      for (GridContent entry : row) {
        if (entry == GridContent.VISITED) {
          count++;
        }
      }
    }
    return count;
  }

  public String toString() {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < this.rows.length; row++) {
      for (int col = 0; col < this.rows[0].length; col++) {
        GridContent entry = this.rows[row][col];
        if (obstacles.contains(new LabPosition(row, col))) {
          builder.append('O');
        } else {
          builder.append(entry.getDisplay());
        }
      }
      builder.append('\n');
    }
//    for (GridContent[] row : rows) {
//      for (GridContent entry : row) {
//        builder.append(entry.getDisplay());
//      }
//      builder.append("\n");
//    }
    return builder.toString();
  }
}
