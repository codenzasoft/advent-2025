package com.codenzasoft.advent2024.day15;

import com.codenzasoft.advent2024.day6.Direction;
import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.math3.util.Pair;

public class Day15 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-15.txt");
    System.out.println("The answer to part 1 is: " + new Day15().solvePart1(lines));
  }

  public static Pair<WarehouseMap, List<Direction>> parse(List<String> input) {
    final List<List<String>> segments = split(input);
    final WarehouseMap map = WarehouseMap.parse(segments.get(0));
    final List<Direction> directions = WarehouseMap.buildDirections(segments.get(1));
    return new Pair<>(map, directions);
  }

  public static List<List<String>> split(final List<String> input) {
    List<String> map = new ArrayList<>();
    List<String> directions = new ArrayList<>();
    int row = 0;
    String line = input.get(row);
    while (!line.trim().isEmpty()) {
      map.add(line);
      row++;
      line = input.get(row);
    }
    while (row < input.size()) {
      directions.add(input.get(row));
      row++;
    }
    return List.of(map, directions);
  }

  public long solvePart1(final List<String> input) {
    final Pair<WarehouseMap, List<Direction>> result = parse(input);
    WarehouseMap map = result.getFirst();
    final List<Direction> directions = result.getSecond();
    for (Direction direction : directions) {
      map = map.moveRobot(direction);
      //      System.out.println("Move: " + direction.symbol());
      //      System.out.println(map);
      //      System.out.println();
    }
    return map.boxGpsSum();
  }
}
