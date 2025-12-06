package com.codenzasoft.advent2025.day2;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;

/**
 * Validates product ID ranges. Computes the sum of all invalid IDs in a list of product ID {@link
 * Range}s.
 */
public class RangeValidator {

  public static void main(final String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("input-day-2.txt");
    if (lines.size() > 1) {
      throw new IllegalArgumentException("Expecting single input line");
    }
    final String line = lines.get(0);
    final List<String> textRanges = List.of(line.split(","));
    final List<Range> ranges = textRanges.stream().map(Range::parse).toList();
    System.out.println(
        "The sum of invalid mirror product IDs is: "
            + new RangeValidator().computeHalfAndHalfTotal(ranges));
    System.out.println(
        "The sum of invalid subsequence product IDs is: "
            + new RangeValidator().computeRepeatedSequenceTotal(ranges));
  }

  /**
   * Returns the sum of all "half-and-half" product ids in the given {@link Range}s.
   *
   * @param ranges A list of {@link Range}s
   * @return The sum of all "half-and-half" product ids in the given {@link Range}s.
   */
  public long computeHalfAndHalfTotal(final List<Range> ranges) {
    long total = 0;
    for (final Range range : ranges) {
      total += range.findHalfAndHalfs().stream().mapToLong(Long::longValue).sum();
    }
    return total;
  }

  /**
   * Returns the sum of all product ids composed of repeated substrings in the given {@link Range}s.
   *
   * @param ranges A list of {@link Range}s
   * @return The sum of all product ids composed of repeated substrings in the given {@link Range}s.
   */
  public long computeRepeatedSequenceTotal(final List<Range> ranges) {
    long total = 0;
    for (final Range range : ranges) {
      total += range.findRepeatedSequenceNumbers().stream().mapToLong(Long::longValue).sum();
    }
    return total;
  }
}
