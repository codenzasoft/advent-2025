package com.codenzasoft.advent2024.day8;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record CityMap(int width, int height, List<Antenna> antennas) {

  public static CityMap parse(List<String> input) {
    final int rows = input.size();
    final int cols = input.getFirst().length();
    final List<Antenna> antennas = new ArrayList<>();
    for (int i = 0; i < rows; i++) {
      final String row = input.get(i);
      for (int j = 0; j < cols; j++) {
        final String col = String.valueOf(row.charAt(j));
        if (!col.equals(".")) {
          antennas.add(new Antenna(col, new Position(i, j)));
        }
      }
    }
    return new CityMap(cols, rows, Collections.unmodifiableList(antennas));
  }

  public Map<String, List<Antenna>> getFrequencyGroups() {
    return antennas().stream().collect(Collectors.groupingBy(Antenna::frequency));
  }

  public boolean contains(final Position position) {
    return contains(position.x(), position.y());
  }

  public boolean contains(final int x, final int y) {
    return x >= 0 && x < width() && y >= 0 && y < height();
  }
}
