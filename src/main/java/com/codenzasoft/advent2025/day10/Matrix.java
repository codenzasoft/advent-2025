package com.codenzasoft.advent2025.day10;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

  public Matrix removeColumns(final List<Integer> columnIndicies) {
    final List<Vector> newRows = new ArrayList<>();
    for (final Vector row : rows()) {
      newRows.add(row.removeIndicies(columnIndicies));
    }
    return new Matrix(newRows);
  }

  public List<Vector> getRowsWithNonZeroColumn(final int column) {
    final List<Vector> result = new ArrayList<>();
    for (Vector row : rows()) {
      if (row.getValue(column) != 0) {
        result.add(row);
      }
    }
    return result;
  }

  public Matrix removeRow(final int row) {
    final List<Vector> newRows = new ArrayList<>(rows());
    newRows.remove(row);
    return new Matrix(newRows);
  }

  public Matrix removeRows(final List<Integer> rowIndicies) {
    final List<Vector> newRows = new ArrayList<>();
    for (int index = 0; index < rowIndicies.size(); index++) {
      if (!rowIndicies.contains(index)) {
        newRows.add(rows().get(index));
      }
    }
    return new Matrix(newRows);
  }

  public Matrix reorderColumns(final List<Integer> columnIndicies) {
    final List<Vector> newRows = new ArrayList<>();
    for (final Vector row : rows()) {
      newRows.add(row.reorder(columnIndicies));
    }
    return new Matrix(newRows);
  }

  public Vector coefficientsWith(final int value) {
    return Vector.withAll(getRowCount(), value);
  }

  public Vector coefficientsFrom(final Map<Vector, Long> occurrences) {
    final List<Integer> coefficients = new ArrayList<>();
    for (final Vector row : rows()) {
      coefficients.add(occurrences.getOrDefault(row, 0L).intValue());
    }
    return new Vector(coefficients);
  }

  public Vector getSum(final Vector coefficients) {
    Vector sum = Vector.withAll(getColumnCount(), 0);
    for (int r = 0; r < rows().size(); r++) {
      sum = sum.add(getRow(r).multiply(coefficients.getValue(r)));
    }
    return sum;
  }
}
