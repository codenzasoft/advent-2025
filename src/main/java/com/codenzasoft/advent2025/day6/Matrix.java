package com.codenzasoft.advent2025.day6;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public record Matrix(List<List<Long>> rows) {

  public static class MatrixRowBuilder {
    List<List<Long>> rows = new ArrayList<>();

    public MatrixRowBuilder addLongRow(final List<Long> row) {
      if (!rows.isEmpty()) {
        if (rows.get(0).size() != row.size()) {
          throw new IllegalArgumentException("Rows must have same length");
        }
      }
      rows.add(row);
      return this;
    }

    public MatrixRowBuilder addStringRow(final String row) {
      addLongRow(ProblemSolver.getArguments(row));
      return this;
    }

    public Matrix build() {
      return new Matrix(rows);
    }
  }

  public static class MatrixColumnBuilder {
    final List<List<Long>> rows = new ArrayList<>();

    public MatrixColumnBuilder addLongColumn(final List<Long> column) {
      if (rows.isEmpty()) {
        for (int i = 0; i < column.size(); i++) {
          rows.add(new ArrayList<>());
        }
      }
      for (int i = 0; i < column.size(); i++) {
        rows.get(i).add(column.get(i));
      }
      return this;
    }

    public Matrix build() {
      return new Matrix(rows);
    }
  }

  public long getValue(final int row, final int column) {
    return rows.get(row).get(column);
  }

  public int getRowCount() {
    return rows.size();
  }

  public int getColumnCount() {
    return rows.get(0).size();
  }

  public long applyToColum(final int column, final Operation operation) {
    return IntStream.range(0, getRowCount())
        .mapToObj(row -> getValue(row, column))
        .reduce(operation::apply)
        .orElse(0L);
  }
}
