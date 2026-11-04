package com.codenzasoft.advent2024.day1;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.*;

public class PuzzleOne {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-puzzle-1.txt");
    System.out.println("The total distance score: " + new PuzzleOne().solveDistance(lines));
    System.out.println("The total similarity score: " + new PuzzleOne().solveSimilarity(lines));
  }

  public long solveDistance(final List<String> lines) {
    final List<List<Long>> lists = parseLists(lines);
    final List<Long> left = lists.get(0);
    final List<Long> right = lists.get(1);
    Collections.sort(left);
    Collections.sort(right);
    return getDistance(left, right);
  }

  public long solveSimilarity(final List<String> lines) {
    final List<List<Long>> lists = parseLists(lines);
    final List<Long> left = lists.get(0);
    final List<Long> right = lists.get(1);
    return getSimilarity(left, right);
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

  private static Map<Long, Integer> buildOccurrences(final List<Long> list) {
    final List<Long> sortedList = new ArrayList<>(list);
    Collections.sort(sortedList);
    final Map<Long, Integer> occurrences = new HashMap<>();
    long prevValue = sortedList.get(0);
    int count = 1;
    for (int i = 1; i < sortedList.size(); i++) {
      final Long value = sortedList.get(i);
      if (value == prevValue) {
        count++;
      } else {
        occurrences.put(prevValue, count);
        count = 1;
      }
      prevValue = value;
    }
    occurrences.put(prevValue, count);
    return occurrences;
  }

  public static long getSimilarity(final List<Long> left, final List<Long> right) {
    final Map<Long, Integer> occurrences = buildOccurrences(right);
    return left.stream().map(num -> occurrences.getOrDefault(num, 0) * num).mapToLong(n -> n).sum();
  }
}
