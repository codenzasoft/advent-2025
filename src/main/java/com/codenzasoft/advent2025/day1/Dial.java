package com.codenzasoft.advent2025.day1;

/**
 * Models a circular safe dial with the numbers 0 through N in order, pointing at the specified
 * number (less than or equal to N).
 *
 * @param size The number of notches on the dial
 * @param location The current location of the dial
 */
public record Dial(int size, int location) {

  public Dial left(int distance) {
    return new Dial(size, (location - distance) % size);
  }

  public Dial right(int distance) {
    return new Dial(size, (location + distance) % size);
  }
}
