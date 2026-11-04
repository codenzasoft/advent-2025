package com.codenzasoft.advent2024.day5;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.ArrayList;
import java.util.List;

public class Day5 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-5.txt");
    final EdgesAndPaths data = EdgesAndPaths.parse(lines);
    System.out.println(
        "The sum of middle page correct paths is: " + new Day5().validMiddlePageSum(data));
    System.out.println(
        "The sum of middle page corrected paths is: " + new Day5().correctAndSumInvalidPaths(data));
  }

  public int validMiddlePageSum(EdgesAndPaths data) {
    final List<PagePath> correct = new ArrayList<>();
    for (final PagePath path : data.paths()) {
      //      final DirectedMultigraph<Integer, DefaultEdge> graph = path.buildGraph(data);
      if (path.isSorted(data)) {
        correct.add(path);
        System.out.println("Valid Path: " + path);
      }
    }
    return correct.stream().map(PagePath::middlePageNumber).mapToInt(Integer::intValue).sum();
  }

  public int correctAndSumInvalidPaths(EdgesAndPaths data) {
    final List<PagePath> corrected = new ArrayList<>();
    for (final PagePath path : data.paths()) {
      if (!path.isSorted(data)) {
        final PagePath fixed = new PagePath(path.sorted(data));
        corrected.add(fixed);
        System.out.println("Corrected Path: " + path);
      }
    }
    return corrected.stream().map(PagePath::middlePageNumber).mapToInt(Integer::intValue).sum();
  }
}
