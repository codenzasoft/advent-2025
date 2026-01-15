package com.codenzasoft.advent2025.day10;

import java.util.ArrayList;
import java.util.List;

public record Matrix(List<Vector> rows) {

  public Vector getRow(final int row) {
    return rows().get(row);
  }

  public Vector getColumn(final int column) {
    final List<Integer> values = new ArrayList<>();
    for (int row = 0; row < rows().size(); row++) {
      values.add(getValue(row, column));
    }
    return new Vector(values);
  }

  public int getColumnCount() {
    return rows().get(0).values().size();
  }

  public int getRowCount() {
    return rows().size();
  }

  public int getValue(final int row, final int column) {
    return getRow(row).getValue(column);
  }

  public Matrix removeColumn(final int column) {
    final List<Vector> newRows = new ArrayList<>();
    for (final Vector row : rows()) {
      newRows.add(row.removeColumn(column));
    }
    return new Matrix(newRows);
  }

  public Matrix removeRow(final int row) {
    final List<Vector> newRows = new ArrayList<>(rows());
    newRows.remove(row);
    return new Matrix(newRows);
  }

  public Matrix reorderColumns(final List<Integer> columnIndicies) {
    final List<Vector> newRows = new ArrayList<>();
    for (final Vector row : rows()) {
      newRows.add(row.reorder(columnIndicies));
    }
    return new Matrix(newRows);
  }
}
