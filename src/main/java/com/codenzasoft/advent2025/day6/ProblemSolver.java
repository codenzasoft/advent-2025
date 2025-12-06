package com.codenzasoft.advent2025.day6;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProblemSolver {

  public static void main(String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-6.txt");
    final Matrix.MatrixRowBuilder builder = new Matrix.MatrixRowBuilder();
    for (int i = 0; i < lines.size() - 1; i++) {
      builder.addStringRow(lines.get(i));
    }
    final Matrix matrix = builder.build();
    final List<Operation> operations = getOperands(lines.get(lines.size() - 1));
    System.out.println("The answer to part 1 is: " + part1(matrix, operations));
    System.out.println("The answer to part 2 is: " + part2(lines));
  }

  public static List<Long> getArguments(final String line) {
    final String[] tokens = line.trim().split("\\s+");
    return Arrays.stream(tokens)
        .map(String::trim)
        .filter(s -> !s.isEmpty())
        .map(Long::parseLong)
        .toList();
  }

  public static List<Operation> getOperands(final String line) {
    final String[] tokens = line.trim().split("\\s+");
    return Arrays.stream(tokens).filter(s -> !s.isEmpty()).map(Operation::parse).toList();
  }

  public static long part1(final Matrix matrix, final List<Operation> operations) {
    long result = 0;
    for (int col = 0; col < matrix.getColumnCount(); col++) {
      result += matrix.applyToColum(col, operations.get(col));
    }
    return result;
  }

  public static long part2(final List<String> lines) {
    final List<String> rows = lines.subList(0, lines.size() - 1);
    final int rowLength = rows.get(0).length();
    final String operands = lines.get(lines.size() - 1);
    final List<List<Long>> groups = new ArrayList<>();
    List<Long> values = new ArrayList<>();
    for (int col = 0; col < rowLength; col++) {
      final StringBuilder builder = new StringBuilder();
      for (final String row : rows) {
        builder.append(row.charAt(col));
      }
      final String columnValue = builder.toString();
      if (!columnValue.trim().isEmpty()) {
        values.add(Long.parseLong(columnValue.trim()));
      }
      if (columnValue.trim().isBlank() || col == rowLength - 1) {
        // end of column
        groups.add(new ArrayList<>(values));
        values.clear();
      }
    }
    final List<Operation> operations = getOperands(operands);

    long result = 0;
    for (int i = 0; i < groups.size(); i++) {
      final Operation operation = operations.get(i);
      final List<Long> group = groups.get(i);
      result += operation.apply(group);
    }

    return result;
  }
}
