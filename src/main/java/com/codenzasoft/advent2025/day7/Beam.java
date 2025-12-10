package com.codenzasoft.advent2025.day7;

import java.util.Optional;

public record Beam(int x, long edges) {

  public Optional<Beam> combine(final Beam beam) {
    if (beam.x() == this.x()) {
      return Optional.of(new Beam(beam.x(), this.edges() + beam.edges()));
    }
    return Optional.empty();
  }
}
