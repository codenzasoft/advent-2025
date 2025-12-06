package com.codenzasoft.advent2025.day2;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;
import java.util.Optional;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A numeric range, based on longs.
 *
 * @param start The starting value of the range (inclusive).
 * @param end The ending value of the range (inclusive).
 */
public record Range(long start, long end) {

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
   * Returns a {@link Stream} of longs in this {@link Range}.
   *
   * @return A {@link Stream} of longs in this {@link Range}.
   */
  public LongStream stream() {
    return LongStream.range(start, end + 1L);
  }

  /**
   * Returns whether the provided value is contained in this {@link Range}.
   *
   * @param value A value
   * @return Whether the provided value is contained in this {@link Range}.
   */
  public boolean contains(final long value) {
    return value >= start && value <= end;
  }

  /**
   * Returns whether this range begins after the provided value. That is, the starting value of this
   * range is greate than the provied value.
   *
   * @param value a value
   * @return Whether this range begins after the provided value.
   */
  public boolean isGreaterThanValue(final long value) {
    return value < start;
  }

  /**
   * Returns whether there is any overlap between this {@link Range} and the provided {@link Range}.
   *
   * @param other a {@link Range}
   * @return Whether there is any overlap between this {@link Range} and the provided {@link Range}.
   */
  public boolean overlaps(final Range other) {
    return contains(other.start()) || contains(other.end());
  }

  /**
   * Returns a {@link Range} equivalent to combining this {@link Range} with the provided {@link
   * Range} iff this {@link Range} overlaps with the provided {@link Range}, otherwise empty.
   *
   * @param other A {@link Range}
   * @return A combined {@link Range} or empty if the ranges do not overlap.
   */
  public Optional<Range> combine(final Range other) {
    if (overlaps(other)) {
      return Optional.of(
          new Range(Math.min(this.start(), other.start()), Math.max(this.end(), other.end())));
    }
    return Optional.empty();
  }

  /**
   * Returns the size of this {@link Range}. That is, the number of values in this {@link Range}.
   *
   * @return The size of this {@link Range}.
   */
  public long size() {
    return end() - start() + 1;
  }
}
