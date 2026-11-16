package com.codenzasoft.advent2024.day9;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;

public class Day9 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-9.txt");
    System.out.println("The checksum for part one is: " + new Day9().solvePart1(lines.getFirst()));
  }

  public long solvePart1(final String input) {
    final BlockMap map = BlockMap.parse(input);
    map.compress();
    return map.checksum();
  }
}
