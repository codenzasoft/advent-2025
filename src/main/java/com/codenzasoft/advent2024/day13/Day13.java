package com.codenzasoft.advent2024.day13;

import com.codenzasoft.advent2025.PuzzleHelper;
import java.util.List;
import org.apache.commons.math3.linear.SingularMatrixException;

public class Day13 {

  public static void main(String[] args) {
    List<String> lines = PuzzleHelper.getInputLines("2024/input-day-13.txt");
    System.out.println("The answer to part 1 is: " + new Day13().solvePart1(lines));
    System.out.println("The answer to part 2 is: " + new Day13().solvePart2(lines));
  }

  public long solvePart1(final List<String> input) {
    long tokens = 0;
    final List<Machine> machines = Machine.parse(input);
    for (Machine machine : machines) {
      try {
        final Vector answer = machine.solve();
        tokens += 3 * answer.x() + answer.y();
      } catch (final SingularMatrixException e) {
        // not solvable
      }
    }
    return tokens;
  }

  public long solvePart2(final List<String> input) {
    long tokens = 0;
    final List<Machine> machines = Machine.parse(input);
    for (Machine machine : machines) {
      try {
        final Vector modifiedResult =
            new Vector(
                machine.result().x() + 10000000000000L, machine.result().y() + 10000000000000L);
        final Machine modified = new Machine(machine.a(), machine.b(), modifiedResult);
        final Vector answer = modified.solve();
        tokens += 3 * answer.x() + answer.y();
      } catch (final SingularMatrixException e) {
        // not solvable
      }
    }
    return tokens;
  }
}
