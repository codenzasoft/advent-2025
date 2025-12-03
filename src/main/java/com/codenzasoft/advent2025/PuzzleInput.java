package com.codenzasoft.advent2025;

import com.codenzasoft.advent2025.day1.SafeCracker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/** Helper to read an input file of text, returning a list of strings - one for each line. */
public class PuzzleInput {

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
}
