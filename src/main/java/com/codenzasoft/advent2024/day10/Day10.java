package com.codenzasoft.advent2024.day10;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;

public class Day10 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-10.txt");
    System.out.println("The score for part one is: " + new Day10().solvePart1(lines));
  }

  public int solvePart1(List<String> lines) {
    final TopoMap map = TopoMap.parse(lines);
    return new TrailSearch().calculateScore(map);
  }
}
