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
    Plotter plotter = this;
    while (!plotter.isSymmetric()) {
      plotter = plotter.move(1);
      count++;
      if (count % 5000 == 0) {
        System.out.println(count);
      }
    }
    return count;
  }

  public boolean isSymmetric() {
    final Map<Integer, List<Location>> groups =
        robots().stream()
            .map(Robot::location)
            .distinct()
            .collect(Collectors.groupingBy(Location::y));
    if (groups.get(0).size() == 1) {
      return groups.values().stream().allMatch(this::isMirrored);
    }
    return false;
  }

  public boolean isMirrored(final List<Location> locations) {
    if (locations.size() < 2) {
      return locations.getFirst().x() == grid().width() / 2;
    }
    LinkedList<Location> mirrored = new LinkedList<>(locations);
    Collections.sort(mirrored);
    final Location first = mirrored.removeFirst();
    final Location last = mirrored.getLast();
    final boolean same = (first.x() == (grid().width() - last.x() - 1));
    return same;
  }

  public int trySome(int n) {
    long max = 0;
    int movesForMax = 0;
    Plotter plotter = this;
    for (int i = 0; i < n; i++) {
      plotter = plotter.move(1);
      final long score = plotter.connectedScore();
      if (score > max) {
        max = score;
        movesForMax = i;
        System.out.println("Score: " + score + ", Moves: " + (movesForMax + 1));
        System.out.println(plotter);
      }
    }
    return movesForMax + 1;
  }

  public long connectedScore() {
    final Set<Location> locations =
        robots.stream().map(Robot::location).collect(Collectors.toSet());
    long score = 0;
    for (Location location : locations) {
      if (locations.contains(location.left())) {
        score += 1;
      }
      if (locations.contains(location.right())) {
        score += 1;
      }
      if (locations.contains(location.up())) {
        score += 1;
      }
      if (locations.contains(location.down())) {
        score += 1;
      }
    }
    return score;
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
