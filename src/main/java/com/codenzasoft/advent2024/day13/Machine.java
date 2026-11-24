package com.codenzasoft.advent2024.day13;

import com.codenzasoft.advent2024.day12.Edge;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public record Machine(Vector a, Vector b, Vector result) {

  public static List<Machine> parse(final List<String> input) {
    final Iterator<String> iterator = input.iterator();
    List<Machine> machines = new ArrayList<>();
    while (iterator.hasNext()) {
      Vector buttonA = Vector.parseButton(iterator.next()); // Button A: X+94, Y+34
      Vector buttonB = Vector.parseButton(iterator.next()); // Button B: X+22, Y+67
      Vector prize = Vector.parsePrize(iterator.next());    // Prize: X=8400, Y=5400
      if (iterator.hasNext()) {
        iterator.next(); // blank line
      }
      machines.add(new Machine(buttonA, buttonB, prize));
    }
    return machines;
  }
}
