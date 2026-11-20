package com.codenzasoft.advent2024.day12;

import com.codenzasoft.advent2024.day6.Direction;
import com.codenzasoft.advent2024.day6.Position;
import java.util.Optional;

/**
 * A side is created from a collection of adjacent edges in the same row or column and on the same
 * side (direction).
 *
 * @param from
 * @param to
 * @param direction
 */
public record Side(Position from, Position to, Direction direction) {

  public Optional<Side> append(final Edge edge) {
    if (edge.direction() == direction()) {
      final Position position = edge.position();
      if (isHorizontal()) {
        if (to.col() + 1 == position.col()) {
          return Optional.of(new Side(from, position, direction()));
        }
      } else {
        // vertical
        if (to.row() + 1 == position.row()) {
          return Optional.of(new Side(from, position, direction()));
        }
      }
    }
    return Optional.empty();
  }

  public boolean isHorizontal() {
    return direction() == Direction.UP || direction() == Direction.DOWN;
  }

  public boolean isVertical() {
    return direction() == Direction.LEFT || direction() == Direction.RIGHT;
  }
}
