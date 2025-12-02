package com.codenzasoft.advent2025.day1;

/** The direction of a dial rotation - left or right. */
public enum Direction {
  LEFT,
  RIGHT,
  UNDEFINED;

  /**
   * Builds a direction by parsing a raw input string - like "R10" or "L5".
   *
   * @param rotation A rotation from the input file as a string
   * @return Corresponding direction
   */
  public static Direction parse(final String rotation) {
    if (rotation.startsWith("R")) {
      return RIGHT;
    } else if (rotation.startsWith("L")) {
      return LEFT;
    }
    return UNDEFINED;
  }
}
