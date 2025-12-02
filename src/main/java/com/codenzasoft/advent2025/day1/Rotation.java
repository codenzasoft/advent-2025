package com.codenzasoft.advent2025.day1;

public record Rotation(Direction direction, int distance) {

  public static Rotation parse(final String input) {
    final Direction direction = Direction.getDirection(input);
    final int distance = Integer.parseInt(input.substring(1));
    return new Rotation(direction, distance);
  }
}
