package com.codenzasoft.advent2025.day10;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Equation {

  private final Vector desiredTotal;
  private final List<Vector> coefficientSolutions = new ArrayList<>();
  private Optional<Vector> closestSolution;
  private Optional<ExpirationTime> expirationTime;
  private Matrix matrix;
  private Vector initialCoefficients = null;
  private Vector initialTotals = null;

  public Equation(final Matrix matrix, final Vector desiredTotal) {
    this(matrix, desiredTotal, null);
  }

  public Equation(
      final Matrix matrix, final Vector desiredTotal, final ExpirationTime expirationTime) {
    this.desiredTotal = desiredTotal;
    this.closestSolution = Optional.empty();
    this.expirationTime = Optional.ofNullable(expirationTime);
    this.matrix = matrix;
    withDefaultInitialCoefficients();
  }

  public Equation optimize() {
    return this.reduce().reorderColumns();
  }

  /**
   * Returns a reduced {@link Equation} if the desired total of this equation contains any zeros.
   * Any columns with zero totals can be removed from the search. Additionally, any rows with a
   * non-zero value in those columns can be removed from the search, as they cannot be used. This
   * reduces the number of combinations in the search space.
   *
   * @return An equivalent optimized {@link Equation}.
   */
  public Equation reduce() {
    final List<Integer> zeroIndicies = getDesiredTotal().indiciesOf(0);
    if (zeroIndicies.isEmpty()) {
      return this;
    }
    final Vector newTotal = getDesiredTotal().removeIndicies(zeroIndicies);
    Matrix newMatrix = getMatrix().removeColumns(zeroIndicies);
    final List<Integer> rowsToRemove = new ArrayList<>();
    for (int row = 0; row < getMatrix().getRowCount(); row++) {
      for (int col : zeroIndicies) {
        if (getMatrix().getRow(row).getValue(col) != 0) {
          rowsToRemove.add(row);
          break;
        }
      }
    }
    newMatrix = newMatrix.removeRows(rowsToRemove);
    return new Equation(newMatrix, newTotal, expirationTime.orElse(null));
  }

  /**
   * Returns an equivalent {@link Equation} with columns sorted in ascending order of desired
   * totals.
   *
   * @return An equivalent re-ordered {@link Equation}.
   */
  public Equation reorderColumns() {
    final List<Integer> incdicies = getDesiredTotal().getAscendingIncdicies();
    final Vector newTotal = getDesiredTotal().reorder(incdicies);
    final Matrix newMatrix = getMatrix().reorderColumns(incdicies);
    return new Equation(newMatrix, newTotal, expirationTime.orElse(null));
  }

  /**
   * Returns an equivalent {@link Equation} with rows sorted in descending order of binary value.
   *
   * @return An equivalent re-ordered {@link Equation}.
   */
  public Equation reorderRows() {
    final List<Integer> rowValues =
        getMatrix().rows().stream().map(Vector::getBinaryValue).toList();
    final List<Integer> indicies =
        IntStream.range(0, rowValues.size())
            .boxed()
            .sorted(Comparator.comparing(rowValues::get).reversed())
            .collect(Collectors.toList());
    return new Equation(
        getMatrix().reorderRows(indicies), getDesiredTotal(), expirationTime.orElse(null));
  }

  public Equation withDefaultInitialCoefficients() {
    initialTotals = Vector.withAll(getMatrix().getColumnCount(), 0);
    initialCoefficients = Vector.withAll(getMatrix().getRowCount(), 0);
    return this;
  }

  public Equation withOptimizedInitialCoefficients() {
    final List<Integer> initialCoefficientValues =
        new ArrayList<>(getMatrix().coefficientsWith(0).values());
    for (int col = 0; col < getMatrix().getColumnCount(); col++) {
      final Vector column = getMatrix().getColumn(col);
      if (column.sum() == 1) {
        System.out.println("INDEPENDENT COLUMN: " + col);
        final int coefficientIndex = column.indiciesOf(1).get(0);
        initialCoefficientValues.set(coefficientIndex, getDesiredTotal().getValue(col));
      }
    }

    initialCoefficients = new Vector(initialCoefficientValues);
    initialTotals = getMatrix().getSum(initialCoefficients);
    addSolution(initialTotals, initialCoefficients);
    return this;
  }

  public Vector getInitialCoefficients() {
    return initialCoefficients;
  }

  public Vector getInitialTotals() {
    return initialTotals;
  }

  public Matrix getMatrix() {
    return matrix;
  }

  public boolean isSolution(final Vector aTotal) {
    return aTotal.equals(desiredTotal);
  }

  public Vector getDesiredTotal() {
    return desiredTotal;
  }

  public boolean addSolution(final Vector solution, final Vector coefficients) {
    if (isSolution(solution)) {
      coefficientSolutions.add(coefficients);
      return true;
    }
    return false;
  }

  public Optional<Vector> getSolution() {
    return coefficientSolutions.stream().min(Comparator.comparing(Vector::sum));
  }

  public OptionalInt getSolutionSum() {
    return getSolution().stream().mapToInt(Vector::sum).min();
  }

  public Optional<Vector> getClosestSolution() {
    return closestSolution;
  }

  public void addClosestSolution(final Vector coefficients) {
    if (closestSolution.isPresent()) {
      final Vector nextSum = getMatrix().getSum(coefficients);
      final int diff = getDesiredTotal().subtract(nextSum).sum();
      final Vector prevClosest = closestSolution.get();
      final int prevDiff =
          getDesiredTotal().subtract(getMatrix().getSum(closestSolution.get())).sum();
      if (diff <= prevDiff && prevClosest.sum() < coefficients.sum()) {
        closestSolution = Optional.of(coefficients);
      }
    } else {
      closestSolution = Optional.of(coefficients);
    }
  }

  public boolean isExpired() {
    return expirationTime.isPresent() && expirationTime.get().isExpired();
  }

  public boolean isGreaterThanDesiredTotal(final Vector vector) {
    return vector.greaterThan(getDesiredTotal());
  }
}
