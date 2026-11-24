package com.codenzasoft.advent2024.day14;

import java.util.Optional;

public record Grid(int width, int height) {

  public Optional<Quadrant> quadrantOf(Location location) {
    final Optional<Integer> x = xSideOf(location);
    final Optional<Integer> y = ySideOf(location);
    if (x.isPresent() && y.isPresent()) {
      return Optional.of(new Quadrant(x.get(), y.get()));
    }
    return Optional.empty();
  }

  public Optional<Integer> xSideOf(Location location) {
    final int x;
    int qWidth = (width - 1) / 2;
    if (location.x() < qWidth) {
      x = 0;
    } else if (location.x() > qWidth) {
      x = 1;
    } else {
      return Optional.empty();
    }
    return Optional.of(x);
  }

  public Optional<Integer> ySideOf(Location location) {
    final int y;
    int qHeight = (height - 1) / 2;
    if (location.y() < qHeight) {
      y = 0;
    } else if (location.y() > qHeight) {
      y = 1;
    } else {
      return Optional.empty();
    }
    return Optional.of(y);
  }
}
