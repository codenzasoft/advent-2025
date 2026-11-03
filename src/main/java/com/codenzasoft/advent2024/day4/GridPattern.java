package com.codenzasoft.advent2024.day4;

import java.util.List;

/**
 * A grid of letters to search for in a {@link LetterGrid}.
 * Any letters with a dot (.) do not have to match.
 *
 * @param pattern
 */
public record GridPattern(char[][] pattern) {

  public static final char WILDCARD = '.';

  public static GridPattern parse(List<String> rows) {
    final char[][] pattern = new char[rows.size()][];
    for (int i = 0; i < rows.size(); i++) {
      pattern[i] = rows.get(i).toCharArray();
    }
    return new GridPattern(pattern);
  }

  public int rows() {
    return pattern.length;
  }

  public int cols() {
    return pattern[0].length;
  }

  public boolean matches(final int row, final int col, final LetterGrid grid) {
    for (int r = 0; r < rows(); r++) {
      for (int c = 0; c < cols(); c++) {
        char letter = pattern[r][c];
        if (letter != WILDCARD) {
          if (!grid.matches(r + row, c + col, letter)) {
            return false;
          }
        }
      }
    }
    return true;
  }
}
