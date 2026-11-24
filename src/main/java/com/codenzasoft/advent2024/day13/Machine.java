package com.codenzasoft.advent2024.day13;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.linear.*;

public record Machine(Vector a, Vector b, Vector result) {

  private static final double EPSILON = 1e-2;

  public static List<Machine> parse(final List<String> input) {
    final Iterator<String> iterator = input.iterator();
    List<Machine> machines = new ArrayList<>();
    while (iterator.hasNext()) {
      Vector buttonA = Vector.parseButton(iterator.next()); // Button A: X+94, Y+34
      Vector buttonB = Vector.parseButton(iterator.next()); // Button B: X+22, Y+67
      Vector prize = Vector.parsePrize(iterator.next()); // Prize: X=8400, Y=5400
      if (iterator.hasNext()) {
        iterator.next(); // blank line
      }
      machines.add(new Machine(buttonA, buttonB, prize));
    }
    return machines;
  }

  // Example (2x + 3y = 8) and (5x - y = 3)
  public Vector solve() throws SingularMatrixException {
    // Coefficients matrix A
    RealMatrix coefficients =
        new Array2DRowRealMatrix(
            new double[][] {
              {a().x(), b().x()},
              {a().y(), b().y()}
            },
            false);

    // Constants vector b
    RealVector constants = new ArrayRealVector(new double[] {result().x(), result().y()}, false);

    // Solve A * x = b
    DecompositionSolver solver = new LUDecomposition(coefficients).getSolver();
    RealVector solution = solver.solve(constants);
    if (isIntegers(solution)) {
      return new Vector(Math.round(solution.getEntry(0)), Math.round(solution.getEntry(1)));
    }
    throw new SingularMatrixException();
  }

  private static boolean isIntegers(RealVector vector) {
    for (int i = 0; i < vector.getDimension(); i++) {
      final double value = vector.getEntry(i);
      if (Math.abs(value - Math.round(value)) > EPSILON) {
        return false;
      }
    }
    return true;
  }
}
