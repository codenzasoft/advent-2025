package com.codenzasoft.advent2024.day5;

import java.util.Optional;

public record PageEdge(int from, int to) {

  public static Optional<PageEdge> parse(String line) {
    if (line.indexOf("|") > 0) {
      String[] pages = line.split("\\|");
      return Optional.of(new PageEdge(Integer.parseInt(pages[0]), Integer.parseInt(pages[1])));
    }
    return Optional.empty();
  }
}
