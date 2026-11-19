package com.codenzasoft.advent2024.day12;

import com.codenzasoft.advent2024.day6.Direction;
import com.codenzasoft.advent2024.day6.Position;
import java.util.*;

public record GardenMap(char[][] rows) {

  public static GardenMap parse(List<String> input) {
    char[][] rows = new char[input.size()][];
    for (int i = 0; i < rows.length; i++) {
      rows[i] = input.get(i).toCharArray();
    }
    return new GardenMap(rows);
  }

  public int getRowCount() {
    return rows.length;
  }

  public int getColumnCount() {
    return rows[0].length;
  }

  public char getPlantType(final Position position) {
    return rows[position.row()][position.col()];
  }

  public boolean containsPosition(final Position position) {
    return position.row() >= 0
        && position.row() < getRowCount()
        && position.col() >= 0
        && position.col() < getColumnCount();
  }

  public TreeSet<Position> getAllPositions() {
    final TreeSet<Position> positions = new TreeSet<>();
    for (int row = 0; row < getRowCount(); row++) {
      for (int col = 0; col < getColumnCount(); col++) {
        positions.add(new Position(row, col));
      }
    }
    return positions;
  }

  public List<Region> buildRegions() {
    final List<Region> regions = new ArrayList<>();
    final TreeSet<Position> positions = getAllPositions();
    while (!positions.isEmpty()) {
      final Position position = positions.removeFirst();
      final Region region = discoverRegion(position);
      regions.add(region);
      positions.removeAll(region.positions());
    }
    return regions;
  }

  private Region discoverRegion(final Position position) {
    final Set<Position> positions = new HashSet<>();
    positions.add(position);
    final char plantType = getPlantType(position);
    addConnectedPositions(plantType, position, positions);
    return new Region(plantType, positions);
  }

  private void addConnectedPositions(
      final char plantType, final Position position, final Set<Position> positions) {
    for (final Direction direction : Direction.values()) {
      final Position neighbour = position.move(direction);
      if (containsPosition(neighbour)
          && getPlantType(neighbour) == plantType
          && !positions.contains(neighbour)) {
        positions.add(neighbour);
        addConnectedPositions(plantType, neighbour, positions);
      }
    }
  }
}
