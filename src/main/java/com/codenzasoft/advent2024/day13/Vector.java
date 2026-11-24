package com.codenzasoft.advent2024.day13;

public record Vector(int x, int y) {

  public static Vector parseButton(final String input) {
    final String[] split = input.split(":");
    final String[] xAndY = split[1].trim().split(",");
    final String xPart = xAndY[0].trim().substring(1);
    final String yPart = xAndY[1].trim().substring(1);
    return new Vector(Integer.parseInt(xPart), Integer.parseInt(yPart));
  }

  public static Vector parsePrize(final String input) {
    final String[] split = input.split(":");
    final String[] xAndY = split[1].trim().split(",");
    final String[] xPart = xAndY[0].trim().split("=");
    final String[] yPart = xAndY[1].trim().split("=");
    return new Vector(Integer.parseInt(xPart[1]), Integer.parseInt(yPart[1]));
  }
}
