package com.codenzasoft.advent2024.day6;

import com.codenzasoft.advent2025.PuzzleHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class Day6 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-6.txt");
    final LabGrid grid = new Day6().traceRoute(lines);
    System.out.println("The sum of unique visited positions: " + grid.countVisitedPositions());
    System.out.println("The number of potential loops: " + grid.getLoopCounter());

    final Route route = new Day6().buildRoute(lines);
    System.out.println("The sum of unique locations: " + route.uniquePositions().size());
    final Set<LabPosition> cycles = new Day6().cycleSearch(lines);
    System.out.println("The number blocks that create cycles: " + cycles.size());
  }

  public LabGrid traceRoute(List<String> input) {
    final LabGrid grid = LabGrid.parse(input);
    while (grid.move()) {}
    System.out.println(grid);
    return grid;
  }

  public Route buildRoute(List<String> lines) {
    final FloorMap map = FloorMap.parse(lines);
    return new RouteBuilder(map).build();
  }

  public Set<LabPosition> cycleSearch(List<String> lines) {
    final FloorMap map = FloorMap.parse(lines);
    final Route safeRoute = new RouteBuilder(map).build();
    Set<LabPosition> cycles = new HashSet<>();
    for (final Step step : safeRoute.getSteps()) {
      final LabPosition block = step.position().move(step.direction());
      if (map.isInBounds(block) && !map.obstacles().contains(block)) {
        final FloorMap trialMap = map.addObstacle(block);
        final Route alternateRoute = new RouteBuilder(trialMap).build();
        if (alternateRoute.isCycle()) {
          cycles.add(block);
        }
      }
    }
    return cycles;
  }
}
