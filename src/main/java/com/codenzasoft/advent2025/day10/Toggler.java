package com.codenzasoft.advent2025.day10;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.util.Combinations;
import org.paukov.combinatorics3.Generator;

public class Toggler {

  public static void main(String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-10.txt");
    final List<Machine> machines = lines.stream().map(Machine::parse).toList();
    System.out.println("The sum for part 1 is: " + part1(machines));
    System.out.println("The sum for part 2 is: " + part2(machines));
  }

  public static int part1(final List<Machine> machines) {
    return machines.stream().mapToInt(Toggler::solveMachineLights).sum();
  }

  public static int solveMachineLights(final Machine machine) {
    final int n = machine.buttonList().size();
    int minButtons = n;
    for (int m = 1; m <= n; m++) {
      final Combinations combinations = new Combinations(n, m);
      final Iterator<int[]> iterator = combinations.iterator();
      while (iterator.hasNext()) {
        final int[] combination = iterator.next();
        Lights state = Lights.allOff(machine.lights().states().size());
        for (int i = 0; i < combination.length; i++) {
          state = state.press(machine.buttonList().get(combination[i]));
        }
        if (state.equals(machine.lights())) {
          if (combination.length < minButtons) {
            minButtons = combination.length;
            break;
          }
        }
      }
    }
    return minButtons;
  }

  public static int part2(final List<Machine> machines) {
    int sum = 0;
    int count = 0;
    for (final Machine machine : machines) {
      sum += solve(machine).getSolutionSum().orElse(0);
      count++;
      System.out.println("Solved " + count + " of " + machines.size());
    }
    return sum;
  }

  public static Equation solve(final Machine machine) {
    return solve(machine.getEquation().optimize());
  }

  public static Equation solve(final Equation equation) {
    final Vector initialTotal = Vector.withAll(equation.getMatrix().getColumnCount(), 0);
    final Vector initialCoefficients = equation.getMatrix().coefficientsWith(0);

    int total = solve(equation, 0, initialTotal, initialCoefficients, 0);
    final Optional<Vector> min = equation.getSolution();
    System.out.println("Total combinations: " + total + " Min: " + min);
    return equation;
  }

  public static int solveInParts(final List<Machine> machines) {
    int sum = 0;
    int count = 0;
    for (final Machine machine : machines) {
      sum += solveInParts(machine);
      count++;
      System.out.println("Solved " + count + " of " + machines.size());
    }
    return sum;
  }

  public static int solveInParts(final Machine machine) {
    final Equation equation = machine.getEquation().optimize();

    final Vector total1 =
        Vector.withAll(
            equation.getMatrix().getColumnCount(), equation.getDesiredTotal().minValue());
    final Equation equation1 =
        new Equation(equation.getMatrix(), total1, ExpirationTime.after(TimeUnit.SECONDS, 5));
    solve(equation1);
    final Vector remainingTotal;
    final int part1Sum;
    if (equation1.getSolution().isEmpty()) {
      remainingTotal =
          equation
              .getDesiredTotal()
              .subtract(equation1.getMatrix().getSum(equation1.getClosestSolution().get()));
      part1Sum = equation1.getClosestSolution().get().sum();
    } else {
      remainingTotal = equation.getDesiredTotal().subtract(total1);
      part1Sum = equation1.getSolutionSum().getAsInt();
    }
    final Equation equation2 = new Equation(equation.getMatrix(), remainingTotal);
    solve(equation2);
    if (equation2.getSolution().isEmpty()) {
      System.out.println("UNSOLVED!");
      return -1;
    }
    return equation2.getSolutionSum().getAsInt() + part1Sum;
  }

  /**
   * Searches for a minimum equation to achieve the desired total defined in the provided equation,
   * by adding any combination of rows in the associated matrix. Solutions are added to the provided
   * {@link Equation} as a {@link Vector} of coefficients describing the number of each row in the
   * matrix used to produce the desiredTotal.
   *
   * <p>The algorithm works by attempting all combinations of each column that add up to the desired
   * total for that column. Once a equation is found, it is used to reduce the search space by
   * avoiding solutions that require more rows to achieve the desired total.
   *
   * @param equation A {@link Equation} defining the desired sum of rows to solve/search for and
   *     associated {@link Matrix}.
   * @param currentColumn The column of the matrix currently being solved.
   * @param currentTotal A {@link Vector} of the current sum.
   * @param currentCoefficients A {@link Vector} of the current coefficients.
   * @param attemptedCombinationsCount Total number of coefficient combinations tried.
   * @return The number of coefficient combinations attempted.
   */
  public static int solve(
      final Equation equation,
      final int currentColumn,
      final Vector currentTotal,
      Vector currentCoefficients,
      int attemptedCombinationsCount) {
    if (currentColumn < equation.getMatrix().getColumnCount()) {
      final int min = equation.getSolution().map(Vector::sum).orElse(Integer.MAX_VALUE);
      final List<Vector> rows = equation.getMatrix().getRowsWithNonZeroColumn(currentColumn);
      final int desiredColumnTotal = equation.getDesiredTotal().getValue(currentColumn);
      final int currentColumnTotal = currentTotal.getValue(currentColumn);
      final int remainingLevel = desiredColumnTotal - currentColumnTotal;
      if (currentCoefficients.sum() + remainingLevel < min) {
        // exclude any rows that will exceed the desired total
        final List<Vector> allowable =
            rows.stream()
                .filter(row -> !currentTotal.add(row).greaterThan(equation.getDesiredTotal()))
                .sorted(Comparator.comparingInt(Vector::sum).reversed())
                .toList();
        for (List<Vector> vectorCombination :
            Generator.combination(allowable).multi(remainingLevel)) {
          if (equation.isExpired()) {
            return -1;
          }
          attemptedCombinationsCount++;
          final Map<Vector, Long> occurrences =
              vectorCombination.stream()
                  .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
          final Vector nextCoefficients =
              currentCoefficients.add(equation.getMatrix().coefficientsFrom(occurrences));
          final Vector nextTotal = equation.getMatrix().getSum(nextCoefficients);
          if (equation.addSolution(nextTotal, nextCoefficients)) {
            System.out.println(
                "Found Equation: "
                    + nextCoefficients
                    + "("
                    + attemptedCombinationsCount
                    + " combinations)");
          } else if (equation.getDesiredTotal().greaterThan(nextTotal)
              && currentColumn < equation.getMatrix().getColumnCount()) {
            equation.addClosestSolution(nextCoefficients);
            attemptedCombinationsCount =
                solve(
                    equation,
                    currentColumn + 1,
                    nextTotal,
                    nextCoefficients,
                    attemptedCombinationsCount);
          }
        }
      }
    }
    return attemptedCombinationsCount;
  }

  public static int solveMinimumNorm(final Machine machine) {
    final List<Vector> vectors =
        machine.buttonList().stream().map(b -> b.getVector(machine)).toList();
    final Vector desiredJoltage = machine.joltage().getVector();

    // Coefficients matrix A - vectors become the columns
    final int numRows = desiredJoltage.values().size();
    final int numCols = vectors.size();
    double[][] lhsArray = new double[numRows][numCols];
    for (int r = 0; r < numRows; r++) {
      for (int c = 0; c < numCols; c++) {
        lhsArray[r][c] = vectors.get(c).getValue(r);
      }
    }
    // Constants vector B
    double[] rhsArray = desiredJoltage.toJama();

    // Create Matrix objects from the arrays
    RealMatrix a = new Array2DRowRealMatrix(lhsArray);
    RealVector b = new Array2DRowRealMatrix(rhsArray).getColumnVector(0);

    RealVector minNormSolution = solveMinimumNorm(a, b);
    System.out.println("Minimum norm solution: " + minNormSolution);
    System.out.println("Norm: " + minNormSolution.getL1Norm());
    final List<Integer> coefficients = new ArrayList<>();
    for (double v : minNormSolution.toArray()) {
      coefficients.add((int) Math.round(v));
    }
    final Vector v = new Vector(coefficients);
    System.out.println("Rounded Equation: " + v);
    System.out.println("Validated: " + multiply(vectors, v));
    return (int) Math.round(Math.floor(minNormSolution.getL1Norm()));
  }

  public static RealVector solveMinimumNorm(RealMatrix A, RealVector b) {
    // Use Singular Value Decomposition (SVD) for handling underdetermined systems
    DecompositionSolver solver = new SingularValueDecomposition(A).getSolver();

    // The solve method for SVD decomposition automatically computes
    // the minimum norm least-squares solution for underdetermined systems.
    // It's typically more robust than using the direct pseudoinverse calculation.
    if (solver.isNonSingular()) {
      // For a unique solution case, it returns the single solution
      System.out.println("System has a unique solution.");
    } else {
      System.out.println(
          "System has multiple solutions (or no solution), finding minimum norm solution.");
    }

    RealVector x = solver.solve(b);
    return x;
  }

  public static Vector multiply(final List<Vector> vectors, final Vector coefficients) {
    final List<Vector> newVectors = new ArrayList<>();
    for (int i = 0; i < vectors.size(); i++) {
      newVectors.add(vectors.get(i).multiply(coefficients.getValue(i)));
    }
    Vector sum = Vector.withAll(newVectors.get(0).values().size(), 0);
    for (int i = 1; i < newVectors.size(); i++) {
      sum = sum.add(newVectors.get(i));
    }
    return sum;
  }
}
