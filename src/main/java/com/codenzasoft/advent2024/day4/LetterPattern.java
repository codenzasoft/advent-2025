package com.codenzasoft.advent2024.day4;

public record LetterPattern(char[] letters, int rowInc, int colInc) {

  public static LetterPattern withLetters(String word, int rowInc, int colInc) {
    return new LetterPattern(word.toCharArray(), rowInc, colInc);
  }

  public char get(int offset) {
    return letters[offset];
  }

  public int length() {
    return letters.length;
  }

  public boolean matches(final int row, final int col, final LetterGrid grid) {
    int r = row;
    int c = col;
    for (int i = 0; i < length(); i++) {
      if (!grid.matches(r, c, get(i))) {
        return false;
      }
      r += rowInc();
      c += colInc();
    }
    return true;
  }
}
