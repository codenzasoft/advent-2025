package com.codenzasoft.advent2024.day14;

public record Velocity(int x, int y) {

  public static Velocity parse(String line) {
    final String[] split = line.substring(2).split(",");
    return new Velocity(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
  }
}
