package com.codenzasoft.advent2024.day6;

public record LabPosition(int row, int col) {

  public LabPosition move(Direction direction) {
    return new LabPosition(row + direction.rowInc(), col + direction.colInc());
  }
}
