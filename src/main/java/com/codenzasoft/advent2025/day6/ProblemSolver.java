package com.codenzasoft.advent2025.day6;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.Arrays;
import java.util.List;

public class ProblemSolver {

  public static void main(String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-6.txt");
    final Matrix.MatrixBuilder builder = new Matrix.MatrixBuilder();
    for (int i = 0; i < lines.size() - 1; i++) {
      builder.addStringRow(lines.get(i));
    }
    final Matrix matrix = builder.build();
    final List<Operation> operations = getOperands(lines.get(lines.size() - 1));
    System.out.println("The answer to part 1 is: " + part1(matrix, operations));
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
}
