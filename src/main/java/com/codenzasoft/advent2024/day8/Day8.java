package com.codenzasoft.advent2024.day8;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.math3.util.Combinations;

public class Day8 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-8.txt");
    System.out.println("The answer to part 1 is: " + new Day8().solvePart1(lines));
  }

  public long solvePart1(final List<String> input) {
    final CityMap map = CityMap.parse(input);
    final List<Position> antiNodes = new ArrayList<>();
    final Map<String, List<Antenna>> groups = map.getFrequencyGroups();
    for (Map.Entry<String, List<Antenna>> group : groups.entrySet()) {
      final String frequency = group.getKey();
      final List<Antenna> antennas = group.getValue();
      final Iterator<int[]> combinations = new Combinations(antennas.size(), 2).iterator();
      while (combinations.hasNext()) {
        final int[] combination = combinations.next();
        final Line line =
            new Line(
                antennas.get(combination[0]).position(), antennas.get(combination[1]).position());
        final Line antinodeLine = line.getAntinodes();
        antiNodes.add(antinodeLine.from());
        antiNodes.add(antinodeLine.to());
      }
    }
    return antiNodes.stream().filter(map::contains).distinct().count();
  }
}
