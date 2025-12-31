package com.codenzasoft.advent2025.day10;

import java.util.ArrayList;
import java.util.List;

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

  public static JoltageLevels zero(final int size) {
    final List<Integer> levelList = new ArrayList<>();
    for (int i = 0; i < size; i++) {
      levelList.add(0);
    }
    return new JoltageLevels(levelList);
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
}
