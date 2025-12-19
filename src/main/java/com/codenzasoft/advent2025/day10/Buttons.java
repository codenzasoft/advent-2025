package com.codenzasoft.advent2025.day10;

public record Buttons(int[] buttonOffsets) {

  public static Buttons parse(String input) {
    final String text = input.substring(1, input.length() - 1);
    final String[] numbers = text.split(",");
    final int[] offsets = new int[numbers.length];
    for (int i = 0; i < numbers.length; i++) {
      offsets[i] = Integer.parseInt(numbers[i]);
    }
    return new Buttons(offsets);
  }
}
