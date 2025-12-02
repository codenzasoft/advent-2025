package com.codenzasoft.advent2025.day1;

import java.io.*;
import java.util.List;

/**
 * Reads a document (puzzle input) containing a sequence of rotations, one per line, which describe
 * how to open the safe. A rotation starts with an L or R which indicates whether the rotation
 * should be to the left (toward lower numbers) or to the right (toward higher numbers). Then, the
 * rotation has a distance value which indicates how many clicks the dial should be rotated in that
 * direction.
 *
 * <p>There are two parts to the puzzle. Part 1 determines how many times the dial ends up pointing
 * at zero (0), after a rotation is complete. Part 2 determines how many times the dial ends up
 * pointing at or passing zero (0) at any time during the rotations.
 */
public class SafeCracker {

  public static void main(final String[] args) throws IOException {
    try (final InputStream inputStream =
        SafeCracker.class.getResourceAsStream("/input-day-1.txt")) {
      if (inputStream == null) {
        throw new IllegalArgumentException("Input file not found");
      }
      final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
      List<String> rotations = reader.lines().toList();
      System.out.println("The answer to part 1 is: " + new SafeCracker().solve(rotations, 100, 50));
      System.out.println(
          "The answer to part 2 is: " + new SafeCracker().solve0x434C49434B(rotations, 100, 50));
    }
  }

  /**
   * Solves the puzzle according to part 1 rules. Returns the number of times the dial ends up
   * pointing at zero at the end of each rotation.
   *
   * @param rotations A list of rotations to execute
   * @param dialSize The size of the dial (number of notches)
   * @param dialLocation The initial location the dial is pointing at
   * @return The number of time the dial ends up pointing at zero.
   */
  public int solve(final List<String> rotations, final int dialSize, final int dialLocation) {
    Dial dial = new Dial(dialSize, dialLocation);
    int answer = 0;

    for (final String instruction : rotations) {
      final Rotation rotation = Rotation.parse(instruction);
      dial = dial.rotate(rotation);
      if (dial.location() == 0) {
        answer++;
      }
    }

    return answer;
  }

  /**
   * Solves the puzzle according to part 2 rules. Returns the number of times the dial ends up
   * pointing at or passing zero while executing the rotations.
   *
   * @param rotations A list of rotations to execute
   * @param dialSize The size of the dial (number of notches)
   * @param dialLocation The initial location the dial is pointing at
   * @return The number of time the dial ends up pointing at zero or passing zero while executing
   *     the rotations.
   */
  public int solve0x434C49434B(
      final List<String> rotations, final int dialSize, final int dialLocation) throws IOException {
    Dial dial = new Dial(dialSize, dialLocation);
    int answer = 0;

    for (final String instruction : rotations) {
      final Rotation outerRotation = Rotation.parse(instruction);
      // execute the individual steps in the rotation instead of one step
      final Rotation innerRotation = new Rotation(outerRotation.direction(), 1);
      for (int i = 0; i < outerRotation.distance(); i++) {
        dial = dial.rotate(innerRotation);
        if (dial.location() == 0) {
          answer++;
        }
      }
    }

    return answer;
  }
}
