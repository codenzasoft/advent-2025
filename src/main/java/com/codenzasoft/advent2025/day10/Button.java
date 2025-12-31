package com.codenzasoft.advent2025.day10;

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
}
