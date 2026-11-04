package com.codenzasoft.advent2024.day3;

public record Multiplication(long x, long y) {

  public long value() {
    return x * y;
  }
}
