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
      sum += solve(machine).map(Vector::sum).orElse(0);
      count++;
      System.out.println("Solved " + count + " of " + machines.size());
    }
    return sum;
  }

  public static int solveForValue(
      final Machine machine, final JoltageLevels solution, final ExpirationTime expirationTime) {
    System.out.println("Solving for value: " + solution);
    JoltageLevels zero = machine.newJoltage(0);
    final List<Vector> solutions = new ArrayList<>();
    final Matrix matrix = machine.getMatrix();
    final Vector coefficients = matrix.coefficientsWith(0);
    int total =
        solve(
            solution.getVector(),
            matrix,
            0,
            zero.getVector(),
            solutions,
            0,
            coefficients,
            expirationTime);
    final int min = solutions.stream().mapToInt(Vector::sum).min().orElse(0);
    System.out.println(
        "Value (" + solution.getVector() + ") combinations: " + total + " Min: " + min);
    return min;
  }

  public static Optional<Vector> solve(final Machine machine) {
    final Machine sortedMachine = machine.getSortedJoltage().removeZeroJoltages();
    final Vector value = Vector.withAll(sortedMachine.joltage().levels().size(), 0);
    final List<Vector> solutions = new ArrayList<>();
    final Matrix matrix = sortedMachine.getMatrix();
    final Vector coefficients = matrix.coefficientsWith(0);

    int total =
        solve(
            sortedMachine.joltage().getVector(),
            matrix,
            0,
            value,
            solutions,
            0,
            coefficients,
            ExpirationTime.never());
    final Optional<Vector> min = solutions.stream().min(Comparator.comparing(Vector::sum));
    System.out.println("Total combinations: " + total + " Min: " + min);
    return min;
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
    // return machines.stream().mapToInt(Toggler::solveInParts).sum();
  }

  public static int solveInParts(final Machine machine) {
    final ExpirationTime expirationTime = ExpirationTime.after(TimeUnit.SECONDS, 10);
    final JoltageLevels minJoltage = machine.newJoltage(machine.joltage().getMinValue());
    final int part1 = solveForValue(machine, minJoltage, expirationTime);
    if (part1 <= 0) {
      System.out.println("UNSOLVED!");
      return -1;
    } else {
      final JoltageLevels remainingJoltage = machine.joltage().subtract(minJoltage);
      final int part2 = solveForValue(machine, remainingJoltage, expirationTime);
      if (part2 < 0) {
        System.out.println("UNSOLVED!");
        return -1;
      }
      return part2 + part1;
    }
  }

  /**
   *
   * @param solution A {@link Vector} defining the sum of rows to solve/search for.
   * @param matrix A {@link Matrix} of rows that can be summed to solve the total.
   * @param currentColumn The index of the column to solve for.
   * @param currentTotal A {@link Vector} of the current sum of rows.
   * @param solvedCoefficients
   * @param total
   * @param currentCoefficients
   * @param expirationTime
   * @return
   */
  public static int solve(
      final Vector solution,
      final Matrix matrix,
      final int currentColumn,
      final Vector currentTotal,
      final List<Vector> solvedCoefficients,
      int total,
      Vector currentCoefficients,
      final ExpirationTime expirationTime) {
    if (currentColumn < matrix.getColumnCount()) {
      final int min =
          solvedCoefficients.stream().mapToInt(Vector::sum).min().orElse(Integer.MAX_VALUE);
      final List<Vector> rows = matrix.getRowsWithNonZeroColumn(currentColumn);
      final int columnTarget = solution.getValue(currentColumn);
      final int currentLevel = currentTotal.getValue(currentColumn);
      final int remainingLevel = columnTarget - currentLevel;
      if (currentCoefficients.sum() + remainingLevel < min) {
        // exclude any rows that will exceed the solution level
        final List<Vector> allowable =
            rows.stream()
                .filter(row -> !currentTotal.add(row).greaterThan(solution))
                .sorted(Comparator.comparingInt(Vector::sum).reversed())
                .toList();
        for (List<Vector> vectorCombination :
            Generator.combination(allowable).multi(remainingLevel)) {
          if (expirationTime.isExpired()) {
            return -1;
          }
          total++;
          final Map<Vector, Long> occurrences =
              vectorCombination.stream()
                  .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
          final Vector nextCoefficients = currentCoefficients.add(matrix.coefficientsFrom(occurrences));
          final Vector nextTotal = matrix.getSum(nextCoefficients);
          final int nextPresses = nextTotal.sum();
          if (solution.equals(nextTotal)) {
            solvedCoefficients.add(nextCoefficients);
            System.out.println("Presses: " + nextPresses + " Coefficients: " + nextCoefficients);
          } else if (solution.greaterThan(nextTotal) && currentColumn < matrix.getColumnCount()) {
            total =
                solve(
                    solution,
                    matrix,
                    currentColumn + 1,
                    nextTotal,
                    solvedCoefficients,
                    total,
                    nextCoefficients,
                    expirationTime);
          }
        }
      }
    }
    return total;
  }

  public static int bfs(final List<Machine> machines) {
    return machines.stream().mapToInt(Toggler::bfs).sum();
  }

  public static int bfs(final Machine machine) {
    final Matrix matrix = machine.getMatrix();
    final Vector solution = new Vector(machine.joltage().levels());
    final Vector zeros = Vector.withAll(matrix.getColumnCount(), 0);
    final Node root = new Node(zeros, 0);
    final LinkedList<Node> queue = new LinkedList<>();
    queue.add(root);
    int total = 1;
    while (!queue.isEmpty()) {
      final Node node = queue.remove();
      final List<Node> children = node.buildChildren(matrix, solution);
      total += children.size();
      final Optional<Node> winner =
          children.stream().filter(c -> c.getVector().equals(solution)).findFirst();
      if (winner.isPresent()) {
        System.out.println("Winner (after " + total + "): " + winner.get());
        return winner.get().getCount();
      }
      queue.addAll(children);
    }
    return -1;
  }

  public static int solveCoefficients(final List<Machine> machines) {
    return machines.stream().mapToInt(Toggler::solveCoefficients).sum();
  }

  public static int solveCoefficients(final Machine machine) {
    final List<Vector> vectors =
        machine.buttonList().stream()
            .map(b -> b.getVector(machine))
            .sorted(Comparator.comparingInt(Vector::sum))
            .toList();
    final Vector desiredJoltage = machine.joltage().getVector();
    final Vector value = Vector.withAll(machine.joltage().levels().size(), 0);
    final Vector coefficients = Vector.withAll(vectors.size(), 0);
    final List<Vector> solutions = new ArrayList<>();
    int total =
        solveCoefficients(
            desiredJoltage, value, coefficients, vectors, 0, solutions, 0, ExpirationTime.never());
    final int min = solutions.stream().mapToInt(Vector::sum).min().orElse(0);
    System.out.println("Total combinations: " + total + " Min: " + min);
    return min;
  }

  public static int solveCoefficients(
      final Vector desiredJoltage,
      final Vector currentValue,
      final Vector coefficients,
      final List<Vector> vectors,
      final int vectorIndex,
      final List<Vector> solutions,
      int totalCombinations,
      final ExpirationTime expirationTime) {

    if (vectorIndex < vectors.size()) {
      final OptionalInt minSolution = solutions.stream().mapToInt(Vector::sum).min();
      final Vector remainingJoltage = desiredJoltage.subtract(currentValue);
      final Vector vector = vectors.get(vectorIndex);
      final int maxCoefficient = vector.getMaxCoefficient(remainingJoltage);
      for (int coefficient = maxCoefficient; coefficient >= 0; coefficient--) {
        final Vector nextCoefficients = coefficients.withValueAt(vectorIndex, coefficient);
        if (minSolution.isEmpty() || minSolution.getAsInt() > nextCoefficients.sum()) {
          final Vector multiple = vector.multiply(coefficient);
          final Vector nextValue = currentValue.add(multiple);
          totalCombinations++;
          if (nextValue.equals(desiredJoltage)) {
            solutions.add(nextCoefficients);
            System.out.println(
                "Solution ("
                    + totalCombinations
                    + "): "
                    + nextCoefficients
                    + " Num Presses: "
                    + nextCoefficients.sum());
          } else if (nextValue.greaterThan(desiredJoltage)) {
            break;
          } else {
            totalCombinations =
                solveCoefficients(
                    desiredJoltage,
                    nextValue,
                    nextCoefficients,
                    vectors,
                    vectorIndex + 1,
                    solutions,
                    totalCombinations,
                    expirationTime);
          }
        }
      }
    }
    return totalCombinations;
  }

  public static int solveJama(final List<Machine> machines) {
    return machines.stream().mapToInt(Toggler::solveCoefficients).sum();
  }

  // solve linear equations
  public static int solveJama(final Machine machine) {
    final List<Vector> vectors =
        machine.buttonList().stream()
            .map(b -> b.getVector(machine))
            .sorted(Comparator.comparingInt(Vector::sum).reversed())
            .toList();
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
    Jama.Matrix lhs = new Jama.Matrix(lhsArray);
    Jama.Matrix rhs = new Jama.Matrix(rhsArray, numRows); // Specify number of rows

    // Solve for the variable vector X (A * X = B)
    // The solve() method performs the necessary linear algebra operations
    Jama.Matrix ans = lhs.solve(rhs);

    // Print the results (rounded for cleaner output)
    final List<Integer> solution = new ArrayList<>();
    for (int i = 0; i < vectors.size(); i++) {
      final int c = (int) Math.round(ans.get(i, 0));
      solution.add(c);
      System.out.println("c(" + i + ") = " + c);
    }

    final int min = solution.stream().mapToInt(i -> i).sum();
    System.out.println("Jama solution: " + min);
    return min;
  }

  public static int solveMinimumNorm(final List<Machine> machines) {
    return machines.stream().mapToInt(Toggler::solveMinimumNorm).sum();
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
    System.out.println("Rounded Solution: " + v);
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
