package com.codenzasoft.advent2024.day4;

import java.util.List;

/**
 * A matrix of chars indexed by row and column.
 *
 * @param grid
 */
public record LetterGrid(char[][] grid) {

  public static LetterGrid parse(List<String> rows) {
    final char[][] grid = new char[rows.size()][];
    for (int i = 0; i < rows.size(); i++) {
      grid[i] = rows.get(i).toCharArray();
    }
    return new LetterGrid(grid);
  }

  public int rows() {
    return grid.length;
  }

  public int cols() {
    return grid[0].length;
  }

  public boolean matches(final int row, final int col, final LetterPattern pattern) {
    int r = row;
    int c = col;
    for (int i = 0; i < pattern.length(); i++) {
      if (!matches(r, c, pattern.get(i))) {
        return false;
      }
      r += pattern.rowInc();
      c += pattern.colInc();
    }
    return true;
  }

  public boolean matches(final int row, final int col, char c) {
    if (row < 0 || row >= grid.length || col < 0 || col >= grid[0].length) {
      return false;
    }
    return grid[row][col] == c;
  }
}
