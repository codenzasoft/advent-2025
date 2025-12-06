package com.codenzasoft.advent2025.day5;

import com.codenzasoft.advent2025.PuzzleHelper;
import com.codenzasoft.advent2025.day2.Range;
import java.util.ArrayList;
import java.util.List;

public class IdSearch {
  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("input-day-5.txt");
    final InputPair pair = parse(lines);
    final Ranges ranges = pair.ranges().compress();
    final List<Long> ids = pair.ids();

    System.out.println("The number of IDs in range (part 1): " + new IdSearch().part1(ranges, ids));
    System.out.println("The number of fresh IDs (part 2): " + new IdSearch().part2(ranges));
  }

  public static InputPair parse(List<String> lines) {
    final List<Range> ranges = new ArrayList<>();
    final List<Long> ids = new ArrayList<>();
    lines.forEach(
        line -> {
          if (line.contains("-")) {
            ranges.add(Range.parse(line));
          } else if (!line.trim().isEmpty()) {
            ids.add(Long.parseLong(line));
          }
        });
    return new InputPair(new Ranges(ranges), ids);
  }

  public int part1(final Ranges ranges, final List<Long> ids) {
    return ranges.getContainedIds(ids).size();
  }

  public long part2(final Ranges ranges) {
    return ranges.idSize();
  }
}
