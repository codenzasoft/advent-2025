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

  public int numberOfOffsets() {
    return buttonOffsets.length;
  }

  public Vector getVector(final Machine machine) {
    return getVector(machine.joltage().levels().size());
  }

  public Vector getVector(final int numColumns) {
    final List<Integer> values = new ArrayList<>();
    for (int i = 0; i < numColumns; i++) {
      values.add(0);
    }
    for (int buttonOffset : buttonOffsets) {
      values.set(buttonOffset, 1);
    }
    return new Vector(values);
  }

  public boolean containsAnyIndex(final List<Integer> indicies) {
    for (int buttonOffset : buttonOffsets) {
      if (indicies.contains(buttonOffset)) {
        return true;
      }
    }
    return false;
  }
}
