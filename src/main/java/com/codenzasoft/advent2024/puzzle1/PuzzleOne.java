package com.codenzasoft.advent2024.puzzle1;

import com.codenzasoft.advent2025.PuzzleHelper;
import com.codenzasoft.advent2025.day1.SafeCracker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class PuzzleOne {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-puzzle-1.txt");
    System.out.println("The answer to puzzle 1 is: " + new PuzzleOne().solve(lines));
  }

  public long solve(final List<String> lines) {
    final List<List<Long>> lists = parseLists(lines);
    final List<Long> left = lists.get(0);
    final List<Long> right = lists.get(1);
    Collections.sort(left);
    Collections.sort(right);
    return getDistance(left, right);
  }

  public static List<List<Long>> parseLists(final List<String> lines) {
    final List<Long> left = new ArrayList<>();
    final List<Long> right = new ArrayList<>();
    for (String line : lines) {
      final String[] split = line.split("\\s+");
      if (split.length == 2) {
        left.add(Long.parseLong(split[0]));
        right.add(Long.parseLong(split[1]));
      }
    }
    return List.of(left, right);
  }

  public static long getDistance(final List<Long> left, final List<Long> right) {
    long distance = 0;
    for (int i = 0; i < left.size(); i++) {
      distance += Math.abs(left.get(i) - right.get(i));
    }
    return distance;
  }
}
