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

  public Machine removeZeroJoltages() {
    Vector newJoltage = joltage().getVector();
    int zeroIndex = newJoltage.indexOf(0);
    Matrix newMatrix = getMatrix();
    while (zeroIndex >= 0) {
      newJoltage = newJoltage.removeColumn(zeroIndex);
      // remove any rows (buttons) that have a 1 for that index (they cannot be used)
      Vector column = newMatrix.getColumn(zeroIndex);
      int oneIndex = column.indexOf(1);
      while (oneIndex >= 0) {
        newMatrix = newMatrix.removeRow(oneIndex);
        column = newMatrix.getColumn(zeroIndex);
        oneIndex = column.indexOf(1);
      }
      // remove the column
      newMatrix = newMatrix.removeColumn(zeroIndex);

      zeroIndex = newJoltage.indexOf(0);
    }
    final List<Button> newButtons = new ArrayList<>();
    int id = 0;
    for (final Vector row : newMatrix.rows()) {
      newButtons.add(row.toButton(id));
      id++;
    }
    return new Machine(lights(), newButtons, newJoltage.toJoltageLevels());
  }
}
