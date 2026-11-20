package com.codenzasoft.advent2024.day12;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;

public class Day12 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-12.txt");
    System.out.println(
        "The total perimeter price for part 1 is: "
            + new Day12().computeTotalPerimeterPrice(lines));
    System.out.println(
        "The total side price for part 2 is: " + new Day12().computeTotalSidePrice(lines));
  }

  public long computeTotalPerimeterPrice(List<String> input) {
    final GardenMap map = GardenMap.parse(input);
    final List<Region> regions = map.buildRegions();
    long total = 0;
    for (final Region region : regions) {
      total += (long) region.computePerimeter(map) * region.getArea();
    }
    return total;
  }

  public long computeTotalSidePrice(List<String> input) {
    final GardenMap map = GardenMap.parse(input);
    final List<Region> regions = map.buildRegions();
    long total = 0;
    for (final Region region : regions) {
      final List<Side> sides = region.computeSides(map);
      total += (long) sides.size() * region.getArea();
    }
    return total;
  }
}
