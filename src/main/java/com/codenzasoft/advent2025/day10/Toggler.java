package com.codenzasoft.advent2025.day10;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.math3.linear.*;
import org.apache.commons.math3.util.Combinations;
import org.la4j.decomposition.SingularValueDecompositor;
import org.la4j.matrix.dense.Basic2DMatrix;
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

  public static int breakdown(final List<Machine> machines) {
    int sum = 0;
    int count = 0;
    for (final Machine machine : machines) {
      sum += breakdown(machine);
      count++;
      System.out.println("Solved " + count + " of " + machines.size());
    }
    return sum;
  }

  public static int breakdown(final Machine machine) {
    final Equation equation = machine.getEquation();

    // build vector required to make desired total even
    final List<Integer> remainders = new ArrayList<>();
    for (int v : equation.getDesiredTotal().values()) {
      remainders.add(v % 2);
    }
    final Vector remainder = new Vector(remainders);

    final Vector modifiedTotal = equation.getDesiredTotal().subtract(remainder);
    final Vector half = modifiedTotal.divide(2);

    final Equation remainderResult = solve(new Equation(equation.getMatrix(), remainder));
    if (remainderResult.getSolution().isPresent()) {
      System.out.println("Remainder result AVAILABLE");
      final Equation halfResult = solve(new Equation(equation.getMatrix(), half).optimize());
      if (halfResult.getSolution().isPresent()) {
        System.out.println("Half result AVAILABLE");
        return (halfResult.getSolution().get().sum() * 2)
            + remainderResult.getSolution().get().sum();
      }
    }

    return solve(machine).getSolutionSum().orElse(0);
  }

  public static Equation solve(final Machine machine) {
    return solve(machine.getEquation().optimize().withOptimizedInitialCoefficients());
  }

  public static Equation solve(final Equation equation) {
    System.out.println("Matrix: " + equation.getMatrix());
    System.out.println("Solving for total: " + equation.getDesiredTotal());
    int total =
        solve(equation, 0, equation.getInitialTotals(), equation.getInitialCoefficients(), 0);
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

    final int gcd = findGCD(equation.getDesiredTotal().values());
    final Vector total1 = Vector.withAll(equation.getMatrix().getColumnCount(), gcd);
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
      final int multiple = equation.getDesiredTotal().minValue() / gcd;
      remainingTotal = equation.getDesiredTotal().subtract(total1.multiply(multiple));
      part1Sum = equation1.getSolutionSum().getAsInt() * multiple;
    }
    final Equation equation2 = new Equation(equation.getMatrix(), remainingTotal);
    solve(equation2);
    if (equation2.getSolution().isEmpty()) {
      System.out.println("UNSOLVED!");
      return -1;
    }
    return equation2.getSolutionSum().getAsInt() + part1Sum;
  }

  public static int findGCD(final List<Integer> numbers) {
    BigInteger result = BigInteger.valueOf(numbers.get(0));
    for (int i = 1; i < numbers.size(); i++) {
      result = result.gcd(BigInteger.valueOf(numbers.get(i)));
      if (result.equals(BigInteger.ONE)) {
        return 1; // GCD is 1, no need to check further
      }
    }
    return result.intValue();
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
      final int min = equation.getSolutionLimit();
      final List<Vector> rows = equation.getMatrix().getRowsWithHighOrderColumn(currentColumn);
      final int desiredColumnTotal = equation.getDesiredTotal().getValue(currentColumn);
      final int currentColumnTotal = currentTotal.getValue(currentColumn);
      final int remainingLevel = desiredColumnTotal - currentColumnTotal;
      if (remainingLevel == 0 && currentColumn < equation.getMatrix().getColumnCount()) {
        // slight shortcut if this column is already at its desired total
        attemptedCombinationsCount =
            solve(
                equation,
                currentColumn + 1,
                currentTotal,
                currentCoefficients,
                attemptedCombinationsCount);
      } else if (currentCoefficients.sum() + remainingLevel < min) {
        // exclude any rows that will exceed the desired total
        final List<Vector> allowable =
            rows.stream()
                .filter(row -> !currentTotal.add(row).greaterThan(equation.getDesiredTotal()))
                .sorted(Comparator.comparingInt(Vector::getBinaryValue).reversed())
                .toList();
        if (allowable.isEmpty()) {
          // slight shortcut if there are no allowable rows
          attemptedCombinationsCount =
              solve(
                  equation,
                  currentColumn + 1,
                  currentTotal,
                  currentCoefficients,
                  attemptedCombinationsCount);
        } else {
          for (List<Vector> vectorCombination :
              Generator.combination(allowable).multi(remainingLevel)) {
            if (equation.isExpired()) {
              return -1;
            }
            attemptedCombinationsCount++;
            if (attemptedCombinationsCount % 1000000 == 0) {
              System.out.println(
                  "Attempted combinations: "
                      + attemptedCombinationsCount
                      + " Coefficients: "
                      + currentCoefficients
                      + " Total: "
                      + currentTotal);
            }
            final Map<Vector, Long> occurrences =
                vectorCombination.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            final Vector nextCoefficients =
                currentCoefficients.add(equation.getMatrix().coefficientsFrom(occurrences));
            final Vector nextTotal = equation.getMatrix().getSum(nextCoefficients);
            if (equation.addSolution(nextTotal, nextCoefficients)) {
              System.out.println(
                  "Found Solution: "
                      + nextCoefficients
                      + " Sum: "
                      + nextCoefficients.sum()
                      + " ("
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

  public static long useLa4j(final List<Machine> machines) {
    long sum = 0;
    int count = 0;
    for (final Machine machine : machines) {
      sum += Math.floor(useLa4j(machine));
      count++;
      System.out.println("Solved " + count + " of " + machines.size());
    }
    return sum;
  }

  public static double useLa4j(final Machine machine) {
    return useLa4j(machine.getEquation()).sum();
  }

  public static org.la4j.Vector useLa4j(final Equation equation) {
    // 1. Create the coefficient matrix 'a'
    org.la4j.Matrix a = equation.getMatrix().flip().toLa4j();

    // 2. Create the right-hand side vector 'b'
    org.la4j.Vector b = equation.getDesiredTotal().toLa4j();

    // Perform SVD
    // The result is an array of matrices: {U, S, V^T}
    // U: Left Singular Vectors
    // S: Singular Values (Diagonal Matrix)
    // V: Right Singular Vectors
    SingularValueDecompositor svd = new SingularValueDecompositor(a);
    org.la4j.Matrix[] decomposition = svd.decompose();

    org.la4j.Matrix U = decomposition[0];
    org.la4j.Matrix S = decomposition[1];
    org.la4j.Matrix V = decomposition[2];

    // Create pseudo-inverse of the diagonal matrix
    // 2. Compute S+ (diagonal matrix of reciprocal singular values)
    // SVD in la4j returns S as a diagonal matrix
    org.la4j.Matrix S_plus = new Basic2DMatrix(S.rows(), S.columns());
    for (int i = 0; i < Math.min(S.rows(), S.columns()); i++) {
      double val = S.get(i, i);
      if (val > 1e-10) { // Using a tolerance for zero
        S_plus.set(i, i, 1.0 / val);
      }
    }
    // S+ must be transposed for the final multiplication
    S_plus = S_plus.transpose();

    // 3. Compute Pseudo-inverse: A+ = V * S+ * Ut
    // Note: la4j SVD returns V, not Vt, so we use Vt = V.transpose()
    org.la4j.Matrix pseudoInverse = V.multiply(S_plus).multiply(U.transpose());

    System.out.println("Pseudo-inverse:\n" + pseudoInverse);

    // Multiply by the vector to get the answer
    org.la4j.Vector x = pseudoInverse.multiply(b);

    // 5. Output the result
    System.out.println("Result :" + x + " Sum: " + x.sum());
    return x;
  }

  public static Equation adHoc(final Equation equation) {
    final Equation ordered = equation.optimize();
    return ordered;
  }

  public static int adHoc(
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
      if (remainingLevel == 0 && currentColumn < equation.getMatrix().getColumnCount()) {
        // slight shortcut if this column is already at its desired total
        attemptedCombinationsCount =
            adHoc(
                equation,
                currentColumn + 1,
                currentTotal,
                currentCoefficients,
                attemptedCombinationsCount);
      } else if (currentCoefficients.sum() + remainingLevel < min) {
        // exclude any rows that will exceed the desired total
        final List<Vector> allowable =
            rows.stream()
                .filter(row -> !currentTotal.add(row).greaterThan(equation.getDesiredTotal()))
                .sorted(Comparator.comparingInt(Vector::getReversedBinaryValue).reversed())
                .toList();
        if (allowable.isEmpty()) {
          // slight shortcut if there are no allowable rows
          attemptedCombinationsCount =
              solve(
                  equation,
                  currentColumn + 1,
                  currentTotal,
                  currentCoefficients,
                  attemptedCombinationsCount);
        } else {
          for (List<Vector> vectorCombination :
              Generator.combination(allowable).multi(remainingLevel)) {
            if (equation.isExpired()) {
              return -1;
            }
            attemptedCombinationsCount++;
            if (attemptedCombinationsCount % 1000000 == 0) {
              System.out.println("Attempted combinations: " + attemptedCombinationsCount);
            }
            final Map<Vector, Long> occurrences =
                vectorCombination.stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
            final Vector nextCoefficients =
                currentCoefficients.add(equation.getMatrix().coefficientsFrom(occurrences));
            final Vector nextTotal = equation.getMatrix().getSum(nextCoefficients);
            if (equation.addSolution(nextTotal, nextCoefficients)) {
              System.out.println(
                  "Found Solution: "
                      + nextCoefficients
                      + " Sum: "
                      + nextCoefficients.sum()
                      + " ("
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
    }
    return attemptedCombinationsCount;
  }
}
