package com.codenzasoft.advent2024.day7;

public enum Operator {
  ADDITION('+'),
  MULTIPLICATION('*'),
  CONCATENATION('|');

  private final char display;

  Operator(char display) {
    this.display = display;
  }

  public char getDisplay() {
    return display;
  }

  public long apply(long a, long b) {
    return switch (this) {
      case ADDITION -> a + b;
      case MULTIPLICATION -> a * b;
      case CONCATENATION -> Long.parseLong(Long.toString(a) + Long.toString(b));
    };
  }
}
