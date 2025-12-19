package com.codenzasoft.advent2025.day10;

import java.util.ArrayList;
import java.util.List;

public record Machine(Lights lights, List<Buttons> buttonsList) {

  public static Machine parse(final String input) {
    final String[] parts = input.split(" ");
    final Lights lights = Lights.parse(parts[0]);
    final List<Buttons> buttons = new ArrayList<>();
    for (int i = 1; i < parts.length - 1; i++) {
      buttons.add(Buttons.parse(parts[i]));
    }
    return new Machine(lights, buttons);
  }
}
