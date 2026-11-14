package com.codenzasoft.advent2024.day7;

import com.codenzasoft.advent2025.PuzzleHelper;

import java.util.List;

public class Day7 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-7.txt");
    final List<Equation> equations = parseEquations(lines);
    System.out.println("The answer to part one is: " + new Day7().solvePartOne(equations));
  }

  public static List<Equation> parseEquations(final List<String> lines) {
    return lines.stream().map(Equation::parse).toList();
  }

  public long solvePartOne(List<Equation> equations) {
    long result = 0;
    for (final Equation equation : equations) {
      if (equation.isSolvable()) {
        result += equation.testValue();
      }
    }
    return result;
  }
}
