package com.codenzasoft.advent2025.day8;

public record Point3D(long x, long y, long z) {

  public static Point3D parse(final String input) {
    String[] split = input.split(",");
    return new Point3D(Long.parseLong(split[0]), Long.parseLong(split[1]), Long.parseLong(split[2]));
  }

  public double distance(final Point3D point) {
    return Math.sqrt(
        sqrdDifference(x(), point.x()) +
            sqrdDifference(y(), point.y()) + sqrdDifference(z(), point.z()));
  }

  private double sqrdDifference(final long v1, final long v2) {
    return (v1 - v2) * (v1 - v2);
  }
}
