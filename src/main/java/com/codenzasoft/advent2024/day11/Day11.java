package com.codenzasoft.advent2024.day11;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;

public class Day11 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-11.txt");
    System.out.println("The answer to part 1 is: " + new Day11().solveBlinks(lines.getFirst(), 25));
    System.out.println(
        "The answer to part 2 is: " + new Day11().splitAndSolve(lines.getFirst(), 75));
  }

  public long solveBlinks(final String input, final int blinks) {
    StoneList list = StoneList.parse(input);
    for (int i = 0; i < blinks; i++) {
      list = list.blink();
    }
    return list.stones().size();
  }

  public long splitAndSolve(final String input, final int blinks) {
    // break the list up
    final StoneList list = StoneList.parse(input);
    long result = 0;
    final StoneBlinkCache cache = new StoneBlinkCache();
    for (final Stone stone : list.stones()) {
      result += stone.countStones(blinks, cache);
    }
    return result;
  }
}
