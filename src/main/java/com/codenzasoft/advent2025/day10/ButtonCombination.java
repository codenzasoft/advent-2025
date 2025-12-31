package com.codenzasoft.advent2025.day10;

import java.util.*;

public record ButtonCombination(List<Button> buttons, int presses) {

  public ButtonCombination getRemaining(final Button button, final int numPresses) {
    if (buttons.contains(button)) {
      if (numPresses <= presses()) {
        return new ButtonCombination(buttons, presses - numPresses);
      } else {
        throw new IllegalStateException("Too many presses");
      }
    }
    return this;
  }

  public List<Button> getCommonButtons(final ButtonCombination combination) {
    final Set<Button> commonButtons = new HashSet<>(buttons());
    commonButtons.retainAll(combination.buttons());
    return new ArrayList<>(commonButtons);
  }

  public Optional<ButtonCombination> removeButtons(final List<Button> remove) {
    final List<Button> newButtons = new ArrayList<>(buttons());
    newButtons.removeAll(remove);
    if (newButtons.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(new ButtonCombination(newButtons, presses()));
  }
}
