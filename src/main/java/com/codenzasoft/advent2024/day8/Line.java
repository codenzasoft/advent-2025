package com.codenzasoft.advent2024.day8;

import java.util.ArrayList;
import java.util.List;

public record Line(Position from, Position to) {

  public Position getDifference() {
    return to.subtract(from);
  }

  public Line getAntinodes() {
    final Position difference = getDifference();
    return new Line(from.subtract(difference), to.add(difference));
  }

  public List<Position> getExtendedPointsWithin(final CityMap map) {
    final Position increment = getDifference();
    //    final Position increment = difference.getLowestCommonFactor();
    final List<Position> result = new ArrayList<>();
    Position position = from();
    while (map.contains(position)) {
      result.add(position);
      position = position.add(increment);
    }
    position = from().subtract(increment);
    while (map.contains(position)) {
      result.add(position);
      position = position.subtract(increment);
    }
    return result;
  }
}
