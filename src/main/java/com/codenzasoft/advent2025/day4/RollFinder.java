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

  /**
   * Performs a single pass of the provided {@link Grid} to determine how many rolls of paper can be
   * removed. A roll can be removed if it has fewer than 4 adjacent paper rolls.
   *
   * @param grid A {@link Grid} to examine
   * @return The number of paper rolls that can be removed on the first pass.
   */
  public int partOne(final Grid grid) {
    return grid.getEntriesWithFewerAdjacentValues(GridEntry.PAPER_ROLL, GridEntry.PAPER_ROLL, 4)
        .size();
  }

  /**
   * Performs passes over the provided {@link Grid} to determine how many rolls of paper can be
   * removed, by effectively removing accessible rolls on each pass, and then determining if any
   * more rolls can be removed.
   *
   * @param grid A {@link Grid} to examine.
   * @return The total number of paper rolls that can be removed from the {@link Grid}
   */
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
