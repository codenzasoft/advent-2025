package com.codenzasoft.advent2024.day6;

public record Position(int row, int col) {

  public Position move(Direction direction) {
    return new Position(row + direction.rowInc(), col + direction.colInc());
  }
}
