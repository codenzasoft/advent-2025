package com.codenzasoft.advent2025.day6;

import java.util.List;

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

  public long apply(final List<Long> numbers) {
    return numbers.stream().reduce(this::apply).orElse(0L);
  }

  public static Operation parse(String input) {
    return switch (input) {
      case "+" -> ADDITION;
      case "*" -> MULTIPLICATION;
      default -> throw new IllegalArgumentException();
    };
  }
}
