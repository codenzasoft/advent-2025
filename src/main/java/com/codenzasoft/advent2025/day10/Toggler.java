package com.codenzasoft.advent2025.day10;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.*;
import org.apache.commons.math3.util.Combinations;

public class Toggler {

  public static void main(String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-10.txt");
    final List<Machine> machines = lines.stream().map(Machine::parse).toList();
    System.out.println("The sum for part 1 is: " + part1(machines));
    System.out.println("The sum for part 2 is: " + part2(machines));
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
    return machines.stream().mapToInt(Toggler::solveJoltage).sum();
  }

  public static int solveJoltage(final Machine machine) {
    JoltageLevels outputLevels = JoltageLevels.zero(machine.joltage().levels().size());
    List<ButtonCombination> remaining = machine.getIndividualCombinations();
    int totalPresses = 0;
    while (!remaining.isEmpty()) {
      // Start with the buttons needing the most presses
      final ButtonCombination max =
          Collections.max(remaining, Comparator.comparingInt(ButtonCombination::presses));

      // find a common button with the most presses remaining
      final Button button;
      final int presses;
      final Optional<ButtonCombination> next =
          remaining.stream()
              .filter(c -> !c.equals(max) && !c.getCommonButtons(max).isEmpty())
              .max(Comparator.comparingInt(ButtonCombination::presses));
      if (next.isPresent()) {
        button = next.get().getCommonButtons(max).get(0);
        // find the actual max number of presses allowed for the common button
        presses =
            remaining.stream()
                .filter(c -> c.buttons().contains(button))
                .mapToInt(ButtonCombination::presses)
                .min()
                .orElse(next.get().presses());
      } else {
        // nothing in common - press the max
        button = max.buttons().get(0);
        presses = max.presses();
      }
      outputLevels = outputLevels.press(button, presses);
      totalPresses += presses;
      remaining = remaining.stream().map(c -> c.getRemaining(button, presses)).toList();
      final List<ButtonCombination> empty =
          remaining.stream().filter(c -> c.presses() == 0).toList();
      for (final ButtonCombination buttonCombination : empty) {
        remaining =
            remaining.stream()
                .map(c -> c.removeButtons(buttonCombination.buttons()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
      }
    }
    if (outputLevels.equals(machine.joltage())) {
      System.out.println("Expected Joltage: " + outputLevels);
    } else {
      System.out.println("Unexpected Joltage: " + outputLevels + " vs" + machine.joltage());
    }
    return totalPresses;
  }
}
