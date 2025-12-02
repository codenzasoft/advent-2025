package com.codenzasoft.advent2025.day1;

/**
 * Models a circular safe dial with the numbers 0 through N in order, pointing at the specified
 * number (less than or equal to N).
 *
 * @param size The number of notches on the dial
 * @param location The current location of the dial
 */
public record Dial(int size, int location) {

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

  public Dial rotate(final Rotation rotation) {
    return rotate(rotation.direction(), rotation.distance());
  }

  public Dial left(int distance) {
    return rotate(Direction.LEFT, distance);
  }

  public Dial right(int distance) {
    return rotate(Direction.RIGHT, distance);
  }
}
