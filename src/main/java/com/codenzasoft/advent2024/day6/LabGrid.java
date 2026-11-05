package com.codenzasoft.advent2024.day6;

import com.codenzasoft.advent2024.day5.PagePath;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LabGrid {

  private final GridEntry[][] rows;
  private LabPosition position;
  private Direction direction;
  private final LoopDetector loopDetector = new LoopDetector();
  private final List<LabPosition> obstacles = new ArrayList<>();

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
            rows[row][col] = null;
            break;
          case '#':
            rows[row][col] = new Obstacle();
            break;
          case '^':
            position = new LabPosition(row, col);
            direction = Direction.UP;
            rows[row][col] = new Visit(direction);
            break;
          case '>':
            position = new LabPosition(row, col);
            direction = Direction.RIGHT;
            rows[row][col] = new Visit(direction);
            break;
          case 'v':
            position = new LabPosition(row, col);
            direction = Direction.DOWN;
            rows[row][col] = new Visit(direction);
            break;
          case '<':
            position = new LabPosition(row, col);
            direction = Direction.LEFT;
            rows[row][col] = new Visit(direction);
            break;
        }
      }
      row++;
    }
    return new LabGrid(rows, position, direction);
  }

  void visit(final LabPosition position, final Direction direction) {
    this.rows[position.row()][position.col()] = new Visit(direction);
  }

  public Optional<GridEntry> getContent(final LabPosition position) {
    if (isContained(position)) {
      return Optional.ofNullable(this.rows[position.row()][position.col()]);
    }
    return Optional.empty();
  }

  public boolean isContained(final LabPosition position) {
    return position.row() >= 0 && position.row() < this.rows.length && position.col() >= 0 && position.col() < this.rows[0].length;
  }

  public boolean move() {
    final LabPosition nextPosition = position.move(direction);
    if (!isContained(nextPosition)) {
      // gone off grid
      return false;
    }
    final Optional<GridEntry> nextEntry = getContent(nextPosition);

    if (nextEntry.isEmpty()) {
      // first visit
      visit(nextPosition, direction);
      position = nextPosition;
      final Optional<LabPosition> loopObstacle = loopDetector.isLoop2(position, direction, this);
      if (loopObstacle.map(this::isContained).orElse(false)) {
        obstacles.add(loopObstacle.get());
      }
    } else if (nextEntry.get().isObstacle()) {
      // hit an obstacle
      direction = direction.turnRight();
      loopDetector.addCorner(position);
    } else {
      // secondary visit
      visit(nextPosition, direction);
      position = nextPosition;
      final Optional<LabPosition> loopObstacle = loopDetector.isLoop2(position, direction, this);
      if (loopObstacle.map(this::isContained).orElse(false)) {
        obstacles.add(loopObstacle.get());
      }
    }
    return true;
  }

  public int getLoopCounter() {
    return obstacles.size();
  }

  public int countVisitedPositions() {
    int count = 0;
    for (int row = 0; row < this.rows.length; row++) {
      for (int col = 0; col < this.rows[0].length; col++) {
        final Optional<GridEntry> entry = getContent(new LabPosition(row,col));
        if (entry.isPresent()) {
          if (entry.get().isVisit()) {
            count++;
          }
        }
      }
    }
    return count;
  }

  public String toString() {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < this.rows.length; row++) {
      for (int col = 0; col < this.rows[0].length; col++) {
        final LabPosition position = new LabPosition(row,col);
        if (obstacles.contains(position)) {
          builder.append('O');
        } else {
          Optional<GridEntry> entry = getContent(position);
          if (entry.isPresent()) {
            builder.append(entry.get().getSymbol());
          } else {
            builder.append('.');
          }
        }
      }
      builder.append('\n');
    }
    return builder.toString();
  }
}
