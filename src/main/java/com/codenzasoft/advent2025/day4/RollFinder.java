package com.codenzasoft.advent2025.day4;

import com.codenzasoft.advent2025.PuzzleInput;
import java.util.List;

public class RollFinder {

  public static void main(String[] args) {
    List<String> lines = PuzzleInput.getInputLines("input-day-4.txt");
    System.out.println(
        "The number of paper rolls for part one is: "
            + new RollFinder().partOne(new Grid.GridBuilder().withRows(lines).build()));
    System.out.println(
        "The number of paper rolls for part two is: "
            + new RollFinder().partTwo(new Grid.GridBuilder().withRows(lines).build()));
  }

  public int partOne(final Grid grid) {
    return grid.getEntriesWithFewerAdjacentValues(GridEntry.PAPER_ROLL, GridEntry.PAPER_ROLL, 4)
        .size();
  }

  public int partTwo(final Grid grid) {
    int found;
    int total = 0;
    Grid nextGrid = grid;
    do {
      List<GridEntry> removed =
          nextGrid.getEntriesWithFewerAdjacentValues(GridEntry.PAPER_ROLL, GridEntry.PAPER_ROLL, 4);
      found = removed.size();
      total += found;
      final Grid.GridBuilder builder = new Grid.GridBuilder().withRows(nextGrid.getRowStrings());
      removed.forEach(entry -> builder.removeEntry(entry.position()));
      nextGrid = builder.build();
    } while (found > 0);
    return total;
  }
}
