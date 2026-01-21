package com.codenzasoft.advent2025.day10;

import java.util.*;

public class Equation {

  private final Vector desiredTotal;
  private final List<Vector> coefficientSolutions = new ArrayList<>();
  private Optional<Vector> closestSolution;
  private Matrix matrix;

  public Equation(final Matrix matrix, final Vector desiredTotal) {
    this.desiredTotal = desiredTotal;
    this.closestSolution = Optional.empty();
    this.matrix = matrix;
  }

  public Equation optimize() {
    return this.reduce().reorder();
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
    return new Equation(newMatrix, newTotal);
  }

  /**
   * Returns an equivalent {@link Equation} with columns sorted in ascending order of desired
   * totals.
   *
   * @return An equivalent re-ordered {@link Equation}.
   */
  public Equation reorder() {
    final List<Integer> incdicies = getDesiredTotal().getAscendingIncdicies();
    final Vector newTotal = getDesiredTotal().reorder(incdicies);
    final Matrix newMatrix = getMatrix().reorderColumns(incdicies);
    return new Equation(newMatrix, newTotal);
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
}
