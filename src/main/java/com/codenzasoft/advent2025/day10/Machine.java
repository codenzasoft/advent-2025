package com.codenzasoft.advent2025.day10;

import java.util.ArrayList;
import java.util.List;

public record Machine(Lights lights, List<Button> buttonList, JoltageLevels joltage) {

  public static Machine parse(final String input) {
    final String[] parts = input.split(" ");
    final Lights lights = Lights.parse(parts[0]);
    final List<Button> buttons = new ArrayList<>();
    for (int i = 1; i < parts.length - 1; i++) {
      buttons.add(Button.parse(i, parts[i]));
    }
    final JoltageLevels levels = JoltageLevels.parse(parts[parts.length - 1]);
    return new Machine(lights, buttons, levels);
  }

  public List<ButtonCombination> getIndividualCombinations() {
    final List<ButtonCombination> combinations = new ArrayList<>();
    for (int joltageIndex = 0; joltageIndex < joltage().levels().size(); joltageIndex++) {
      final int level = joltage().levels().get(joltageIndex);
      final int index = joltageIndex;
      final List<Button> buttons =
          buttonList().stream().filter(button -> button.includesOffset(index)).toList();
      combinations.add(new ButtonCombination(buttons, level));
    }
    return combinations;
  }
}
