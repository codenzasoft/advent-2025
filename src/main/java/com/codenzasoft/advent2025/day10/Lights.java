package com.codenzasoft.advent2025.day10;

import java.util.ArrayList;
import java.util.List;

public record Lights(List<Boolean> states) {

  public static Lights parse(final String indicators) {
    final List<Boolean> states = new ArrayList<>();
    for (int i = 1; i < indicators.length() - 1; i++) {
      states.add(indicators.charAt(i) == '#');
    }
    return new Lights(states);
  }

  public static Lights allOff(int numberOfLights) {
    final List<Boolean> states = new ArrayList<>();
    for (int i = 0; i < numberOfLights; i++) {
      states.add(false);
    }
    return new Lights(states);
  }

  public Lights press(final Button button) {
    final List<Boolean> next = new ArrayList<>(this.states);
    for (int offset : button.buttonOffsets()) {
      next.set(offset, !(next.get(offset)));
    }
    return new Lights(next);
  }
}
