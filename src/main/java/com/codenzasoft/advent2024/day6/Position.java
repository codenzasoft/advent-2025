package com.codenzasoft.advent2024.day6;

public record Position(int row, int col) implements Comparable<Position> {

  public Position move(Direction direction) {
    return new Position(row + direction.rowInc(), col + direction.colInc());
  }

  public int compareTo(final Position o) {
    if (this.row == o.row) {
      return Integer.compare(this.col, o.col);
    }
    return Integer.compare(this.row, o.row);
  }
}
