package com.codenzasoft.advent2025;

import com.codenzasoft.advent2025.day1.SafeCracker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/** Helper to read an input file of text, returning a list of strings - one for each line. */
public class PuzzleHelper {

  /**
   * Returns a list of {@link String}s, one per line of the text contained in the provided file.
   *
   * @param filename The name of the file to read, which must be in the <code>resources</code>
   *     directory.
   * @return A list of {@link String}s, one per line of the text contained in the provided file.
   */
  public static List<String> getInputLines(final String filename) {
    try (final InputStream inputStream = SafeCracker.class.getResourceAsStream("/" + filename)) {
      if (inputStream == null) {
        throw new IllegalArgumentException("Input file not found: " + filename);
      }
      final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      return reader.lines().toList();
    } catch (final IOException e) {
      System.err.println("Error reading input file: " + filename);
      System.exit(1);
      return null;
    }
  }

  /**
   * Returns whether the first and second half of the provided {@link String} are equal. For
   * example, <code>true</code> would be returned for "123123" or "1111".
   *
   * @param value A {@link String} to analyze.
   * @return whether the first and second half of the provided {@link String} are equal.
   */
  public static boolean isHalfAndHalf(final String value) {
    final int count = PuzzleHelper.getPeriodicSubstringCount(value);
    return count > 0 && count % 2 == 0;
  }

  /**
   * Returns whether the provided {@link String} is composed entirely of a repeated substring.
   *
   * @param value A {@link String} to analyze.
   * @return Whether the provided {@link String} is composed entirely of a repeated substring.
   */
  public static boolean isPeriodicSequence(final String value) {
    return PuzzleHelper.getPeriodicSubstringCount(value) >= 2;
  }

  /**
   * If the provided {@link String} is composed entirely of a repeated substring, the number of
   * repetitions of the substring is returned, otherwise 0.
   *
   * @param value The string to analyze for repeated substrings.
   * @return The number of repetitions a sub-string of a substring required to compose the provided
   *     value.
   */
  public static int getPeriodicSubstringCount(final String value) {
    int size = 1;
    while (size <= value.length() / 2) {
      final String sequence = value.substring(0, size);
      final int repeats = getSubstringCount(value, sequence);
      if (repeats > 0) {
        return repeats;
      }
      size++;
    }
    return 0;
  }

  /**
   * Returns the repetitions of the provided substring required to build the provided string, or 0
   * if not applicable.
   *
   * @param value The string to build
   * @param subString A substring of the value
   * @return The repetitions of the provided substring required to build the provided string, or 0
   *     if not applicable.
   */
  private static int getSubstringCount(final String value, final String subString) {
    if (value.length() % subString.length() == 0) {
      int offset = 0;
      final List<String> candidates = new ArrayList<>();
      while (offset < value.length()) {
        candidates.add(value.substring(offset, offset + subString.length()));
        offset += subString.length();
      }
      if (candidates.stream().allMatch(c -> c.equals(subString))) {
        return candidates.size();
      }
    }
    return 0;
  }
}
