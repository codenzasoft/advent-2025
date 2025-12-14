package com.codenzasoft.advent2025.day9;

public record Point(long x, long y) {

  public static Point parse(String input) {
    final String[] coords = input.split(",");
    return new Point(Long.parseLong(coords[0]), Long.parseLong(coords[1]));
  }
}
