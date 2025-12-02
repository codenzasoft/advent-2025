package com.codenzasoft.advent2025.day2;

import com.codenzasoft.advent2025.day1.SafeCracker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Validates {@link ProductId} ranges. Computes the sum of all invalid IDs in a list of {@link
 * ProductId} {@link Range}s.
 */
public class RangeValidator {

  public static void main(final String[] args) throws IOException {
    try (final InputStream inputStream =
        SafeCracker.class.getResourceAsStream("/input-day-2.txt")) {
      if (inputStream == null) {
        throw new IllegalArgumentException("Input file not found");
      }
      final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      List<String> lines = reader.lines().toList();
      if (lines.size() > 1) {
        throw new IllegalArgumentException("Expecting single input line");
      }
      final String line = lines.get(0);
      final List<String> textRanges = List.of(line.split(","));
      final List<Range> ranges = textRanges.stream().map(Range::parse).toList();
      final long answer = new RangeValidator().computeMirrorTotal(ranges);
      System.out.println("The sum of invalid mirror product IDs is: " + answer);
    }
  }

  /**
   * Returns the sum of all invalid mirror {@link ProductId}s in the given {@link Range}s.
   *
   * @param ranges A list of {@link Range}s
   * @return The sum of all invalid mirror {@link ProductId}s in the given {@link Range}s.
   */
  public long computeMirrorTotal(final List<Range> ranges) {
    long total = 0;
    for (final Range range : ranges) {
      total += range.findMirrorIds().stream().mapToLong(ProductId::value).sum();
    }
    return total;
  }

  /**
   * Returns the sum of all invalid mirror {@link ProductId}s in the given {@link Range}s.
   *
   * @param ranges A list of {@link Range}s
   * @return The sum of all invalid mirror {@link ProductId}s in the given {@link Range}s.
   */
  public long computeRepeatedSequenceTotal(final List<Range> ranges) {
    long total = 0;
    for (final Range range : ranges) {
      total += range.findRepeatedSequenceIds().stream().mapToLong(ProductId::value).sum();
    }
    return total;
  }
}
