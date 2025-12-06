package com.codenzasoft.advent2025.day2;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A range of product IDs (longs).
 *
 * @param start The starting value of the range.
 * @param end The ending value of the range (inclusive).
 */
public record Range(long start, long end) {

  /**
   * Returns a list of product ids that are half-and-half. That is, where the first and second half
   * of the ID are the same number. For example, "123123" or "1111".
   *
   * @return A list of product ids that are half-and-half.
   */
  public List<Long> findHalfAndHalfIds() {
    return stream().filter(l -> PuzzleHelper.isHalfAndHalf(Long.toString(l))).boxed().toList();
  }

  /**
   * Returns a list of product ids composed of a repeated sub-sequence included in this {@link
   * Range}.
   *
   * @return A list of product ids composed of a repeated sub-sequence included in this {@link
   *     Range}.
   */
  public List<Long> findRepeatedSequenceIds() {
    return stream()
        .filter(l -> PuzzleHelper.getPeriodicSubstringCount(Long.toString(l)) > 1)
        .boxed()
        .toList();
  }

  /**
   * Returns a {@link Stream} of product ids in this {@link Range}.
   *
   * @return A {@link Stream} of product ids in this {@link Range}.
   */
  public LongStream stream() {
    return LongStream.range(start, end + 1L);
  }

  /**
   * Builds a {@link Range} by parsing a raw input string like "446443-446449".
   *
   * @param range A range as a raw input string
   * @return Corresponding {@link Range}
   */
  public static Range parse(final String range) {
    final String[] offsets = range.split("-");
    return new Range(Long.parseLong(offsets[0]), Long.parseLong(offsets[1]));
  }
}
