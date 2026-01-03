package com.codenzasoft.advent2025.day10;

import java.util.*;

public record JoltageLevels(List<Integer> levels) {

  public static JoltageLevels parse(final String input) {
    final String list = input.substring(1, input.length() - 1);
    final String[] levels = list.split(",");
    final List<Integer> levelList = new ArrayList<>();
    for (String level : levels) {
      levelList.add(Integer.parseInt(level));
    }
    return new JoltageLevels(levelList);
  }

  public JoltageLevels duplicate() {
    return new JoltageLevels(new ArrayList<>(levels()));
  }

  public static JoltageLevels value(final int size, final int value) {
    final List<Integer> levelList = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      levelList.add(value);
    }
    return new JoltageLevels(levelList);
  }

  public int getMinValue() {
    int min = Integer.MAX_VALUE;
    for (int i = 0; i < levels().size(); i++) {
      min = Math.min(min, levels().get(i));
    }
    return min;
  }

  public boolean isExceededBy(final JoltageLevels joltage) {
    for (int i = 0; i < levels.size(); i++) {
      if (joltage.levels.get(i) > levels.get(i)) {
        return true;
      }
    }
    return false;
  }

  public JoltageLevels press(final Button button, final int presses) {
    final List<Integer> newLevels = new ArrayList<>();
    for (int i = 0; i < levels().size(); i++) {
      if (button.includesOffset(i)) {
        newLevels.add(i, levels().get(i) + presses);
      } else {
        newLevels.add(i, levels().get(i));
      }
    }
    return new JoltageLevels(newLevels);
  }

  public int getMaxAllowablePresses(final Button button) {
    return Arrays.stream(button.buttonOffsets())
        .map(buttonOffset -> levels().get(buttonOffset))
        .min()
        .orElse(0);
  }

  public Optional<Integer> getGreatestCommonDivisor() {
    final int max = levels().stream().mapToInt(i -> i).max().orElse(0) / 2;
    int gcd = -1;
    for (int i = 2; i < max; i++) {
      final int div = i;
      if (levels().stream().allMatch(l -> l % div == 0)) {
        gcd = div;
      }
    }
    if (gcd == -1) {
      return Optional.empty();
    }
    return Optional.of(gcd);
  }

  public JoltageLevels subtract(final JoltageLevels joltage) {
    final List<Integer> newLevels = new ArrayList<>();
    for (int i = 0; i < levels().size(); i++) {
      newLevels.add(levels().get(i) - joltage.levels().get(i));
    }
    return new JoltageLevels(newLevels);
  }
}
