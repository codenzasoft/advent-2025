package com.codenzasoft.advent2024.day6;

public enum Direction {
  UP(-1, 0, '^'),
  RIGHT(0, 1, '>'),
  DOWN(1, 0, 'v'),
  LEFT(0, -1, '<');

  private int rowInc;
  private int colInc;
  private char symbol;

  Direction(int rowInc, int colInc, char symbol) {
    this.rowInc = rowInc;
    this.colInc = colInc;
    this.symbol = symbol;
  }

  public int rowInc() {
    return rowInc;
  }

  public int colInc() {
    return colInc;
  }

  public char symbol() {
    return symbol;
  }

  public Direction turnRight() {
    return switch (this) {
      case UP -> RIGHT;
      case DOWN -> LEFT;
      case LEFT -> UP;
      case RIGHT -> DOWN;
    };
  }
}
