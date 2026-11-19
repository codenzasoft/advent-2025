package com.codenzasoft.advent2024.day12;

import com.codenzasoft.advent2024.day6.Direction;
import com.codenzasoft.advent2024.day6.Position;
import java.util.Set;

public record Region(char plantType, Set<Position> positions) {

  public int computePerimeter(final GardenMap map) {
    int perimeter = 0;
    for (final Position position : positions) {
      for (final Direction direction : Direction.values()) {
        final Position neighbour = position.move(direction);
        if (map.containsPosition(neighbour)) {
          if (!contains(neighbour)) {
            perimeter++;
          }
        } else {
          perimeter++;
        }
      }
    }
    return perimeter;
  }

  public int getArea() {
    return positions().size();
  }

  public boolean contains(final Position position) {
    return positions.contains(position);
  }
}
