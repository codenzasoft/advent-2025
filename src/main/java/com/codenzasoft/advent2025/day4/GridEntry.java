package com.codenzasoft.advent2025.day4;

/**
 * An entry in a {@link Grid}. An entry can contain a roll of paper or be empty. Additionally, an
 * entry can be marked as removed - once containing a roll of paper that was subsequently removed.
 * Similar to empty, except that an empty location never contained a roll of paper.
 *
 * @param value One of PAPER_ROLL, EMPTY, or REMOVED
 * @param position The {@link Position} of this {@link GridEntry}
 */
public record GridEntry(char value, Position position) {

  public static final char PAPER_ROLL = '@';
  public static final char EMPTY = '.';
  public static final char REMOVED = 'x';
}
