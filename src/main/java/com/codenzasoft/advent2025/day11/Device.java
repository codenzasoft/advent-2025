package com.codenzasoft.advent2025.day11;

import java.util.ArrayList;
import java.util.List;

public record Device(String name, List<String> outputs) {

  public static Device parse(final String line) {
    final String[] parts = line.split(" ");
    final String name = parts[0].substring(0, parts[0].length() - 1);
    final List<String> outputs = new ArrayList<>();
    for (int i = 1; i < parts.length; i++) {
      outputs.add(parts[i]);
    }
    return new Device(name, outputs);
  }
}
