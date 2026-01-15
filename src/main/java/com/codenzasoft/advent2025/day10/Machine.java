package com.codenzasoft.advent2025.day10;

import java.util.ArrayList;
import java.util.Comparator;
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

  public JoltageLevels newJoltage(final int value) {
    return JoltageLevels.value(joltage().levels().size(), value);
  }

  public Matrix getMatrix() {
    final List<Vector> vectors = new ArrayList<>();
    for (Button button : buttonList()) {
      vectors.add(button.getVector(this));
    }
    return new Matrix(vectors);
  }

  public Machine getSortedJoltage() {
    final Vector originalJoltage = new Vector(joltage().levels());
    final List<Integer> sortedIndicies = originalJoltage.getSortedIndicies();
    final JoltageLevels sortedJoltage = originalJoltage.reorder(sortedIndicies).toJoltageLevels();
    final List<Button> sortedButtons = new ArrayList<>();
    for (final Button button : buttonList()) {
      sortedButtons.add(button.getVector(this).reorder(sortedIndicies).toButton(button.id()));
    }
    return new Machine(lights(), sortedButtons, sortedJoltage);
  }

  public Machine removeZeroColumns() {
    final List<Integer> zeroIndexes = new ArrayList<>();
    final List<Integer> newJoltageLevels = new ArrayList<>();
    for (int index = 0; index < joltage().levels().size(); index++) {
      if (joltage().levels().get(index) == 0) {
        zeroIndexes.add(index);
      } else {
        newJoltageLevels.add(joltage().levels().get(index));
      }
    }
    if (zeroIndexes.isEmpty()) {
      return this;
    } else {
      final JoltageLevels newJoltage = new JoltageLevels(newJoltageLevels);
      final List<Button> newButtons = new ArrayList<>();
      for (final Button button : buttonList()) {
        if (!button.containsAnyIndex(zeroIndexes)) {
          Vector vector = button.getVector(this);
          int removalOffset = 0;
          for (int index : zeroIndexes) {
            vector = vector.removeColumn(index - removalOffset);
            removalOffset++;
          }
          newButtons.add(vector.toButton(button.id()));
        }
      }
      return new Machine(lights(), newButtons, newJoltage);
    }
  }
}
