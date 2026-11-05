package com.codenzasoft.advent2024.day6;

public class Visit extends GridEntry {

  private final Direction direction;

  public Visit(final Direction direction) {
    this.direction = direction;
  }

  @Override
  public boolean isObstacle() {
    return false;
  }

  public Direction getDirection() {
    return direction;
  }
}
