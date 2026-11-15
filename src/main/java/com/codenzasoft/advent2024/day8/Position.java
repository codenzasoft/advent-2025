package com.codenzasoft.advent2024.day8;

public record Position(int x, int y) {

  public Position subtract(final Position other) {
    return new Position(x - other.x(), y - other.y());
  }

  public Position add(final Position other) {
    return new Position(x + other.x(), y + other.y());
  }
}
