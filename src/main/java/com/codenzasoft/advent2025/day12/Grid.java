package com.codenzasoft.advent2025.day12;

import java.util.ArrayList;
import java.util.List;

public record Grid(char[][] grid) {

  public static final char BLOCK = '#';
  public static final char EMPTY = '.';

  public static Grid empty(int height, int width) {
    char[][] grid = new char[height][width];
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        grid[row][col] = EMPTY;
      }
    }
    return new Grid(grid);
  }

  public int getHeight() {
    return grid.length;
  }

  public int getWidth() {
    return grid[0].length;
  }

  public boolean isEmpty(final int row, final int col) {
    if (row < 0 || row >= getHeight() || col < 0 || col >= getWidth()) {
      return false;
    }
    return grid[row][col] == EMPTY;
  }

  public int getEmptyUnits() {
    int units = 0;
    for (int row = 0; row < getHeight(); row++) {
      for (int col = 0; col < getWidth(); col++) {
        if (isEmpty(row, col)) {
          units += 1;
        }
      }
    }
    return units;
  }

  public int getConnectedUnits(final int lowerBound) {
    int units = 0;
    for (int row = 0; row < getHeight(); row++) {
      for (int col = 0; col < getWidth(); col++) {
        if (isEmpty(row, col)) {
          final int emptyNeighbourCount = emptyNeighbourCount(row, col);
          if (emptyNeighbourCount >= lowerBound - 1) {
            units++;
          }
        }
      }
    }
    return units;
  }

  public int emptyNeighbourCount(final int row, final int col) {
    int empty = 0;
    for (int colOffset = 0; colOffset < 3; colOffset++) {
      for (int rowOffset = 0; rowOffset < 3; rowOffset++) {
        if (!(rowOffset == 1 && colOffset == 1)) {
          if (isEmpty(row - 1 + rowOffset, col - 1 + colOffset)) {
            empty += 1;
          }
        }
      }
    }
    return empty;
  }

  public List<Position> getEmptyPositions() {
    final List<Position> emptyPositions = new ArrayList<>();
    for (int row = 0; row < getHeight(); row++) {
      for (int col = 0; col < getWidth(); col++) {
        if (isEmpty(row, col)) {
          emptyPositions.add(new Position(row, col));
        }
      }
    }
    return emptyPositions;
  }

  public boolean canInsert(final Present present, final Position position) {
    return canInsert(present, position.row(), position.col());
  }

  public boolean canInsert(final Present present, final int row, final int col) {
    for (int presentRow = 0; presentRow < present.getHeight(); presentRow++) {
      for (int presentCol = 0; presentCol < present.getWidth(); presentCol++) {
        if (!present.isEmpty(presentRow, presentCol)) {
          int targetRow = row + presentRow;
          int targetCol = col + presentCol;
          if (targetRow >= getHeight() || targetCol >= getWidth()) {
            return false;
          }
          if (!isEmpty(targetRow, targetCol)) {
            return false;
          }
        }
      }
    }
    return true;
  }

  public Grid insert(final Present present, final Position position) {
    return insert(present, position.row(), position.col());
  }

  public Grid insert(final Present present, final int row, final int col) {
    final char[][] newGrid = new char[getHeight()][getWidth()];
    for (int rowIndex = 0; rowIndex < getHeight(); rowIndex++) {
      for (int colIndex = 0; colIndex < getWidth(); colIndex++) {
        newGrid[rowIndex][colIndex] = grid[rowIndex][colIndex];
      }
    }
    for (int presentRow = 0; presentRow < present.getHeight(); presentRow++) {
      for (int presentCol = 0; presentCol < present.getWidth(); presentCol++) {
        if (!present.isEmpty(presentRow, presentCol)) {
          int targetRow = row + presentRow;
          int targetCol = col + presentCol;
          newGrid[targetRow][targetCol] = BLOCK;
        }
      }
    }
    return new Grid(newGrid);
  }

  public String toString() {
    final StringBuilder builder = new StringBuilder();
    for (int row = 0; row < getHeight(); row++) {
      for (int col = 0; col < getWidth(); col++) {
        builder.append(grid[row][col]);
      }
      builder.append("\n");
    }
    return builder.toString();
  }
}
