package com.codenzasoft.advent2024.day2;

import com.codenzasoft.advent2025.PuzzleHelper;

import java.util.List;

public class Day2 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-2.txt");
    System.out.println("The total safe reports: " + new Day2().countSafeReports(lines));
    System.out.println("The total tolerable reports: " + new Day2().countTolerableReports(lines));
  }

  public int countSafeReports(List<String> lines) {
    int safe = 0;
    for (String line : lines) {
      final Report report = new Report(PuzzleHelper.parseAndMap(line, Long::parseLong));
      if (report.isSafe()) {
        safe++;
      }
    }
    return safe;
  }

  public int countTolerableReports(List<String> lines) {
    int tolerable = 0;
    for (String line : lines) {
      final Report report = new Report(PuzzleHelper.parseAndMap(line, Long::parseLong));
      if (report.isTolerable()) {
        tolerable++;
      }
    }
    return tolerable;
  }
}
