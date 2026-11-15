package com.codenzasoft.advent2024.day8;

public record Position(int x, int y) {

  public Position subtract(final Position other) {
    return new Position(x - other.x(), y - other.y());
  }

  public Position add(final Position other) {
    return new Position(x + other.x(), y + other.y());
  }

  public Position getLowestCommonFactor() {
    int x = this.x();
    int y = this.y();
    while (x % 2 == 0) {
      x /= 2;
    }
    while (y % 2 == 0) {
      y /= 2;
    }
    return new Position(x, y);
  }
}
