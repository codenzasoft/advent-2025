package com.codenzasoft.advent2025.day1;

/**
 * A circular safe dial with the numbers 0 through N in order, set to the specified location (less
 * than or equal to N).
 *
 * @param size The number of locations on the dial
 * @param location The current location of the dial
 */
public record Dial(int size, int location) {

  /**
   * Returns the resulting {@link Dial} state after executing a rotation with the specified
   * direction and distance.
   *
   * @param direction A {@link Direction}
   * @param distance A distance
   * @return The resulting {@link Dial} state
   */
  public Dial rotate(final Direction direction, final int distance) {
    switch (direction) {
      case LEFT -> {
        return new Dial(size, (location - distance) % size);
      }
      case RIGHT -> {
        return new Dial(size, (location + distance) % size);
      }
      default -> {
        return this;
      }
    }
  }

  /**
   * Returns the resulting {@link Dial} state after executing the specified {@link Rotation}. on
   * this {@link Dial}.
   *
   * @param rotation A {@link Rotation}
   * @return The resulting {@link Dial} state
   */
  public Dial rotate(final Rotation rotation) {
    return rotate(rotation.direction(), rotation.distance());
  }
}
