package com.codenzasoft.advent2025.day6;

public enum Operation {
  ADDITION,
  MULTIPLICATION;

  public long apply(long a, long b) {
    return switch (this) {
      case ADDITION -> a + b;
      case MULTIPLICATION -> a * b;
      default -> throw new IllegalStateException();
    };
  }

  public static Operation parse(String input) {
    return switch (input) {
      case "+" -> ADDITION;
      case "*" -> MULTIPLICATION;
      default -> throw new IllegalArgumentException();
    };
  }
}
