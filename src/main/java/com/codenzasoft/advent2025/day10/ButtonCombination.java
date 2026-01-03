package com.codenzasoft.advent2025.day10;

import java.util.*;
import org.paukov.combinatorics3.Generator;

public record ButtonCombination(List<Button> buttons, int presses) {

  public Iterator<List<Button>> getCombinations(final int pressCount) {
    return Generator.combination(buttons()).multi(pressCount).iterator();
  }
}
