package com.codenzasoft.advent2025.day4;

public record GridEntry(char value, Position position) {

  public static final char PAPER_ROLL = '@';
  public static final char EMPTY = '.';
  public static final char REMOVED = 'x';
}
