package com.codenzasoft.advent2025.day10;

import java.util.List;

public record Matrix(List<Vector> rows) {

  public Vector get(int row) {
    return rows().get(row);
  }

  public int getColumnCount() {
    return rows().get(0).values().size();
  }

  public int getRowCount() {
    return rows().size();
  }
}
