package com.codenzasoft.advent2024.day6;

import java.util.Optional;

public class RouteBuilder {

  private final FloorMap map;

  public RouteBuilder(final FloorMap map) {
    this.map = map;
  }

  public Route build() {
    final Route route = new Route();
    route.addStep(map.origin());
    boolean done = false;
    while (!done) {
      final Optional<Step> next = map.nextStep(route.getLastStep());
      done = next.map(s -> !route.addStep(s)).orElse(true);
    }
    return route;
  }
}
