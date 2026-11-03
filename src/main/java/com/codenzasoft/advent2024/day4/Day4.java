package com.codenzasoft.advent2024.day4;

import com.codenzasoft.advent2025.PuzzleHelper;

import java.util.List;

public class Day4 {

  public static final String XMAS = "XMAS";

  public static final List<LetterPattern> XMAS_PATTERNS = List.of(
      LetterPattern.withLetters(XMAS, 0, 1),
      LetterPattern.withLetters(XMAS, 1, 1),
      LetterPattern.withLetters(XMAS, 1, 0),
      LetterPattern.withLetters(XMAS, 1, -1),
      LetterPattern.withLetters(XMAS, 0, -1),
      LetterPattern.withLetters(XMAS, -1, -1),
      LetterPattern.withLetters(XMAS, -1, 0),
      LetterPattern.withLetters(XMAS, -1, 1)
      );

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-4.txt");
    System.out.println("The number of XMAS matches is: " + new Day4().search(lines));
  }

  public int search(List<String> lines) {
    final LetterGrid grid = LetterGrid.parse(lines);
    int matches = 0;
    for (int row = 0; row < grid.rows(); row++) {
      for (int col = 0; col < grid.cols(); col++) {
        for (LetterPattern pattern : XMAS_PATTERNS) {
          if (grid.matches(row, col, pattern)) {
            matches++;
          }
        }
      }
    }
    return matches;
  }
}
