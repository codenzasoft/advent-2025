package com.codenzasoft.advent2024.day14;

public record Robot(Location location, Velocity velocity) {

  public static Robot parse(String input) {
    final String[] locationAndVelocity = input.split(" ");
    return new Robot(
        Location.parse(locationAndVelocity[0]), Velocity.parse(locationAndVelocity[1]));
  }

  public Robot move(Grid grid) {
    return new Robot(location.move(velocity, grid), velocity);
  }
}
