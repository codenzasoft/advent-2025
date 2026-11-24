package com.codenzasoft.advent2024.day13;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;
import org.apache.commons.math3.linear.SingularMatrixException;

public class Day13 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-13.txt");
    System.out.println("The answer to part 1 is: " + new Day13().solvePart1(lines));
  }

  public int solvePart1(final List<String> input) {
    int tokens = 0;
    final List<Machine> machines = Machine.parse(input);
    for (Machine machine : machines) {
      try {
        final Vector answer = machine.solve();
        if (answer.x() <= 100 && answer.y() <= 100) {
          tokens += 3 * answer.x() + answer.y();
        }
      } catch (final SingularMatrixException e) {
        // not solvable
      }
    }
    return tokens;
  }
}
