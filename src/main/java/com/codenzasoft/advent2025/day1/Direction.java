package com.codenzasoft.advent2025.day1;

public enum Direction {
  LEFT,
  RIGHT,
  UNDEFINED;

  public static Direction getDirection(final String rotation) {
    if (rotation.startsWith("R")) {
      return RIGHT;
    } else if (rotation.startsWith("L")) {
      return LEFT;
    }
    return UNDEFINED;
  }
}
