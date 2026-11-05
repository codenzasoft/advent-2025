package com.codenzasoft.advent2024.day6;

public enum GridContent {
  EMPTY('.'),
  OBSTACLE('#'),
  VISITED('X');

  private char display;

  GridContent(char display) {
    this.display = display;
  }

  public char getDisplay() {
    return display;
  }
}
