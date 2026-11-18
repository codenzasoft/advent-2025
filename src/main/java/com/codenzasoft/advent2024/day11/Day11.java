package com.codenzasoft.advent2024.day11;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;

public class Day11 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-11.txt");
    System.out.println("The answer to part 1 is: " + new Day11().solveBlinks(lines.getFirst(), 25));
    System.out.println("The answer to part 2 is: " + new Day11().solvePart2(lines.getFirst()));
  }

  public long solveBlinks(final String input, final int blinks) {
    StoneList list = StoneList.parse(input);
    for (int i = 0; i < blinks; i++) {
      list = list.blink();
    }
    return list.stones().size();
  }

  public long solvePart2(final String input) {
    long prevResult = 183484;
    long total = 0;
    for (int i = 26; i < 32; i++) {
      final long result = splitAndSolve(input, i);
      System.out.println("Part 2 with " + i + ": " + result + " -> " + (result - prevResult));
      prevResult = result;
      total += result;
    }
    return total;
  }

  public long splitAndSolve(final String input, final int blinks) {
    // break the list up
    final StoneList list = StoneList.parse(input);
    long result = 0;
    for (final Stone stone : list.stones()) {
      StoneList subList = new StoneList(List.of(stone));
      for (int i = 0; i < blinks; i++) {
        subList = subList.blink();
      }
      result += subList.stones().size();
    }
    return result;
  }
}
