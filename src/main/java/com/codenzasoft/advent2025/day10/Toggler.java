package com.codenzasoft.advent2025.day10;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.*;
import org.apache.commons.math3.util.Combinations;
import org.paukov.combinatorics3.Generator;

public class Toggler {

  public static void main(String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-10.txt");
    final List<Machine> machines = lines.stream().map(Machine::parse).toList();
    System.out.println("The sum for part 1 is: " + part1(machines));
    System.out.println("The sum for part 2 is: " + solveForMinValue(machines));
  }

  public static int part1(final List<Machine> machines) {
    return machines.stream().mapToInt(Toggler::solveMachine).sum();
  }

  public static int solveMachine(final Machine machine) {
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

  public static int solveForMinValue(final List<Machine> machines) {
    return machines.stream().mapToInt(m -> solveForValue(m, m.joltage().getMinValue())).sum();
  }

  public static int solveForValue(final Machine machine, final int value) {
    System.out.println("Solving for value: " + value);
    final List<ButtonCombination> seed = machine.getIndividualCombinations();
    JoltageLevels zero = machine.newJoltage(0);
    final List<Integer> solvedPresses = new ArrayList<>();
    int total =
        solve(
            machine.newJoltage(value),
            seed,
            0,
            zero,
            0,
            solvedPresses,
            0,
            List.of(),
            ExpirationTime.never());
    final int min = solvedPresses.stream().mapToInt(i -> i).min().orElse(0);
    System.out.println("Value (" + value + ") combinations: " + total + " Min: " + min);
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
}
