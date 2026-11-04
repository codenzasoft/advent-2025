package com.codenzasoft.advent2024.day6;

public enum GridEntry {
  EMPTY('.'),
  OBSTACLE('#'),
  VISITED('X');

  private char display;

  GridEntry(char display) {
    this.display = display;
  }

  public char getDisplay() {
    return display;
  }
}
