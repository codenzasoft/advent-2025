package com.codenzasoft.advent2024.day6;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;

public class Day6 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-6.txt");
    System.out.println("The sum of unique visited positions: " + new Day6().traceRoute(lines));
  }

  public int traceRoute(List<String> input) {
    final LabGrid grid = LabGrid.parse(input);
    while (grid.move()) {}
    System.out.println(grid);
    return grid.countVisitedPositions();
  }
}
