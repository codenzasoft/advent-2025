package com.codenzasoft.advent2025.day5;

import com.codenzasoft.advent2025.PuzzleInput;
import java.util.List;

public class IdSearch {
  public static void main(String[] args) {
    List<String> lines = PuzzleInput.getInputLines("input-day-5.txt");
    System.out.println("The number of IDs in range (part 1): " + new IdSearch().part1(lines));
  }

  public int part1(List<String> input) {
    final RangesIdsPair rawPair = RangesIdsPair.parse(input);
    // sort and merge overlapping ranges and ids
    final RangesIdsPair compressed = rawPair.compress();
    return compressed.idsInRange().size();
  }
}
