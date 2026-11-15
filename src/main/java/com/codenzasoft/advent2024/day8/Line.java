package com.codenzasoft.advent2024.day8;

public record Line(Position from, Position to) {

  public Position getDifference() {
    return to.subtract(from);
  }

  public Line getAntinodes() {
    final Position difference = getDifference();
    return new Line(from.subtract(difference), to.add(difference));
  }
}
