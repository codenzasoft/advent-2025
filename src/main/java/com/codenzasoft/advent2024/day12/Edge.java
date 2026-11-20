package com.codenzasoft.advent2024.day12;

import com.codenzasoft.advent2024.day6.Direction;
import com.codenzasoft.advent2024.day6.Position;
import java.util.Comparator;

/**
 * An edge has a position and direction. The direction identifies the side that the edge is on -
 * left, right, top (up), or bottom (down).
 *
 * @param position The coordinate that contains an edge.
 * @param direction The side the edge is on.
 */
public record Edge(Position position, Direction direction) {

  public static Comparator<Edge> ROW_COMPARATOR =
      Comparator.comparing(edge -> edge.position().row());
  public static Comparator<Edge> COL_COMPARATOR =
      Comparator.comparing(edge -> edge.position().col());

  public boolean isHorizontal() {
    return direction() == Direction.UP || direction() == Direction.DOWN;
  }

  public boolean isVertical() {
    return direction() == Direction.LEFT || direction() == Direction.RIGHT;
  }
}
