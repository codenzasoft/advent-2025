package com.codenzasoft.advent2025.day10;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.*;
import java.util.concurrent.TimeUnit;
import org.apache.commons.math3.util.Combinations;
import org.paukov.combinatorics3.Generator;

public class Toggler {

  public static void main(String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-10.txt");
    final List<Machine> machines = lines.stream().map(Machine::parse).toList();
    System.out.println("The sum for part 1 is: " + part1(machines));
    System.out.println("The sum for part 2 is: " + solveCoefficients(machines));
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
    return machines.stream().mapToInt(Toggler::solve).sum();
  }

  public static int solveForValue(
      final Machine machine, final JoltageLevels solution, final ExpirationTime expirationTime) {
    System.out.println("Solving for value: " + solution);
    final List<ButtonCombination> seed = machine.getIndividualCombinations();
    JoltageLevels zero = machine.newJoltage(0);
    final List<Integer> solvedPresses = new ArrayList<>();
    int total = solve(solution, seed, 0, zero, 0, solvedPresses, 0, List.of(), expirationTime);
    final int min = solvedPresses.stream().mapToInt(i -> i).min().orElse(0);
    System.out.println("Value (" + seed + ") combinations: " + total + " Min: " + min);
    return min;
  }

  public static int solve(final Machine machine) {
    final List<ButtonCombination> seed = machine.getIndividualCombinations();
    final JoltageLevels value = machine.newJoltage(0);
    final List<Integer> solvedPresses = new ArrayList<>();
    int total =
        solve(
            machine.joltage(),
            seed,
            0,
            value,
            0,
            solvedPresses,
            0,
            List.of(),
            ExpirationTime.never());
    final int min = solvedPresses.stream().mapToInt(i -> i).min().orElse(0);
    System.out.println("Total combinations: " + total + " Min: " + min);
    return min;
  }

  public static int solveInParts(final List<Machine> machines) {
    return machines.stream().mapToInt(Toggler::solveInParts).sum();
  }

  public static int solveInParts(final Machine machine) {
    final ExpirationTime expirationTime = ExpirationTime.after(TimeUnit.SECONDS, 10);
    final JoltageLevels minJoltage = machine.newJoltage(machine.joltage().getMinValue());
    final int part1 = solveForValue(machine, minJoltage, expirationTime);
    if (part1 < 0) {
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

  public static int solve(
      final JoltageLevels solution,
      final List<ButtonCombination> combinations,
      final int combinationIndex,
      final JoltageLevels levels,
      final int presses,
      final List<Integer> solvedPresses,
      int total,
      List<List<Button>> currentCombination,
      final ExpirationTime expirationTime) {
    if (combinationIndex < combinations.size()) {
      final int min = solvedPresses.stream().mapToInt(i -> i).min().orElse(Integer.MAX_VALUE);
      final ButtonCombination combination = combinations.get(combinationIndex);
      final int targetLevel = solution.levels().get(combinationIndex);
      final int currentLevel = levels.levels().get(combinationIndex);
      final int remainingLevel = targetLevel - currentLevel;
      if (presses + remainingLevel < min) {
        // exclude any buttons that will exceed the expected level
        final List<Button> allowable =
            combination.buttons().stream()
                .filter(button -> !solution.isExceededBy(levels.press(button, 1)))
                .toList();
        for (List<Button> buttonList : Generator.combination(allowable).multi(remainingLevel)) {
          if (expirationTime.isExpired()) {
            return -1;
          }
          total++;
          JoltageLevels nextLevel = levels.duplicate();
          int nextPresses = presses;
          final List<List<Button>> curr = new ArrayList<>(currentCombination);
          curr.add(buttonList);
          for (final Button button : buttonList) {
            nextLevel = nextLevel.press(button, 1);
            nextPresses++;
          }
          if (solution.equals(nextLevel)) {
            solvedPresses.add(nextPresses);
            System.out.println("Presses: " + nextPresses + " Combination: " + curr);
          } else if (!solution.isExceededBy(nextLevel) && combinationIndex < combinations.size()) {
            total =
                solve(
                    solution,
                    combinations,
                    combinationIndex + 1,
                    nextLevel,
                    nextPresses,
                    solvedPresses,
                    total,
                    curr,
                    expirationTime);
          }
        }
      }
    }
    return total;
  }

  public static int bfs(final List<Machine> machines) {
    return machines.stream().mapToInt(Toggler::solve).sum();
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
        machine.buttonList().stream().map(b -> b.getVector(machine)).toList();
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
      final Vector remainingJoltage = desiredJoltage.subtract(currentValue);
      final Vector vector = vectors.get(vectorIndex);
      final int maxCoefficient = vector.getMaxCoefficient(remainingJoltage);
      for (int coefficient = 0; coefficient <= maxCoefficient; coefficient++) {
        final Vector nextCoefficients = coefficients.withValueAt(vectorIndex, coefficient);
        final Vector multiple = vector.multiply(coefficient);
        final Vector nextValue = currentValue.add(multiple);
        totalCombinations++;
        if (nextValue.equals(desiredJoltage)) {
          solutions.add(nextCoefficients);
          System.out.println("Solution (" + totalCombinations + "): " + nextCoefficients);
        } else if (!nextValue.greaterThan(desiredJoltage)) {
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
    return totalCombinations;
  }
}
