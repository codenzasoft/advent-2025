package com.codenzasoft.advent2024.day12;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;

public class Day12 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-12.txt");
    System.out.println("The total price for part 1 is: " + new Day12().computeTotalPrice(lines));
  }

  public long computeTotalPrice(List<String> input) {
    final GardenMap map = GardenMap.parse(input);
    final List<Region> regions = map.buildRegions();
    long total = 0;
    for (final Region region : regions) {
      total += (long) region.computePerimeter(map) * region.getArea();
    }
    return total;
  }
}
