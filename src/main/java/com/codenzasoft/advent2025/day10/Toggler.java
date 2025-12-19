package com.codenzasoft.advent2025.day10;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.math3.util.Combinations;

public class Toggler {

  public static void main(String[] args) {
    final List<String> lines = PuzzleHelper.getInputLines("input-day-10.txt");
    final List<Machine> machines = lines.stream().map(Machine::parse).toList();
    System.out.println("The sum for part 1 is: " + part1(machines));
  }

  public static int part1(final List<Machine> machines) {
    int sum = 0;
    return machines.stream().mapToInt(Toggler::solveMachine).sum();
  }

  public static int solveMachine(final Machine machine) {
    final int n = machine.buttonsList().size();
    int minButtons = n;
    for (int m = 1; m <= n; m++) {
      final Combinations combinations = new Combinations(n, m);
      final Iterator<int[]> iterator = combinations.iterator();
      while (iterator.hasNext()) {
        final int[] combination = iterator.next();
        Lights state = Lights.allOff(machine.lights().states().size());
        for (int i = 0; i < combination.length; i++) {
          state = state.press(machine.buttonsList().get(combination[i]));
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
}
