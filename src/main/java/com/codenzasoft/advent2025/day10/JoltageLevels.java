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
}
