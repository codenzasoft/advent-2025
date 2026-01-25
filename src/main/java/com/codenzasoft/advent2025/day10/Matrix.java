package com.codenzasoft.advent2025.day10;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.la4j.matrix.dense.Basic2DMatrix;
import org.la4j.vector.dense.BasicVector;

public record Matrix(List<Vector> rows) {

  public Vector getRow(final int row) {
    return rows().get(row);
  }

  public List<Vector> columns() {
    final List<Vector> columns = new ArrayList<>();
    for (int col = 0; col < getColumnCount(); col++) {
      columns.add(getColumn(col));
    }
    return columns;
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

  public List<Vector> getRowsWithHighOrderColumn(final int column) {
    return rows().stream().filter(v -> v.getHighOrderColumnIndex() == column).toList();
  }

  public Matrix removeRow(final int row) {
    final List<Vector> newRows = new ArrayList<>(rows());
    newRows.remove(row);
    return new Matrix(newRows);
  }

  public Matrix removeRows(final List<Integer> rowIndicies) {
    final List<Vector> newRows = new ArrayList<>();
    for (int index = 0; index < rows().size(); index++) {
      if (!rowIndicies.contains(index)) {
        newRows.add(rows().get(index));
      }
    }
    return new Matrix(newRows);
  }

  public Matrix removeDuplicateRows() {
    final List<Vector> distinct = rows().stream().distinct().toList();
    if (distinct.size() < getRowCount()) {
      System.out.println("Removing (" + (getRowCount() - distinct.size()) + ") duplicate rows");
      return new Matrix(distinct);
    }
    return this;
  }

  public Matrix reorderColumns(final List<Integer> columnIndicies) {
    final List<Vector> newRows = new ArrayList<>();
    for (final Vector row : rows()) {
      newRows.add(row.reorder(columnIndicies));
    }
    return new Matrix(newRows);
  }

  public Matrix reorderRows(final List<Integer> rowIndicies) {
    final List<Vector> newRows = new ArrayList<>();
    for (int row = 0; row < rowIndicies.size(); row++) {
      newRows.add(rows().get(rowIndicies.get(row)));
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

  public org.la4j.Vector getLa4jSum(final org.la4j.Vector coefficients) {
    org.la4j.Vector sum = new BasicVector(getColumnCount());
    for (int r = 0; r < rows().size(); r++) {
      sum = sum.add(getRow(r).toLa4j().multiply(coefficients.get(r)));
    }
    return sum;
  }

  public String toString() {
    final StringBuilder builder = new StringBuilder();
    builder.append("\n");
    for (int r = 0; r < rows().size(); r++) {
      builder.append(rows().get(r).toString());
      builder.append("\n");
    }
    return builder.toString();
  }

  public Matrix flip() {
    final List<Vector> newRows = new ArrayList<>();
    for (int c = 0; c < getColumnCount(); c++) {
      newRows.add(getColumn(c));
    }
    return new Matrix(newRows);
  }

  public org.la4j.Matrix toLa4j() {
    return new Basic2DMatrix(toDoubles());
  }

  public double[][] toDoubles() {
    final double[][] dRows = new double[getRowCount()][getColumnCount()];
    for (int r = 0; r < getRowCount(); r++) {
      for (int c = 0; c < getColumnCount(); c++) {
        dRows[r][c] = getValue(r, c);
      }
    }
    return dRows;
  }
}
