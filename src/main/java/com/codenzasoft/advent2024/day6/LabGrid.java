package com.codenzasoft.advent2024.day6;

import java.util.List;
import java.util.Optional;

public class LabGrid {

  private final GridEntry[][] rows;
  private LabPosition position;
  private Direction direction;

  public LabGrid(final GridEntry[][] rows, final LabPosition position, final Direction direction) {
    this.rows = rows;
    this.position = position;
    this.direction = direction;
  }

  public static LabGrid parse(final List<String> lines) {
    final int rowCount = lines.size();
    final int colCount = lines.getFirst().length();
    final GridEntry[][] rows = new GridEntry[rowCount][colCount];
    int row = 0;
    LabPosition position = null;
    Direction direction = null;
    for (final String line : lines) {
      for (int col = 0; col < colCount; col++) {
        final char value = line.charAt(col);
        switch (value) {
          case '.':
            rows[row][col] = GridEntry.EMPTY;
            break;
          case '#':
            rows[row][col] = GridEntry.OBSTACLE;
            break;
          case '^':
            position = new LabPosition(row, col);
            direction = Direction.UP;
            rows[row][col] = GridEntry.VISITED;
            break;
          case '>':
            position = new LabPosition(row, col);
            direction = Direction.RIGHT;
            rows[row][col] = GridEntry.VISITED;
            break;
          case 'v':
            position = new LabPosition(row, col);
            direction = Direction.DOWN;
            rows[row][col] = GridEntry.VISITED;
            break;
          case '<':
            position = new LabPosition(row, col);
            direction = Direction.LEFT;
            rows[row][col] = GridEntry.VISITED;
            break;
        }
      }
      row++;
    }
    return new LabGrid(rows, position, direction);
  }

  void visit(final LabPosition position) {
    this.rows[position.row()][position.col()] = GridEntry.VISITED;
  }

  public Optional<GridEntry> getEntry(final LabPosition position) {
    if (position.row() >= 0
        && position.row() < this.rows.length
        && position.col() >= 0
        && position.col() < this.rows[0].length) {
      return Optional.of(this.rows[position.row()][position.col()]);
    }
    return Optional.empty();
  }

  public boolean move() {
    final LabPosition nextPosition = position.move(direction);
    final Optional<GridEntry> nextEntry = getEntry(nextPosition);
    if (nextEntry.isEmpty()) {
      // off the grid
      return false;
    }
    switch (nextEntry.get()) {
      case VISITED:
      case EMPTY:
        visit(nextPosition);
        position = nextPosition;
        break;
      case OBSTACLE:
        direction = direction.turnRight();
        break;
    }
    return true;
  }

  public int countVisitedPositions() {
    int count = 0;
    for (GridEntry[] row : rows) {
      for (GridEntry entry : row) {
        if (entry == GridEntry.VISITED) {
          count++;
        }
      }
    }
    return count;
  }

  public String toString() {
    final StringBuilder builder = new StringBuilder();
    for (GridEntry[] row : rows) {
      for (GridEntry entry : row) {
        builder.append(entry.getDisplay());
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
