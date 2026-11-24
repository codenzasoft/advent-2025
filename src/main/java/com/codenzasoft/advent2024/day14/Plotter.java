package com.codenzasoft.advent2024.day14;

import java.util.*;
import java.util.stream.Collectors;

public record Plotter(List<Robot> robots, Grid grid) {

  public static Plotter parse(List<String> input, final Grid grid) {
    final List<Robot> robots = new ArrayList<>();
    for (String line : input) {
      robots.add(Robot.parse(line));
    }
    return new Plotter(robots, grid);
  }

  public Plotter move(int n) {
    final List<Robot> nextRobots = new ArrayList<>();
    for (Robot robot : robots) {
      Robot nextRobot = robot;
      for (int i = 0; i < n; i++) {
        nextRobot = nextRobot.move(grid);
      }
      nextRobots.add(nextRobot);
    }
    return new Plotter(nextRobots, grid);
  }

  public int moveUntilSymmetric() {
    int count = 0;
    boolean symmetric = false;
    Plotter plotter = this;
    while (!plotter.isSymmetric()) {
      plotter = plotter.move(1);
      count++;
    }
    return count;
  }

  public boolean isSymmetric() {
    final Map<Integer, List<Location>> groups =
        robots().stream()
            .map(Robot::location)
            .distinct()
            .collect(Collectors.groupingBy(Location::y));
    return groups.values().stream().allMatch(this::isMirrored);
  }

  public boolean isMirrored(final List<Location> locations) {
    if (locations.size() < 2) {
      return true;
    }
    LinkedList<Location> mirrored = new LinkedList<>(locations);
    Collections.sort(mirrored);
    final Location first = mirrored.removeFirst();
    final Location last = mirrored.getLast();
    final boolean same = (first.x() == (grid().width() - last.x() - 1));
    return same;
  }

  public String toString() {
    final StringBuilder builder = new StringBuilder();
    Set<Location> locations = robots().stream().map(Robot::location).collect(Collectors.toSet());
    for (int y = 0; y < grid.height(); y++) {
      for (int x = 0; x < grid.width(); x++) {
        if (locations.contains(new Location(x, y))) {
          builder.append('*');
        } else {
          builder.append('.');
        }
      }
      builder.append('\n');
    }
    return builder.toString();
  }
}
