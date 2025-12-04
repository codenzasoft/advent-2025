package com.codenzasoft.advent2025.day4;

/** Valid values contained by a {@link GridEntry}. */
public enum GridValue {
  EMPTY,
  PAPER_ROLL,
  REMOVED_ROLL;

  public static final char PAPER_ROLL_CHAR = '@';
  public static final char EMPTY_CHAR = '.';
  public static final char REMOVED_ROLL_CHAR = 'x';

  public static GridValue fromChar(char c) {
    return switch (c) {
      case PAPER_ROLL_CHAR -> PAPER_ROLL;
      case EMPTY_CHAR -> EMPTY;
      case REMOVED_ROLL_CHAR -> REMOVED_ROLL;
      default -> throw new IllegalArgumentException();
    };
  }
}
