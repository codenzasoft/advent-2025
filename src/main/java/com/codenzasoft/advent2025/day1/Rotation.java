package com.codenzasoft.advent2025.day1;

/**
 * A dial rotation of a specific direction and distance.
 *
 * @param direction
 * @param distance
 */
public record Rotation(Direction direction, int distance) {

  /**
   * Builds a rotation by parsing a raw input string - like "R10" or "L5".
   *
   * @param rotation A rotation from the input file as a string
   * @return A corresponding {@link Rotation}
   */
  public static Rotation parse(final String rotation) {
    final Direction direction = Direction.parse(rotation);
    final int distance = Integer.parseInt(rotation.substring(1));
    return new Rotation(direction, distance);
  }
}
