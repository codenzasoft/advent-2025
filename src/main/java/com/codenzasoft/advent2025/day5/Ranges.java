package com.codenzasoft.advent2025.day5;

import com.codenzasoft.advent2025.day2.Range;
import java.util.*;

public record Ranges(List<Range> ranges) {

  /**
   * Sorts and merges overlapping ranges.
   *
   * @return A compatible {@link Ranges}
   */
  public Ranges compress() {
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
    return new Ranges(compressedRange);
  }

  /**
   * Returns any Ids in the provided list that are contained in this {@link Range}.
   *
   * @param ids A list of ids
   * @return A list of the provided Ids that are contained in this {@link Range}.
   */
  public List<Long> getContainedIds(final List<Long> ids) {
    List<Long> sortedIds = new ArrayList<>(ids);
    Collections.sort(sortedIds);

    final List<Long> selected = new ArrayList<>();
    int rangeIndex = 0;
    int idIndex = 0;
    while (idIndex < sortedIds.size() && rangeIndex < ranges.size()) {
      final Long id = sortedIds.get(idIndex);
      final Range range = ranges.get(rangeIndex);
      if (range.contains(id)) {
        selected.add(id);
        idIndex++;
      } else {
        if (range.isGreaterThanValue(id)) {
          idIndex++;
        } else {
          rangeIndex++;
        }
      }
    }
    return selected;
  }

  /**
   * Returns the number of Ids contained in this {@link Range}.
   *
   * @return The number of Ids contained in this {@link Range}.
   */
  public long idSize() {
    return ranges.stream().mapToLong(Range::size).sum();
  }
}
