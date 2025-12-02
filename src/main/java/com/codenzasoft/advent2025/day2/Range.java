package com.codenzasoft.advent2025.day2;

import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * A range of {@link ProductId}s.
 *
 * @param start The starting value of the range.
 * @param end The ending value of the range (inclusive).
 */
public record Range(long start, long end) {

  /**
   * Returns a list of invalid mirror {@link ProductId}s included in this {@link Range}.
   *
   * @return A list of invalid mirror {@link ProductId}s included in this {@link Range}.
   */
  public List<ProductId> findMirrorIds() {
    return stream().filter(ProductId::isMirrorSequence).toList();
  }

  /**
   * Returns a list of invalid repeated sub-sequence {@link ProductId}s included in this {@link Range}.
   *
   * @return A list of invalid repeated sub-sequence {@link ProductId}s included in this {@link Range}.
   */
  public List<ProductId> findRepeatedSequenceIds() {
    return stream().filter(ProductId::isRepeatedSequence).toList();
  }

  /**
   * Returns a {@link Stream} of {@link ProductId}s in this {@link Range}.
   *
   * @return A {@link Stream} of {@link ProductId}s in this {@link Range}.
   */
  public Stream<ProductId> stream() {
    return LongStream.range(start, end + 1L).mapToObj(ProductId::new);
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
