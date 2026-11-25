package com.codenzasoft.advent2024.day14;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Day14 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-14.txt");
    System.out.println(
        "The answer to part 1 is: " + new Day14().solvePart1(lines, new Grid(101, 103), 100));
    System.out.println(
        "The answer to part 2 is: " + new Day14().solvePart2(lines, new Grid(101, 103)));
  }

  public long solvePart1(List<String> lines, final Grid grid, final int n) {
    final Plotter plotter = Plotter.parse(lines, grid);
    final Plotter after = plotter.move(n);
    final Map<Quadrant, List<Quadrant>> groups =
        after.robots().stream()
            .map(robot -> grid.quadrantOf(robot.location()))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.groupingBy(quadrant -> quadrant));
    return groups.values().stream().mapToLong(List::size).reduce(1, (a, b) -> a * b);
  }

  public int solvePart2(List<String> lines, final Grid grid) {
    final Plotter plotter = Plotter.parse(lines, grid);
    final int moves = plotter.trySome(10000);
    return moves;
  }
}
