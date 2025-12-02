package com.codenzasoft.advent2025.day1;

import java.io.*;
import java.util.List;

/**
 * Reads a document (puzzle input) containing a sequence of rotations, one per line, which describe
 * how to open the safe. A rotation starts with an L or R which indicates whether the rotation
 * should be to the left (toward lower numbers) or to the right (toward higher numbers). Then, the
 * rotation has a distance value which indicates how many clicks the dial should be rotated in that
 * direction.
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

  public int solve0x434C49434B(
      final List<String> rotations, final int dialSize, final int dialLocation) throws IOException {
    Dial dial = new Dial(dialSize, dialLocation);
    int answer = 0;

    for (final String instruction : rotations) {
      final Rotation outerRotation = Rotation.parse(instruction);
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
