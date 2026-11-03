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

  public static final List<GridPattern> MAS_PATTERNS = List.of(
    GridPattern.parse(List.of("M.M",".A.","S.S")),
      GridPattern.parse(List.of("M.S",".A.","M.S")),
      GridPattern.parse(List.of("S.S",".A.","M.M")),
      GridPattern.parse(List.of("S.M",".A.","S.M"))
  );

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-4.txt");
    System.out.println("The number of XMAS (word) matches is: " + new Day4().searchWord(lines));
    System.out.println("The number of MAS (grid) matches is: " + new Day4().searchGrid(lines));
  }

  public int searchWord(List<String> lines) {
    final LetterGrid grid = LetterGrid.parse(lines);
    int matches = 0;
    for (int row = 0; row < grid.rows(); row++) {
      for (int col = 0; col < grid.cols(); col++) {
        for (LetterPattern pattern : XMAS_PATTERNS) {
          if (pattern.matches(row, col, grid)) {
            matches++;
          }
        }
      }
    }
    return matches;
  }

  public int searchGrid(List<String> lines) {
    final LetterGrid grid = LetterGrid.parse(lines);
    int matches = 0;
    for (int row = 0; row < grid.rows(); row++) {
      for (int col = 0; col < grid.cols(); col++) {
        for (GridPattern pattern : MAS_PATTERNS) {
          if (pattern.matches(row, col, grid)) {
            matches++;
          }
        }
      }
    }
    return matches;
  }
}
