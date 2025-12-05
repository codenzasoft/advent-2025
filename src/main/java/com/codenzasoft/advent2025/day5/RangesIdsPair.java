package com.codenzasoft.advent2025.day5;

import java.util.*;
import java.util.stream.LongStream;

public record RangesIdsPair(List<Range> ranges, List<Long> ids) {

  public static RangesIdsPair parse(List<String> input) {
    List<Range> ranges = new ArrayList<>();
    List<Long> ids = new ArrayList<>();
    input.forEach(
        line -> {
          if (line.contains("-")) {
            ranges.add(Range.parse(line));
          } else if (!line.trim().isEmpty()) {
            ids.add(Long.parseLong(line));
          }
        });
    return new RangesIdsPair(ranges, ids);
  }

  /**
   * Sorts and merges overlapping ranges and ids.
   *
   * @return A compatible {@link RangesIdsPair}
   */
  public RangesIdsPair compress() {
    List<Range> sortedRanges = new ArrayList<>(ranges);
    sortedRanges.sort(
        new Comparator<Range>() {
          @Override
          public int compare(Range o1, Range o2) {
            return Long.compare(o1.start(), o2.start());
          }
        });
    List<Range> compressedRange = new ArrayList<>();
    Range currentRange = sortedRanges.get(0);
    for (int i = 1; i < sortedRanges.size(); i++) {
      final Range nextRange = sortedRanges.get(i);
      final Optional<Range> combined = currentRange.combine(nextRange);
      if (combined.isPresent()) {
        currentRange = combined.get();
      } else {
        compressedRange.add(currentRange);
        currentRange = nextRange;
      }
    }
    if (!compressedRange.contains(currentRange)) {
      compressedRange.add(currentRange);
    }

    List<Long> idsCopy = new ArrayList<>(ids);
    Collections.sort(idsCopy);
    return new RangesIdsPair(compressedRange, idsCopy);
  }

  public List<Long> findIdsInRange() {
    final List<Long> selected = new ArrayList<>();
    int rangeIndex = 0;
    int idIndex = 0;
    while (idIndex < ids.size() && rangeIndex < ranges.size()) {
      final Long id = ids.get(idIndex);
      final Range range = ranges.get(rangeIndex);
      if (range.contains(id)) {
        selected.add(id);
        idIndex++;
      } else {
        if (range.isGreaterThan(id)) {
          idIndex++;
        } else {
          rangeIndex++;
        }
      }
    }
    return selected;
  }

  public long idSize() {
    return ranges.stream().mapToLong(Range::size).sum();
  }
}
