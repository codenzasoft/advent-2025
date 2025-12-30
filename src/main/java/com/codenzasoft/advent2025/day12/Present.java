package com.codenzasoft.advent2025.day12;

import java.util.List;

public record Present(String id, char[][] shape) {

  public static Present parse(List<String> input) {
    final String id = input.get(0).substring(0, input.get(0).length() - 1);
    final char[][] shape = new char[input.size() - 1][];
    for (int i = 1; i < input.size(); i++) {
      shape[i - 1] = input.get(i).toCharArray();
    }
    return new Present(id, shape);
  }

  public boolean isEmpty(final int row, final int col) {
    return shape[row][col] == Grid.EMPTY;
  }

  public int getUnits() {
    int units = 0;
    for (int row = 0; row < getHeight(); row++) {
      for (int col = 0; col < getWidth(); col++) {
        if (!isEmpty(row, col)) {
          units++;
        }
      }
    }
    return units;
  }

  public int getWidth() {
    return shape[0].length;
  }

  public int getHeight() {
    return shape.length;
  }

  public Present rotate90() {
    final char[][] rotated = new char[getWidth()][getHeight()];
    for (int sourceRow = 0; sourceRow < getHeight(); sourceRow++) {
      int targetCol = rotated[0].length - sourceRow - 1;
      int targetRow = 0;
      for (int sourceCol = 0; sourceCol < getWidth(); sourceCol++) {
        rotated[targetRow][targetCol] = shape[sourceRow][sourceCol];
        targetRow++;
      }
    }
    return new Present(id(), rotated);
  }

  public String toString() {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < getHeight(); row++) {
      for (int col = 0; col < getWidth(); col++) {
        builder.append(shape[row][col]);
      }
      builder.append("\n");
    }
    return builder.toString();
  }

  public List<Present> getRotations() {
    final Present r90 = rotate90();
    final Present r180 = r90.rotate90();
    final Present r270 = r180.rotate90();
    return List.of(this, r90, r180, r270);
  }
}
