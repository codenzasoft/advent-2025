package com.codenzasoft.advent2025.day10;

import java.util.ArrayList;
import java.util.List;

public record Button(int id, int[] buttonOffsets) {

  public static Button parse(int id, String input) {
    final String text = input.substring(1, input.length() - 1);
    final String[] numbers = text.split(",");
    final int[] offsets = new int[numbers.length];
    for (int i = 0; i < numbers.length; i++) {
      offsets[i] = Integer.parseInt(numbers[i]);
    }
    return new Button(id, offsets);
  }

  public boolean includesOffset(final int offset) {
    for (int buttonOffset : buttonOffsets) {
      if (buttonOffset == offset) {
        return true;
      }
    }
    return false;
  }

  public Vector getVector(final Machine machine) {
    final List<Integer> values = new ArrayList<>();
    for (int i = 0; i < machine.joltage().levels().size(); i++) {
      values.add(0);
    }
    for (int buttonOffset : buttonOffsets) {
      values.set(buttonOffset, 1);
    }
    return new Vector(values);
  }
}
